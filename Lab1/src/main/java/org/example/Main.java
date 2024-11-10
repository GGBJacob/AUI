package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Brand b1 = Brand.builder().name("Audi").issueYear(1925).build();
        Brand b2 = Brand.builder().name("BMW").issueYear(1899).build();
        Brand b3 = Brand.builder().name("Mercedes").issueYear(1919).build();


        List<Brand> brands = new ArrayList<>();

        List<Car> cars1 = new ArrayList<>();
        List<Car> cars2 = new ArrayList<>();
        List<Car> cars3 = new ArrayList<>();


        Car c1 = Car.builder().model("ABC").horsePower(156).brand(b1).build();
        Car c2 = Car.builder().model("ABC").horsePower(192).brand(b1).build();
        Car c3 = Car.builder().model("XYZ").horsePower(200).brand(b1).build();

        Car c4 = Car.builder().model("DEF").horsePower(257).brand(b2).build();
        Car c5 = Car.builder().model("HIJ").horsePower(452).brand(b2).build();
        Car c6 = Car.builder().model("KLT").horsePower(380).brand(b2).build();

        Car c7 = Car.builder().model("FFF").horsePower(623).brand(b3).build();
        Car c8 = Car.builder().model("WRR").horsePower(587).brand(b3).build();
        Car c9 = Car.builder().model("KKK").horsePower(292).brand(b3).build();

        cars1.add(c1);
        cars1.add(c2);
        cars1.add(c3);

        cars2.add(c4);
        cars2.add(c5);
        cars2.add(c6);

        cars3.add(c7);
        cars3.add(c8);
        cars3.add(c9);

        b1.cars = cars1;
        b2.cars = cars2;
        b3.cars = cars3;

        brands.add(b1);
        brands.add(b2);
        brands.add(b3);

        System.out.println("\n2. Brands list\n");
        brands.forEach(brand -> {
            System.out.println(brand.name);
            brand.cars.forEach(car -> System.out.println("\t" + car));
        });

        System.out.println("\n3. All objects set\n");

        Set<Car> carsSet = brands.stream()
                .flatMap(brand -> brand.getCars().stream()).collect(Collectors.toSet());

        carsSet.stream().forEach(System.out::println);

        System.out.println("\n4. Filtered and sorted\n");

        carsSet.stream()
                .filter(car -> car.horsePower > 300)
                .sorted(Comparator.comparing(Car::getModel))
                .forEach(System.out::println);

        System.out.println("\n5. Set into list\n");

        List<CarDto> carsList = carsSet.stream()
                .map(car -> new CarDto(car.getModel(), car.getHorsePower(), car.getBrand().getName()))
                .sorted()
                .toList();

        carsList.stream().forEach(System.out::println);

        System.out.println("\n6.1. Serialization\n");

        try {
            FileOutputStream fout = new FileOutputStream("./brands.ser");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(brands);
            out.close();
            fout.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Success!\n\n6.2. Deserialization\n");

        try{
            FileInputStream fin = new FileInputStream("./brands.ser");
            ObjectInputStream in = new ObjectInputStream(fin);

            List<Brand> deserialized_brands = (List<Brand>) in.readObject();
            in.close();
            fin.close();

            for (Brand brand : deserialized_brands)
            {
                System.out.println(brand.name);
                for (Car car : brand.getCars())
                    System.out.println("\t" + car);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n7. Parallel pipelines\n");

        final int parallelism = 1;
        ForkJoinPool forkJoinPool = null;
        try {
            forkJoinPool = new ForkJoinPool(parallelism);

            forkJoinPool.submit(() ->
                    brands.parallelStream().forEach(brand ->
                            brand.getCars().forEach(car -> {
                                System.out.println("thread: " + Thread.currentThread().getName() + "\n\t" + car);
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            })
                    )
            ).get();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
            }
        }

    }
}