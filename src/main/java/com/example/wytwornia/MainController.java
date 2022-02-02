package com.example.wytwornia;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;




public  class MainController  implements Initializable {
    public Button btnAdminPanel;
    public Pane paneFilmy;
    public Pane paneUstawienia;
    public Label labelMainPortfelAmount;
    public Label labelLogin = new Label();
    public Label labelNazwaFirmy;
    public TableView<Film> mainFilmyTable;
    public TableColumn<Film, String> colTitle;
    public TableColumn<Film, String> colDirector;
    public TableColumn<Film, String> colGenre;
    public TableColumn<Film, String> colPrice;
    public AnchorPane mainRootPane;
    public TextField searchbar;
    public static ObservableList<Film> listaFilmow = FXCollections.observableArrayList();
    public static ObservableList<User> listaUzytkownikow = FXCollections.observableArrayList(); //lista uzytkownikow tutaj, bo w admin panelu by sie za kazdym razem inicjalizowala przy odpaleniu
    public String fileMenuName;



    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        adminVisibility();
        initializeText();
       initializeTable();
       initializeUsers();

    }



    private void adminVisibility(){
        if(LoginController.user.getAdmin() == 1)  btnAdminPanel.setVisible(true);   // przyznanie adminowi dostepu do "admin panel"
        else btnAdminPanel.setVisible(false);
    }

    private void initializeText() {
        labelLogin.setText(LoginController.user.getLogin()); // ustawienie loginu w lewym gornym na login obiektu user
        labelMainPortfelAmount.setText(LoginController.user.getWallet() + " PLN"); // wartosc portfela z obiektu user
        try {
            labelNazwaFirmy.setText(LoginController.connection.returnNazwaFirmy("select * from nazwafirmy")); // ustawienie nazwy firmy na gorze na nazwe firmy z bazy, przyda sie przy zmianach nazy firmy
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeTable() {
        listaFilmow.clear();
        colTitle.setCellValueFactory(new PropertyValueFactory<>("Title"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("Director"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("Genre"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        try {

            Object[] filmy = LoginController.connection.returnFilmy();
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

        //Wyszukiwanie

        FilteredList<Film> filteredList = new FilteredList<>(listaFilmow, b->true);
        searchbar.textProperty().addListener((observable, oldValue, newValue)-> {
           filteredList.setPredicate(filmSearch -> {
               if(newValue.isEmpty() || newValue.isBlank() || newValue==null){
                   return true;
               }
               String searchKeyword = newValue.toLowerCase();
               if(filmSearch.getTitle().toLowerCase().indexOf(searchKeyword)!=-1){
                   return true;
               } else
                   return false;

           });
        });
        SortedList<Film> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(mainFilmyTable.comparatorProperty());
        mainFilmyTable.setItems(sortedData);

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
        this.labelMainPortfelAmount.setText(LoginController.user.getWallet()+" PLN");
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

@FXML
    private void btnDodajDoKoszyka(){
        if(mainFilmyTable.getSelectionModel().getSelectedItem()!=null) {
            LoginController.user.koszyk.add(mainFilmyTable.getSelectionModel().getSelectedItem());
            AlertBox.display("Dodano film do koszyka","Sukces");
        }
        else
            AlertBox.display("Nie wybrano filmu", "Błąd");
}
/*
    private void setStyleAndLanguage(){
        switch(LoginController.user.getSettings()){
            case 11:
                mainRootPane.getStyleClass().add("whiteTheme.css");


        }
    }
*/
    private void initializeUsers() {
        if(LoginController.user.getAdmin()==1){
            listaUzytkownikow.clear();
            try {
                Object[] users = LoginController.connection.returnUsers();
                String[] loginArray = (String[]) users[0];
                String[] passwordArray = (String[]) users[1];
                Integer[] adminArray = (Integer[]) users[2];

                for (int i = 0; i < loginArray.length; i++){
                    listaUzytkownikow.add(new User(loginArray[i],passwordArray[i],adminArray[i]));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}