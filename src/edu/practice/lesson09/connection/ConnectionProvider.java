package edu.practice.lesson09.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static edu.practice.lesson09.constants.Constants.*;

public class ConnectionProvider {

    public Optional<Connection> connection() {
        try {
            return Optional.of(DriverManager.getConnection(URL, USERNAME, PASSWORD));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return Optional.empty();
    }
}
