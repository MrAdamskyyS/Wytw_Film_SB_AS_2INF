package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class KoszykController implements Initializable {
    public TableView<Film> tableViewKoszyk;
    public TableColumn<Film, String> colTitle;
    public TableColumn<Film, String> colDirector;
    public TableColumn<Film, String> colGenre;
    public TableColumn<Film, String> colPrice;
    public Label lblDoZaplatyAmount;
    public Label lblDostepneSrodki;
    public RadioButton radioPortfel;
    private  float sum = 0;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
        setSumOfPrices();
        initializeText();
    }

    public void setSumOfPrices(){
         sum = 0;
        for(int i = 0; i<LoginController.user.koszyk.size();i++){
            sum = sum + LoginController.user.koszyk.get(i).getPrice();
        }
        sum = BigDecimal.valueOf(sum)
                .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                .floatValue();
        lblDoZaplatyAmount.setText(String.valueOf(sum)+" PLN");
    }

    public void initializeTable(){
        if(LoginController.user.koszyk.size()!=0) {
            try {
                colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
                colDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));
                colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
                colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
                tableViewKoszyk.setItems(LoginController.user.koszyk);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void initializeText(){
        lblDostepneSrodki.setText(LoginController.user.getWallet()+" PLN)");
    }

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
    }

    @FXML
    public void btnUsunZKoszyka() {
        LoginController.user.koszyk.remove(tableViewKoszyk.getSelectionModel().getSelectedItem());
        setSumOfPrices();
    }@FXML
    public void btnZaplac() throws SQLException {
        if(LoginController.user.koszyk.size()!=0) {
            if (radioPortfel.isSelected()) {
                if (LoginController.user.getWallet() >= sum) {
                    float roznica = LoginController.user.getWallet() - sum;
                    LoginController.user.setWallet(roznica);
                    LoginController.connection.insertQuery("UPDATE `users` SET `Wallet` =\"" + roznica + "\" WHERE `users`.`Login` =\"" + LoginController.user.getLogin() + "\" ;");
                    LoginController.user.koszyk.clear();
                    setSumOfPrices();
                    initializeText();
                    AlertBox.display("Dziękujemy za zakupy w naszym sklepie", "Sukces");
                } else {
                    AlertBox.display("Nie masz wystarczających środków na koncie", "Błąd");
                }
            } else //nie robimy przypadkow gdy ktos wybierze karte visa albo przelewy, zakladamy ze jak to wybiera to konczy pomyslnie platnosc
                AlertBox.display("Dziękujemy za zakupy w naszym sklepie", "Sukces");
                LoginController.user.koszyk.clear();
                setSumOfPrices();
                initializeText();
        } else {
            AlertBox.display("Koszyk jest pusty", "Błąd");
        }
    }
}
