package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    @FXML
    Label UsernameLabel;
    @FXML
    Button submitUsernameButton;
    @FXML
    Label SecretQuestion;
    @FXML
    TextField SecQAns;
    @FXML
    Button SubmitAnswer;
    @FXML
    Label txtSecQAns;
    @FXML
    Button backButtonPressed;


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
        String secQuestion = "";
        try {
            if (forgotPasswordModel.userExists(TXTusername.getText())) {
                secQuestion = forgotPasswordModel.getUser(TXTusername.getText());
                if (secQuestion != "") {
                    SecretQuestion.setText("Your secret question is: " + secQuestion);
                    SecretQuestion.setVisible(true);
                    SecQAns.setVisible(true);
                    SubmitAnswer.setVisible(true);
                    txtSecQAns.setVisible(true);
                    UsernameLabel.setVisible(false);
                    submitUsernameButton.setVisible(false);
                    TXTusername.setVisible(false);
                }
            }
            else
            {
                Stage popupstage = new Stage();
                popupstage.setTitle("Failure");
                popupstage.setScene(new Scene(new TextField("User does not exist"), 200, 200));
                popupstage.show();

            }
        }
        catch (SQLException e) {
            System.out.println(e);

        }

    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Scene createAccScene = new Scene(createAccParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccScene);
        window.show();
    }
}
