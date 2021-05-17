package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.model.ChangePasswordModel;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ChangePasswordController implements Initializable {

    public ChangePasswordModel changePass = new ChangePasswordModel();

    @FXML
    TextField Password;
    @FXML
    TextField ConfirmPassword;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Scene createAccScene = new Scene(createAccParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccScene);
        window.show();
    }

    public void SubmitButtonPushed(ActionEvent event) throws IOException {
        // go to model return if the account is created successfully

        String password = Password.getText();
        String confirmPassword = ConfirmPassword.getText();

        if (password != "" && confirmPassword.equals(password))
        {
            changePass.setPassword(password);
        }
        else
        {
            System.out.println("Fail");
        }
    }
}