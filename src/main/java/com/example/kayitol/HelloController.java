package com.example.kayitol;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController  implements Initializable {


    @FXML
    private TextField txtKullaniciAdi;

    @FXML
    private TextField txtSifre;

    @FXML
    private Button btnGiris;

    @FXML
    private  Button btnKayitOl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    btnGiris.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DbUtilites.logInKullanici(event,txtKullaniciAdi.getText(),txtSifre.getText());
        }
    });

    btnKayitOl.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DbUtilites.changeScene(event,"sign-up.fxml","Giriş",null,null);
        }
    });
    }
}