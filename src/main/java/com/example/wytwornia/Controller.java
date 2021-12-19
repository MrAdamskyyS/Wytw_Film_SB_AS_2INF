package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.sql.SQLException;

public class Controller  {

    public ScrollPane ScrollPaneFilmy;
    DatabaseConnection connection = new DatabaseConnection();
    public Pane paneUstawienia;
    public TextField txtFieldLogin;
    public TextField txtFieldPassword;
    Button anulujButton;
    AlertBox alertbox = new AlertBox();


    public Controller() throws IOException, SQLException {

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
}

    @FXML
    public void btnPortfelWyplata(){
    }

    @FXML
    public void btnPortfelWplata(){
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
    public void btnAnulujOnAction(ActionEvent event) {
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
    public void zaloguj(ActionEvent event) throws IOException, SQLException {
        String login= txtFieldLogin.getText();
        String password = txtFieldPassword.getText();
        boolean validData = connection.query("select login, Passwd from user where login=\""+login+"\" and Passwd=\""+password+"\";");
        if(validData) { // jezeli connection.query zwroci true, czyli znaleziono takiego usera z takim haslem to przelacz scene na mainScene.fxml
        Parent parent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        } else alertbox.display("Nie ma takiego użytkownika");
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