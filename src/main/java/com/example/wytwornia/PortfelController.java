package com.example.wytwornia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class PortfelController implements Initializable {
    public Label labelPortfelAmount;
    public TextField kwotaAmount;

    @FXML
    public void btnAnulujOnAction(ActionEvent event) {
        MainController.btnAnulujOnAction(event);
    }
    public boolean isNumeric(String str) { // sprawdzamy czy string jest numerem i czy jest dodatnia

        try {
            if(Integer.parseInt(str)>0)
            return true;
        } catch(NumberFormatException e){
            return false;
        }
        return false;
    }


    public void btnPortfelWyplata() throws SQLException {
        if(!isNumeric(kwotaAmount.getText())) {
            AlertBox.display("Podaj prawidłową kwotę","Błąd");
            return;
        }
        if(Integer.parseInt(kwotaAmount.getText())<=Integer.parseInt(labelPortfelAmount.getText())){ // jezeli kwotaAmount <= ilosci dostepnych srodkow
            //oblicz roznice kwotaAmount i labelPortfelAmount a następnie zmien znowu na String(String.valueOf) zeby moc wpisac do labelPortfelAmount wartosc po odjeciu
            int roznica = Integer.parseInt(labelPortfelAmount.getText())-Integer.parseInt(kwotaAmount.getText());
            labelPortfelAmount.setText(String.valueOf(roznica));
            //zmien w bazie portfel usera na nowa wartosc
            DatabaseConnection.insertQuery("UPDATE `users` SET `Wallet` =\""+roznica+"\" WHERE `users`.`Login` =\""+LoginController.user.getLogin()+"\" ;");
            AlertBox.display("Pomyślnie wypłaciłeś: "+kwotaAmount.getText()+"zł","Sukces");
            return;
        }
        AlertBox.display("Nie masz tyle środków!","Błąd");
        return;
    }

    public void btnPortfelWplata() throws SQLException {
        if(!isNumeric(kwotaAmount.getText())) {
            AlertBox.display("Podaj prawidłową kwotę","Błąd");
            return;
        }
        int suma = Integer.parseInt(labelPortfelAmount.getText())+Integer.parseInt(kwotaAmount.getText());
        labelPortfelAmount.setText(String.valueOf(suma));
        //zmien w bazie portfel usera na nowa wartosc
        DatabaseConnection.insertQuery("UPDATE `users` SET `Wallet` =\""+suma+"\" WHERE `users`.`Login` =\""+LoginController.user.getLogin()+"\" ;");
        AlertBox.display("Pomyślnie wpłaciłeś: "+kwotaAmount.getText()+"zł","Sukces");
        return;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelPortfelAmount.setText(String.valueOf(LoginController.user.getWallet())); // wartosc portfela z obiektu user
    }
}
