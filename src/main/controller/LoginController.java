package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import main.CreateManageHolder;
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
                    createAccParent = FXMLLoader.load(getClass().getResource("../ui/EmployeeHomepage.fxml"));
                }
                else
                {
                    createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
                }

                Stage newStage = new Stage();
                newStage.setResizable(false);
                newStage.setScene(new Scene(createAccParent));

                newStage.show();

                final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect username and password combination!");
                alert.show();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // To create an account and set the holder instance
    public void createAccountButtonPushed(ActionEvent event) throws IOException
    {
        CreateManageHolder.getInstance().setNewAccount(true);
        CreateManageHolder.getInstance().setAdmin(false);
        CreateManageHolder.getInstance().setUser(null);

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/CreateManageAccount.fxml"));
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Update forgotten password
    public void forgotPassButton(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/ForgotPass.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.setResizable(false);
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
