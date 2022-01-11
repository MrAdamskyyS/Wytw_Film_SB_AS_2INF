package com.example.wytwornia;
import javax.xml.transform.Result;
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

    public static Object[] returnFilmy() throws SQLException { // zwracamy 3 String tablice, dlatego musimy zwrocic tablice typu Object
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM `movie`");
        if (resultSet.next()) {
           resultSet.last(); // ustawiamy na ostatni element
            int rowCount = resultSet.getRow();// rowCount to numer rzędu ostatniego elementu, stad wiemy ile jest wyników
           String[] titleMovie = new String[rowCount];
           String[] directorMovie = new String[rowCount];
           String[] genreMovie = new String[rowCount];


            resultSet.first(); // przesuwamy kursor znowu na poczatek

                 for (int i = 0; i < rowCount; i++) {
                    titleMovie[i] = resultSet.getString("Title");
                    directorMovie[i] = resultSet.getString("Director");
                    genreMovie[i] = resultSet.getString("Genre");
                    resultSet.next();
            }
                 Object[] filmy = new Object[rowCount];
                 filmy[0] = titleMovie;  // pierwszy element tablicy typu Object to tablica typu String titleMovie zawierajaca tytuly
                 filmy[1] = directorMovie; // drugi element tablicy typu Object to tablica typu String directorMovie zawierajaca rezyserow
                 filmy[2] = genreMovie; // trzeci element tablicy typu Object to tablica typu String genreMovie zawierajaca gatunek filmu
            return filmy;
        }
        String[] tempString = new String[1];
        return tempString;
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