package com.example.kayitol;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.kayitol.DbUtilites.*;

public class LoggedInController implements Initializable {


    @FXML

    private Button btnCikis;

    @FXML

    private Label lblHosGeldin;

    @FXML

    private Label lblKanal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnCikis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DbUtilites.changeScene(event,"hello-view.fxml","Giriş Yap",null, null);
            }
        });
    }

    public void setUserInformation(String kullaniciadi, String favKanal){
        lblHosGeldin.setText("Hoş Geldiniz" + kullaniciadi);
        lblKanal.setText("Favori Yotube Kanalınız " + favKanal);
    }
}
