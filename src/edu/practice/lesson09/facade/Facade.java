package edu.practice.lesson09.facade;

import edu.practice.lesson09.model.Manufacturer;
import edu.practice.lesson09.model.Watch;
import edu.practice.lesson09.model.type.WatchType;
import edu.practice.lesson09.service.ManufacturerService;
import edu.practice.lesson09.service.WatchService;
import edu.practice.lesson09.service.impl.ManufacturerServiceImpl;
import edu.practice.lesson09.service.impl.WatchServiceImpl;
import jdk.internal.dynalink.linker.LinkerServices;

import java.util.List;

public class Facade {

    private final WatchService watchService;
    private final ManufacturerService manufacturerService;

    public Facade() {
        watchService = new WatchServiceImpl();
        manufacturerService = new ManufacturerServiceImpl();
    }

    public void printAllBrandWatchByType(WatchType type) {
        List<Watch> watchList = watchService.findAll();
        watchList.stream().filter(watch -> watch.getType().equals(type)).forEach(System.out::println);
    }

    public void printInfoAboutMechanicalWatchWithLessPrice(Double price) {
        List<Watch> watchList = watchService.findAll();
        watchList.stream().filter(watch -> watch.getPrice().compareTo(price) > 0).forEach(System.out::println);
    }

    public void printWatchBrandByCountry(String country) {
        List<Watch> watchList = watchService.findAll();
        watchList.stream().filter(watch -> watch.getManufacturer().getCountry().equals(country))
                .forEach(System.out::println);
    }

    public void printManufacturerByAllPriceLessThis(Double price) {
        List<Watch> watchList = watchService.findAll();
        List<Manufacturer> manufacturerList = manufacturerService.findAll();
        for (Manufacturer manufacturer : manufacturerList) {
            double allPrice = 0;
            for (Watch watch : watchList) {
                if (watch.getManufacturer().equals(manufacturer)) {
                    allPrice += watch.getPrice();
                }
            }
            System.out.println(manufacturer.getName() + " " + manufacturer.getCountry());
        }
    }
}
