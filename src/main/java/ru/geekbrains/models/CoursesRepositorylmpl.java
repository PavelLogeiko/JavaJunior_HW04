package ru.geekbrains.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class CoursesRepositorylmpl implements CoursesRepository{
    @Override
    public void add(Courses item) {
        String url = "jdbc:mysql://localhost:13306/";
        String user = "root";
        String password = "password";

        // Подключение к базе данных
        try(Connection connection = DriverManager.getConnection(url, user, password)){
            String insertDataSQL = "INSERT INTO students (name, age) VALUES (?, ?);";
            try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
                statement.setString(1, item.getTitle());
                statement.setInt(2, item.getDuration());
                statement.executeUpdate();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(Courses item) {

    }

    @Override
    public void delete(Courses item) {

    }

    @Override
    public Courses getById(Integer integer) {
        return null;
    }

    @Override
    public Collection<Courses> getAll() {
        return null;
    }
}
