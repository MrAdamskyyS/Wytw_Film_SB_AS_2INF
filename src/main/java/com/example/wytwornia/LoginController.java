package com.example.wytwornia;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public static DatabaseConnection connection;
    static User user; // static zeby mozna bylo uzyc tego w innej klasie
    public TextField txtFieldLogin;
    public TextField txtFieldPassword;
    public Label labelNazwaFirmy;
    public LoginController() throws SQLException {
        connection = new DatabaseConnection();
    }

    private void setUser(String login) throws SQLException { // tworzy obiekt klasy User z danymi pobranymi z bazy
        user = connection.dodajUzytkownika(login);
    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
           labelNazwaFirmy.setText(connection.returnNazwaFirmy("select * from nazwafirmy"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void zaloguj(ActionEvent event) throws IOException, SQLException {
        String login= txtFieldLogin.getText();
        String password = txtFieldPassword.getText();
        boolean validData = connection.checkForResultsQuery("select Login, Password from users where login=\""+login+"\" and Password=\""+password+"\";");
        if(validData) { // jezeli connection.query zwroci true, czyli znaleziono takiego usera z takim haslem to przelacz scene na mainScene.fxml
            setUser(login); // przypisanie obiektowi user Klasy User, utworzonemu na samej gorze (line 26) danych z poprawnie zalogowanego uzytkownika
            Parent parent = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else MainController.alertbox.display("Nieprawidłowy login lub hasło","Błąd");

    }
    @FXML
    private void zarejestruj() throws SQLException {
        String login= txtFieldLogin.getText();
        String password = txtFieldPassword.getText();
        if(login.isEmpty() || password.isEmpty()) {
            MainController.alertbox.display("Podaj login i hasło","Błąd");
            return;
        }
        boolean validData = connection.checkForResultsQuery("select login from users where login=\""+login+"\";");
        if(!validData) { // jezeli nie ma takiego usera, to go dodaj
            connection.insertQuery("INSERT INTO `users` (`UID`, `Login`, `Password`, `Settings`, `Wallet`, `Admin`) VALUES (NULL, '"+login+"', '"+password+"', '11', '0', '0')");
        } else MainController.alertbox.display("Taki użytkownik już istnieje","Błąd");
    }


}
