package com.example.wytwornia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {


            Parent root = FXMLLoader.load(getClass().getResource("loginScene.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);


  //  DatabaseConnection connection = new DatabaseConnection();
//connection.query();
     //   connection.connect();
            stage.show();


    }


    public static void main(String[] args) throws IOException {launch();}
}