package com.example.wytwornia;
import java.sql.*;

public class DatabaseConnection {

    private static  Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wytwornia_sbas", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseConnection() throws SQLException {
    }

    public static String returnNazwaFirmy(String wantedquery) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(wantedquery);
        if(resultSet.next()) return resultSet.getString("NazwaFirmy");
        else return null;
    }

    public static boolean checkForResultsQuery(String wantedquery) throws SQLException { // sprawdza czy dane zapytanie cos zwroci
        Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(wantedquery);
    if(resultSet.next()) return true; // jezeli cos znajdzie -> true
    else return false; // jezeli nic nie znajdzie -> false

}
    public static boolean insertQuery(String wantedquery) throws SQLException { //wykonujemy insert wantedquery , czyli wpisujemy cos do bazy
        Statement statement = connection.createStatement();
        statement.executeUpdate(wantedquery);
        return true;
    }

    public static User dodajUzytkownika(String login) throws SQLException { // znajdujemy uzytkownika, tworzymy obiekt i go zwracamy
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