package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AdminPanelController implements Initializable {
    public TextField txtFieldCompanyName;
    public TextField txtFieldTitle;
    public TextField txtFieldDirector;
    public TextField txtFieldYear;
    public TextField txtFieldGenre;
    public TextField txtFieldPrice;
    public TableView tableViewFilmyAdminPanel;
    public TableView tableViewUsersAdminPanel;
    public TableColumn<Film, String> colTitle;
    public TableColumn<Film, String> colPrice;
    public TableColumn<User, String> colLogin;
    public TableColumn<User, String> colPassword;
    public TableColumn<User, String> colAdmin;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeMoviesTable();
        initializeUsersTable();
    }

    public void initializeUsersTable(){
        colLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        colAdmin.setCellValueFactory(new PropertyValueFactory<>("Admin"));
        tableViewUsersAdminPanel.setItems(MainController.listaUzytkownikow);

    }

    public void initializeMoviesTable(){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tableViewFilmyAdminPanel.setItems(MainController.listaFilmow);
    }

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
    }

    @FXML
    public void btnDodajFilm() throws SQLException {
String title = txtFieldTitle.getText();
String director = txtFieldDirector.getText();
String year = txtFieldYear.getText();
String genre = txtFieldGenre.getText();
String price = txtFieldPrice.getText();
if(isValidYear(year) && isValidPrice(price)) { // najpierw sprawdzamy czy rok i cena sa prawidlowe
    if (LoginController.connection.checkForResultsQuery("SELECT `Title` FROM `movie` WHERE `Title`=\"" + title + " (" + year + ")\"")) { // jezeli taki film juz istnieje
        AlertBox.display("Taki film już istnieje!", "Błąd");
    } else {
        try {
        LoginController.connection.insertQuery("INSERT INTO `movie` (`ID` ,`Title` , `Director` ,`Genre` ,`Price`) VALUES (NULL, \""+title+" ("+year+")\", \""+director+"\", \""+genre+"\", \""+price+"\");");
        MainController.listaFilmow.add(new Film(title+" ("+year+")",director,genre,Float.parseFloat(price)));
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    }

    private boolean isValidYear(String s) {
        try{
        if (Integer.parseInt(s) >= 1895 && Integer.parseInt(s) < 2023) return true;
        else {
            AlertBox.display("Podaj prawidłowy rok", "Błąd");
            return false;
        }}
        catch (NumberFormatException e) {
            AlertBox.display("Podaj prawidłowy rok", "Błąd");
            return false;
        }
    }

    private boolean isValidPrice(String s){
                try {
                    if (Float.parseFloat(s) > 0) // sprawdzamy czy float jest dodatni
                        return true;
                } catch (NumberFormatException e) { // jezeli dostaniemy number format exception to zwracamy false
                    AlertBox.display("Podaj prawidłową cenę", "Błąd");
                    return false;
                }
                AlertBox.display("Podaj prawidłową cenę", "Błąd");
                return false; // false jezeli float bedzie ujemny
            }

    public void btnChangeCompanyName() {
        try {
            LoginController.connection.insertQuery("UPDATE `nazwafirmy` SET `NazwaFirmy` = '"+txtFieldCompanyName.getText()+"' WHERE `ID` =1;");
            AlertBox.display("Pomyślnie zmieniono nazwę firmy na: "+txtFieldCompanyName.getText()+"!","Sukces");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
