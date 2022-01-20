package com.example.wytwornia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;




public class PortfelController implements Initializable {
    public Label labelPortfelAmount;
    public TextField kwotaAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {initializeText();}

    private void initializeText(){
        labelPortfelAmount.setText(String.valueOf(LoginController.user.getWallet())); // wartosc portfela z obiektu user
    }

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
      MainController.btnAnulujOnAction(event);
    }
    private  boolean isNumeric(String str) { // sprawdzamy czy string jest floatem i czy jest dodatni
        try {
            if(Float.parseFloat(str)>0)
            return true;
        } catch(NumberFormatException e){
            return false;
        }
        return false;
    }

    @FXML
    public void btnPortfelWyplata() throws SQLException {
        if(!isNumeric(kwotaAmount.getText())) {
            AlertBox.display("Podaj prawidłową kwotę","Błąd");
            return;
        }
        if(Float.parseFloat(kwotaAmount.getText())<=Float.parseFloat(labelPortfelAmount.getText())){ // jezeli kwotaAmount <= ilosci dostepnych srodkow
            //oblicz roznice kwotaAmount i labelPortfelAmount a następnie zmien znowu na String(String.valueOf) zeby moc wpisac do labelPortfelAmount wartosc po odjeciu
            float roznica = (Float.parseFloat(labelPortfelAmount.getText())-Float.parseFloat(kwotaAmount.getText()));
            roznica = BigDecimal.valueOf(roznica) // aproksymacja do 2 miejsc
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                    .floatValue();
            labelPortfelAmount.setText(String.valueOf(roznica));
            //zmien w bazie portfel usera na nowa wartosc
            LoginController.connection.insertQuery("UPDATE `users` SET `Wallet` =\""+roznica+"\" WHERE `users`.`Login` =\""+LoginController.user.getLogin()+"\" ;");
            LoginController.user.setWallet(roznica); // ustaw wallet na obiekcie user na nowa wartosc
            AlertBox.display("Pomyślnie wypłaciłeś: "+kwotaAmount.getText()+"zł","Sukces");


            return;
        }
        AlertBox.display("Nie masz tyle środków!","Błąd");
        return;
    }

    @FXML
    public void btnPortfelWplata() throws SQLException {
        if(!isNumeric(kwotaAmount.getText())) {
            AlertBox.display("Podaj prawidłową kwotę","Błąd");
            return;
        }
        float suma = (Float.parseFloat(labelPortfelAmount.getText())+Float.parseFloat(kwotaAmount.getText()));
        suma = BigDecimal.valueOf(suma) //aproksymacja do 2 miejsc
                .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                .floatValue();
        labelPortfelAmount.setText(String.valueOf(suma));
        //zmien w bazie portfel usera na nowa wartosc
        LoginController.connection.insertQuery("UPDATE `users` SET `Wallet` =\""+suma+"\" WHERE `users`.`Login` =\""+LoginController.user.getLogin()+"\" ;");
        LoginController.user.setWallet(suma); // ustaw wallet na obiekcie user na nowa wartosc
        AlertBox.display("Pomyślnie wpłaciłeś: "+kwotaAmount.getText()+"zł","Sukces");
        return;

    }


}
