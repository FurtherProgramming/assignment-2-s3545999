package main.controller;

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
import main.model.ForgotPasswordModel;
import main.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    public ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();

    @FXML
    TextField TXTusername;



    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (forgotPasswordModel.isDbConnected()){
            // isConnected.setText("Connected");
        }else{
            // isConnected.setText("Not Connected");
        }

    }

    public void submitUsernameButton(ActionEvent event) throws IOException {

        try
        {
            if (!forgotPasswordModel.userExists(TXTusername.getText()))
            {
                forgotPasswordModel.getUser(TXTusername.getText());
            }
        }
        catch (SQLException e)
        {

        }
    }
}
