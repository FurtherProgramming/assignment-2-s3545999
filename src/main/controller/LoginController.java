package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.User;
import main.UserHolder;
import main.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){

                Parent createAccParent;
                User user = UserHolder.getInstance().getUser();
                System.out.println(user.getAdmin());
                if(!user.getAdmin())
                {
                    createAccParent = FXMLLoader.load(getClass().getResource("../ui/Welcome.fxml"));
                }
                else
                {
                    createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
                }

                Stage newStage = new Stage();
                newStage.setScene(new Scene(createAccParent, 600, 400));

                newStage.show();

                final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccountButtonPushed(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/createAccount.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void forgotPassButton(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/ForgotPass.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }



}
