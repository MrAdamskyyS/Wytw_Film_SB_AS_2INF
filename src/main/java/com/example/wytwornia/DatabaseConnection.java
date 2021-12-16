package com.example.wytwornia;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection = DriverManager.getConnection("jdbc:mysql://192.166.219.220:3306/sbas", "sbas", "sY.2bUJ.sr"); // "jdbc:mysql://localhost/wytworniafilmowa", "root", ""

    public DatabaseConnection() throws SQLException {
    }


    public void query() throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("login"));

    }


}
}