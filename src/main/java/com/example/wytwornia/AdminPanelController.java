package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminPanelController {
    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
    }
}
