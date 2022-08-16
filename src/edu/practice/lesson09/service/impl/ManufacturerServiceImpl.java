package edu.practice.lesson09.service.impl;

import edu.practice.lesson09.dao.ManufacturerDao;
import edu.practice.lesson09.dao.impl.ManufacturerDaoImpl;
import edu.practice.lesson09.model.Manufacturer;
import edu.practice.lesson09.service.ManufacturerService;

import java.util.List;

public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerDao manufacturerDao;

    public ManufacturerServiceImpl() {
        this.manufacturerDao = new ManufacturerDaoImpl();
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Manufacturer findOne(Long id) {
        return manufacturerDao.findOne(id).orElseThrow(
                () -> new RuntimeException("Manufacturer not found")
        );
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerDao.findAll();
    }

    @Override
    public Manufacturer save(Manufacturer source) {
        return source.getId() == null ?
                manufacturerDao.create(source).orElseThrow(
                        () -> new RuntimeException("Manufacturer not created")
                ) :
                manufacturerDao.update(source).orElseThrow(
                        () -> new RuntimeException("Manufacturer not updated")
                );
    }

    @Override
    public void remove(Long id) {
        manufacturerDao.remove(id);
    }
}
