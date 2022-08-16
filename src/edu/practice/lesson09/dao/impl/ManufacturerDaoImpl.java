package edu.practice.lesson09.dao.impl;

import edu.practice.lesson09.connection.ConnectionProvider;
import edu.practice.lesson09.dao.ManufacturerDao;
import edu.practice.lesson09.model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ManufacturerDaoImpl implements ManufacturerDao {

    private final Optional<Connection> connection;

    public ManufacturerDaoImpl() {
        this.connection = new ConnectionProvider().connection();
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Optional<Manufacturer> findOne(Long id) {
        if (connection.isPresent()) {
            String query = "SELECT id, name, country FROM manufacturer WHERE id = ?";
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
    public List<Manufacturer> findAll() {
        if (connection.isPresent()) {
            String query = "SELECT id, name, country FROM manufacturer ";
            try (Statement statement = connection.get().createStatement()) {
                return getResults(statement.executeQuery(query));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Manufacturer> create(Manufacturer source) {
        if (connection.isPresent()) {
            String query = "INSERT INTO manufacturer(name, country) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, source.getName());
                preparedStatement.setString(2, source.getCountry());
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
    public Optional<Manufacturer> update(Manufacturer source) {
        if (connection.isPresent()) {
            String query = "UPDATE manufacturer SET  name = ?, country = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setString(1, source.getName());
                preparedStatement.setString(2, source.getCountry());
                preparedStatement.setLong(3, source.getId());
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
            String query = "DELETE FROM manufacturer WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.get().prepareStatement(query)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    private List<Manufacturer> getResults(ResultSet resultSet) {
        try {
            List<Manufacturer> manufacturerList = new ArrayList<>();
            while (resultSet.next()) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setId(resultSet.getLong("id"));
                manufacturer.setName(resultSet.getString("name"));
                manufacturer.setCountry(resultSet.getString("country"));
                manufacturerList.add(manufacturer);
            }
            return manufacturerList;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Collections.emptyList();
    }
}
