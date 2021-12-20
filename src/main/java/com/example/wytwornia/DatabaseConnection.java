package com.example.wytwornia;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wytwornia_sbas", "root", ""); // "jdbc:mysql://localhost/wytworniafilmowa", "root", ""

    public DatabaseConnection() throws SQLException {
    }


    public boolean searchQuery(String wantedquery) throws SQLException { // sprawdza czy dane zapytanie cos zwroci
        Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(wantedquery);
    if(resultSet.next()) return true; // jezeli cos znajdzie -> true
    else return false; // jezeli nic nie znajdzie -> false

}
    public boolean insertQuery(String wantedquery) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(wantedquery);
        return true;
    }

    public User dodajUzytkownika(String login) throws SQLException { // znajdujemy uzytkownika, tworzymy obiekt i go zwracamy
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT Login, Password, Settings, Wallet, Admin FROM users WHERE Login=\""+login+"\";");
        if(resultSet.next()) {
            User tempUser = new User();
            tempUser.setLogin(resultSet.getString("Login"));
            tempUser.setPassword(resultSet.getString("Password"));
            tempUser.setAdmin(resultSet.getInt("Admin"));
            tempUser.setSettings(resultSet.getInt("Settings"));
            tempUser.setWallet(resultSet.getInt("Wallet"));
            return tempUser;
        }
        return null;
    }
}