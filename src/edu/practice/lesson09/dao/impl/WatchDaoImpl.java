package edu.practice.lesson09.dao.impl;

import edu.practice.lesson09.connection.ConnectionProvider;
import edu.practice.lesson09.dao.WatchDao;
import edu.practice.lesson09.model.Manufacturer;
import edu.practice.lesson09.model.Watch;
import edu.practice.lesson09.model.type.WatchType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WatchDaoImpl implements WatchDao {

    private final Optional<Connection> connection;

    public WatchDaoImpl() {
        this.connection = new ConnectionProvider().connection();
    }

    //------------------------------------------------------------------------------------------------------------------


    @Override
    public Optional<Watch> findOne(Long id) {
        if (connection.isPresent()) {
            String query = "SELECT id, brand, type, price, amount, manufacturer_id FROM watch WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                return Optional.of(getResults(preparedStatement.executeQuery()).get(0));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Watch> findAll() {
        if (connection.isPresent()) {
            String query = "SELECT id, brand, type, price, amount, manufacturer_id FROM watch";
            try (Statement statement = connection.get().createStatement()) {
                return getResults(statement.executeQuery(query));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Watch> create(Watch source) {
        if (connection.isPresent()) {
            String query = "INSERT INTO watch(brand, type, price, amount, manufacturer_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, source.getBrand());
                preparedStatement.setString(2, source.getType().getType());
                preparedStatement.setDouble(3, source.getPrice());
                preparedStatement.setInt(4, source.getAmount());
                preparedStatement.setLong(5, source.getManufacturer().getId());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return findOne(resultSet.getLong(1));
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Watch> update(Watch source) {
        if (connection.isPresent()) {
            String query = "UPDATE watch SET brand = ?, type = ?, price = ?, amount = ?, manufacturer_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setString(1, source.getBrand());
                preparedStatement.setString(2, source.getType().getType());
                preparedStatement.setDouble(3, source.getPrice());
                preparedStatement.setInt(4, source.getAmount());
                preparedStatement.setLong(5, source.getManufacturer().getId());
                preparedStatement.setLong(6, source.getId());
                preparedStatement.executeUpdate();
                return findOne(source.getId());
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {
        if (connection.isPresent()) {
            String query = "DELETE FROM watch WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private List<Watch> getResults(ResultSet resultSet) {
        try {
            List<Watch> manufacturerList = new ArrayList<>();
            while (resultSet.next()) {
                Watch watch = new Watch();
                watch.setId(resultSet.getLong("id"));
                watch.setBrand(resultSet.getString("brand"));
                watch.setType(getWatchType(resultSet.getString("type")));
                watch.setPrice(resultSet.getDouble("price"));
                watch.setAmount(resultSet.getInt("amount"));
                watch.setManufacturer(new ManufacturerDaoImpl()
                        .findOne(resultSet.getLong("manufacturer_id"))
                        .orElse(new Manufacturer()));
                manufacturerList.add(watch);
            }
            return manufacturerList;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    private WatchType getWatchType(String type) {
        for (WatchType value : WatchType.values()) {
            if(value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }
}
