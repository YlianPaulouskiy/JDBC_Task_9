package edu.practice.lesson09.service.impl;

import edu.practice.lesson09.dao.WatchDao;
import edu.practice.lesson09.dao.impl.ManufacturerDaoImpl;
import edu.practice.lesson09.dao.impl.WatchDaoImpl;
import edu.practice.lesson09.model.Manufacturer;
import edu.practice.lesson09.model.Watch;
import edu.practice.lesson09.service.WatchService;

import java.util.List;

public class WatchServiceImpl implements WatchService {

    private final WatchDao watchDao;

    public WatchServiceImpl() {
        this.watchDao = new WatchDaoImpl();
    }

    //------------------------------------------------------------------------------------------------------------------


    @Override
    public Watch findOne(Long id) {
        return watchDao.findOne(id).orElseThrow(
                () -> new RuntimeException("Watch not found")
        );
    }

    @Override
    public List<Watch> findAll() {
        return watchDao.findAll();
    }

    @Override
    public Watch save(Watch source) {
        return source.getId() == null ?
                watchDao.create(source).orElseThrow(
                        () -> new RuntimeException("Watch not created")
                ) :
                watchDao.update(source).orElseThrow(
                        () -> new RuntimeException("Watch not updated")
                );
    }

    @Override
    public void remove(Long id) {
        watchDao.remove(id);
    }
}
