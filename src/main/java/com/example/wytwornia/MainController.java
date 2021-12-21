package com.example.wytwornia;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public  class MainController implements Initializable {

    public ScrollPane ScrollPaneFilmy;
    public Pane paneUstawienia;
    public Label labelMainPortfelAmount;
    public Label labelLogin = new Label();
    public Label labelNazwaFirmy;
    static AlertBox alertbox = new AlertBox();


    public MainController() {

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelLogin.setText(LoginController.user.getLogin()); // ustawienie loginu w lewym gornym na login obiektu user
        labelMainPortfelAmount.setText(String.valueOf(LoginController.user.getWallet()) + "PLN"); // wartosc portfela z obiektu user
        try {
            labelNazwaFirmy.setText(DatabaseConnection.returnNazwaFirmy("select * from nazwafirmy")); // ustawienie nazwy firmy na gorze na nazwe firmy z bazy, przyda sie przy zmianach nazy firmy
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void btnUstawieniaAction() {
        ScrollPaneFilmy.setVisible(false);
        paneUstawienia.setVisible(true);
    }

public void newWindow(String file) throws IOException {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    Parent root = FXMLLoader.load(getClass().getResource(file));
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
    stage.setOnCloseRequest(e->{

    });


}



    @FXML
    public void openPortfel() throws IOException {
        newWindow("portfel.fxml");
    }

    @FXML
    public void btnFilmyonAction() {
        paneUstawienia.setVisible(false);
        ScrollPaneFilmy.setVisible(true);
    }

    @FXML
    public void openAdminPanel() throws IOException {
        newWindow("adminPanel.fxml");
    }

    @FXML
    public static void btnAnulujOnAction(ActionEvent event) {
        Window window =   ((Node)(event.getSource())).getScene().getWindow();
        if (window instanceof Stage){
            ((Stage) window).close();
        }
    }

    @FXML
    public void openKoszyk() throws IOException {
        newWindow("koszyk.fxml");
    }

@FXML
   public void changeSceneToLogin(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }


}