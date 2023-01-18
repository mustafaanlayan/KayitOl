package com.example.kayitol;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DbUtilites {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String kullanici_adi, String favKanal) {
        Parent root = null;

        if (kullanici_adi != null && favKanal != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DbUtilites.class.getResource(fxmlFile));
                root = loader.load();
                LoggedInController loggedInController = loader.getController();
                loggedInController.setUserInformation(kullanici_adi, favKanal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root=FXMLLoader.load(DbUtilites.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }
    public static void signUpKullanici(ActionEvent event, String kullanici_adi,String sifre,String favKanal){
        Connection connection = null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckKulaniciExists=null;
        ResultSet resultSet=null;

        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","root","");
            psCheckKulaniciExists=connection.prepareStatement("SELECT * FROM kullaniciler WHERE kullanici_adi=?");
            psCheckKulaniciExists.setString(1, kullanici_adi);
            resultSet=psCheckKulaniciExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Mevcut Kullanıcı");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Bu Kullanıcı Adını Kullanamassınız");
                alert.show();
            }else {
                psInsert=connection.prepareStatement("INSERT INTO kullaniciler (kullanici_adi,sifre,favKanal VALUES(?, ?, ?)");
                psInsert.setString(1, kullanici_adi);
                psInsert.setString(2, sifre);
                psInsert.setString(3, favKanal);
                psInsert.executeUpdate();

                changeScene(event, "Loggen-in.fxml","Hoş Geldiniz",kullanici_adi,sifre);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckKulaniciExists !=null){
                try {
                    psCheckKulaniciExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psInsert !=null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection !=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
public static  void logInKullanici(ActionEvent event,String kullanici_adi,String sifre){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;

        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx","root","");
            preparedStatement=connection.prepareStatement("SELECT sifre ,favKanal FROM kullanicilar WHERE kullanici_adi=?");
            preparedStatement.setString(1, kullanici_adi);
            resultSet=preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("Kullanıcı Adı Veri Tabanında Bulunamadı");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Kimlik Bilgileri Doğru ");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedSifre=resultSet.getString("sifre");
                    String retrievedKanal=resultSet.getString("favKanal");
                    if (retrievedSifre.equals(sifre)){
                        changeScene(event,"Loggen-in.fxml","Hoş Geldiniz",kullanici_adi,retrievedKanal);
                    }else {
                        System.out.println("Şifre Yanlış" );
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Şifre Bilgileri Doğru");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
}

}