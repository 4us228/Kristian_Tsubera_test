package com.TsuberaKristian.database;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;


public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/mytask";
    private static final String USER = "root";
    private static final String PASSWORD = "1234567uiI";

    public void insertInBase(String expr, Calculated calculated) {
        double answer = calculated.calculation(expr);
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO exercise (exercise_task, exercise_answer) VALUES ('" + expr + "','" + answer + "')");
        } catch (SQLException e) {
            System.out.println("sql is not connected");
        }
    }

    public void info() {
        String query = "select * from exercise";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Exercises exercises = new Exercises();
                exercises.setExercisesID(resultSet.getInt(1));
                exercises.setExercisesTask(resultSet.getString(2));
                exercises.setExercisesAnswer(resultSet.getDouble(3));
                System.out.println(exercises);

            }
        } catch (SQLException e) {
            System.out.println("sql is not connected");
        }
    }

    public void updateSql(int id, String expr, Calculated calculated) {
        double answer = calculated.calculation(expr);
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE exercise SET exercise_task = '" + expr + "'  WHERE idexercise='" + id + "' ");
            statement.executeUpdate("UPDATE exercise SET exercise_answer = '" + answer + "'  WHERE idexercise='" + id + "' ");
        } catch (SQLException e) {
            System.out.println("sql is not connected");
        }

    }


}
