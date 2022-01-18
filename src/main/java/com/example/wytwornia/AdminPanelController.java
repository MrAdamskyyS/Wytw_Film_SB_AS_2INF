package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AdminPanelController {
    public TextField txtFieldCompanyName;

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
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
