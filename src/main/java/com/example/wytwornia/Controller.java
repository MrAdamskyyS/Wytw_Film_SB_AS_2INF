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
    User user;
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
    public void setUser(String login) throws SQLException { // tworzy obiekt klasy User z danymi pobranymi z bazy
        user = connection.dodajUzytkownika(login);
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
        boolean validData = connection.searchQuery("select Login, Password from users where login=\""+login+"\" and Password=\""+password+"\";");
        if(validData) { // jezeli connection.query zwroci true, czyli znaleziono takiego usera z takim haslem to przelacz scene na mainScene.fxml
        setUser(login); // przypisanie obiektowi user Klasy User, utworzonemu na samej gorze (line 26) danych z poprawnie zalogowanego uzytkownika
        Parent parent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        } else alertbox.display("Nieprawidłowy login lub hasło");
    }
@FXML
public void zarejestruj() throws SQLException {
    String login= txtFieldLogin.getText();
    String password = txtFieldPassword.getText();
    if(login.isEmpty() || password.isEmpty()) {
        alertbox.display("Podaj login i hasło");
        return;
    }
    boolean validData = connection.searchQuery("select login from users where login=\""+login+"\";");
    if(!validData) { // jezeli nie ma takiego usera, to go dodaj
        connection.insertQuery("INSERT INTO `users` (`UID`, `Login`, `Password`, `Settings`, `Wallet`, `Admin`) VALUES (NULL, '"+login+"', '"+password+"', '11', '0', '0')");
    } else alertbox.display("Taki użytkownik już istnieje");
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