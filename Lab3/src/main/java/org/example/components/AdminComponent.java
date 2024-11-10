package org.example.components;

import org.example.entities.Brand;
import org.example.entities.Car;
import org.example.repositories.CarBrandsRepository;
import org.example.repositories.CarsRepository;
import org.example.services.BrandsService;
import org.example.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class AdminComponent implements CommandLineRunner {

    private Boolean isRunning = true;
    private final CarsService carsService;
    private final BrandsService brandsService;

    @Autowired
    public AdminComponent(CarsService carsService, BrandsService brandsService) {
        this.carsService = carsService;
        this.brandsService = brandsService;
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("App Started!");
        Scanner scanner = new Scanner(System.in);
        while (isRunning) {
            System.out.println("Enter command: ");
            String command = scanner.nextLine().trim();
            switch (command) {

                case "listCars":
                {
                    System.out.println("List of Cars: ");
                    carsService.findAll().forEach(System.out::println);
                    break;
                }

                case "listBrands":
                {
                    System.out.println("List of Brands: ");
                    brandsService.findAll().forEach(System.out::println);
                    break;
                }

                case "addCar":
                {
                    System.out.println("Enter car model: ");
                    var carModel = scanner.nextLine().trim();
                    System.out.println("Enter car horse power:");
                    int hp;
                    var temp = scanner.nextLine().trim();
                    try {
                        hp = Integer.parseInt(temp);
                    }catch(InputMismatchException e) {
                        System.out.println("Invalid car power!");
                        break;
                    }
                    System.out.println("Enter car brand:");
                    var brandName = scanner.nextLine().trim();
                    var carBrand = brandsService.findByName(brandName).stream().findFirst();

                    if(carBrand.isEmpty()) {
                        System.out.println("Brand not found!");
                        break;
                    }

                    var car = Car.builder().horsePower(hp).model(carModel).brand((Brand) carBrand.get()).build();

                    carsService.save(car);

                    break;
                }

                case "addBrand":
                {
                    System.out.println("Enter brand name:");
                    var brandName = scanner.nextLine().trim();
                    var brand = brandsService.findByName(brandName).stream().findFirst();
                    if (brand.isPresent())
                    {
                        System.out.println("Brand already exists!");
                        break;
                    }

                    System.out.println("Enter brand issue year:");
                    var temp = scanner.nextLine().trim();

                    int issueYear;
                    try {
                        issueYear = Integer.parseInt(temp);
                    }catch (InputMismatchException e)
                    {
                        System.out.println("Invalid issue year!");
                        break;
                    }

                    var newBrand = Brand.builder().name(brandName).issueYear(issueYear).build();

                    brandsService.save(newBrand);

                    break;
                }

                case "deleteCar":
                {
                    System.out.println("Enter car model to remove: ");
                    String model = scanner.nextLine().trim();

                    List<Car> cars = carsService.findByModel(model);

                    if (cars.isEmpty()) {
                        System.out.println("No cars found with the given model!");
                    } else if (cars.size() == 1) {
                        carsService.delete(cars.get(0));
                        System.out.println("Car removed successfully.");
                    } else {
                        System.out.println("Multiple cars found. Please specify horse power:");
                        System.out.println("Available cars: ");
                        cars.forEach(car -> System.out.println(car.getModel() + " - HP: " + car.getHorsePower()));

                        int horsePower;
                        try {
                            horsePower = Integer.parseInt(scanner.nextLine().trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid horse power value!");
                            break;
                        }

                        var carOptional = cars.stream()
                                .filter(car -> car.getHorsePower() == horsePower)
                                .findFirst();

                        if (carOptional.isPresent()) {
                            carsService.delete(carOptional.get());
                            System.out.println("Car removed successfully.");
                        } else {
                            System.out.println("Car with the specified horse power not found!");
                        }
                    }
                    break;
                }

                case "deleteBrand":
                {
                    System.out.println("Enter brand name to remove:");
                    var brandName = scanner.nextLine().trim();
                    if(brandsService.findByName(brandName).isEmpty()) {
                        System.out.println("Brand not found!");
                        break;
                    }
                    var brand = brandsService.findByName(brandName).stream().findFirst();

                    if (brand.isEmpty()) {
                        System.out.println("Brand not found!");
                        break;
                    }

                    brandsService.delete(brand.get());
                    System.out.println("Brand removed successfully.");
                    break;
                }


                case "exit": {
                    isRunning = false;
                    break;
                }

                default: {
                    System.out.println("Invalid command\n");
                }

                case "help":
                {
                    System.out.println("Available commands: ");
                    System.out.println("listCars - lists all cars in the database");
                    System.out.println("listBrands - lists all brands in the database");
                    System.out.println("addCar - adds a car to the database");
                    System.out.println("addBrand - adds a brand to the database");
                    System.out.println("deleteCar - deletes a car from the database");
                    System.out.println("deleteBrand - cascade deletes a brand from the database");
                    System.out.println("exit - Shutdowns the application");
                    break;
                }
            }
        }
        scanner.close();
        System.out.println("Exited!");
        System.exit(0);
    }
}
