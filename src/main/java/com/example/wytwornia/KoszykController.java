package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class KoszykController implements Initializable {
    public TableView<Film> tableViewKoszyk;
    public TableColumn<Film, String> colTitle;
    public TableColumn<Film, String> colDirector;
    public TableColumn<Film, String> colGenre;
    public TableColumn<Film, String> colPrice;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
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

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
    }
}
