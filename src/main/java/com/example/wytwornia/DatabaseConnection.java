package com.example.wytwornia;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wytwornia_sbas", "root", ""); // "jdbc:mysql://localhost/wytworniafilmowa", "root", ""

    public DatabaseConnection() throws SQLException {
    }


    public boolean searchQuery(String wantedquery) throws SQLException {

        Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(wantedquery);
    if(resultSet.next()) return true;
    else return false;

       /* while (resultSet.next()) {
            System.out.println(resultSet.getString("Login"));

    }

*/
}

    public boolean insertQuery(String wantedquery) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(wantedquery);
        return true;


    }


}