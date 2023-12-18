package ru.geekbrains.task1;

import ru.geekbrains.models.Courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Program {

    private  final static Random random = new Random();

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:13306/";
        String user = "root";
        String password = "password";

        try {
            // Подключение к базе данных
            Connection connection = DriverManager.getConnection(url, user, password);

            // Создание базы данных
            createDatabase(connection);
            System.out.println("Database created successfully");

            // Использование базы данных
            userDatabase(connection);
            System.out.println("Use database successfully");

            // Создание таблицы
            createTable(connection);
            System.out.println("Create table successfully");

            // Добавление данных
            int count = random.nextInt(5, 11);
            for (int i =0; i < count; i++)
                insertData(connection, Courses.create());
            System.out.println("Insert data successfully");

            // Чтение данных из БД
            Collection<Courses> courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            // Обновление данных
            for (var course: courses) {
                course.updateTitle();
                course.updateDuration();
                updateData(connection, course);
            }
            System.out.println("Update data successfully");

            // Чтение данных из БД
            courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            // Удаление данных
            for (var course: courses)
                deleteData(connection, course.getId());
            System.out.println("Delete data successfully");

            // Чтение данных из БД
            courses = readData(connection);
            for (var course: courses)
                System.out.println(course);
            System.out.println("Read data successfully");

            // закрытие соединения с БД
            connection.close();
            System.out.println("Database connection close successfully");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // region Вспомогательные методы

    private static void createDatabase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS schoolDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void userDatabase(Connection connection) throws SQLException{
        String useDatebaseSQL = "USE schoolDB;";
        try(PreparedStatement statement = connection.prepareStatement(useDatebaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Courses (id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255), duration INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }
    private static void insertData (Connection connection, Courses courses) throws SQLException {
        String insertDataSQL = "INSERT INTO Courses (title, duration) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, courses.getTitle());
            statement.setInt(2, courses.getDuration());
            statement.executeUpdate();
        }
    }

    private static Collection<Courses> readData (Connection connection) throws SQLException {
        ArrayList<Courses> coursesList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM Courses;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                coursesList.add (new Courses(id, title, duration));
            }
            return coursesList;
        }
    }

    private static void updateData (Connection connection, Courses courses) throws SQLException {
        String updateDataSQL = "UPDATE Courses SET title=?, duration=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setString(1, courses.getTitle());
            statement.setInt(2, courses.getDuration());
            statement.setInt(3, courses.getId());
            statement.executeUpdate();
        }
    }

    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM Courses WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}