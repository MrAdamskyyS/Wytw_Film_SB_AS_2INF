package com.example.wytwornia;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.ResourceBundle;




public  class MainController  implements Initializable {
    public Button btnAdminPanel;
    public Pane paneFilmy;
    public Pane paneUstawienia;
    public Label labelMainPortfelAmount;
    public Label labelLogin = new Label();
    public Label labelNazwaFirmy;
    public TableView mainFilmyTable;
    public TableColumn<Film, String> colTitle;
    public TableColumn<Film, String> colDirector;
    public TableColumn<Film, String> colGenre;
    public TableColumn<Film, String> colPrice;
    private Object[] filmy;
    private ObservableList<Film> listaFilmow = FXCollections.observableArrayList();



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        adminVisibility();
        initializeText();
       initializeTable();
    }

    private void adminVisibility(){
        if(LoginController.user.getAdmin() == 1)  btnAdminPanel.setVisible(true);   // przyznanie adminowi dostepu do "admin panel"
        else btnAdminPanel.setVisible(false);
    }

    private void initializeText() {
        labelLogin.setText(LoginController.user.getLogin()); // ustawienie loginu w lewym gornym na login obiektu user
        labelMainPortfelAmount.setText(String.valueOf(LoginController.user.getWallet()) + "PLN"); // wartosc portfela z obiektu user
        try {
            labelNazwaFirmy.setText(LoginController.connection.returnNazwaFirmy("select * from nazwafirmy")); // ustawienie nazwy firmy na gorze na nazwe firmy z bazy, przyda sie przy zmianach nazy firmy
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {
            filmy = LoginController.connection.returnFilmy();
            String[] titleArray = (String[]) filmy[0];
            String[] directorArray = (String[]) filmy[1];
            String[] genreArray = (String[]) filmy[2];
            float[] priceArray = (float[]) filmy[3];
            for (int i = 0; i < titleArray.length; i++){
                listaFilmow.add(new Film(titleArray[i],directorArray[i],genreArray[i],priceArray[i]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mainFilmyTable.setItems(listaFilmow);
}

    @FXML
    private void btnUstawieniaAction() {
        paneFilmy.setVisible(false);
        paneUstawienia.setVisible(true);
    }

    private void newWindow(String file) {
        try {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource(file));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
}

    @FXML
    private void openPortfel() {
        try {
            newWindow("portfel.fxml");
        }
        catch (Exception e ) {
            e.printStackTrace();
        }

    }

    @FXML
    private void btnFilmyonAction() {
        paneUstawienia.setVisible(false);
        paneFilmy.setVisible(true);
    }

    @FXML
    private void openAdminPanel(){
        try {
            newWindow("adminPanel.fxml");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public static void btnAnulujOnAction(ActionEvent event) {
        Window window =   ((Node)(event.getSource())).getScene().getWindow();
        if (window instanceof Stage){
            ((Stage) window).close();
        }
    }

    @FXML
    private void updateInfo(){
        this.labelMainPortfelAmount.setText(String.valueOf(LoginController.user.getWallet()+"PLN"));
    }

    @FXML
    private void openKoszyk() throws IOException {
       newWindow("koszyk.fxml");

    }

    @FXML
    private void changeSceneToLogin(ActionEvent event) {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


}