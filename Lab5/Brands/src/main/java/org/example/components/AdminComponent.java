package org.example.components;

import org.example.entities.Brand;
import org.example.services.BrandsService;
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
    private final BrandsService brandsService;

    @Autowired
    public AdminComponent(BrandsService brandsService) {
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


                case "listBrands":
                {
                    System.out.println("List of Brands: ");
                    brandsService.findAll().forEach(System.out::println);
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
                    System.out.println("listBrands - lists all brands in the database");
                    System.out.println("addBrand - adds a brand to the database");
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
