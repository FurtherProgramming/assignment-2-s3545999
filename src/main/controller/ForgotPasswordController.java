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
    TextField txtSecQAns;
    @FXML
    Button SubmitAnswer;
    @FXML
    Label answer;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        SubmitAnswer.setVisible(false);
        txtSecQAns.setVisible(false);
        answer.setVisible(false);
        SecretQuestion.setVisible(false);
    }

    public void submitUsernameButton(ActionEvent event) throws IOException {
        String secQuestion = "";
        try {
            if (forgotPasswordModel.userExists(TXTusername.getText())) {
                forgotPasswordModel.selectUser(TXTusername.getText());
                secQuestion = forgotPasswordModel.getSecQuestion();
                if (secQuestion != "") {
                    viewSecretQuestion(secQuestion);
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

    public void SubmitAnswer(ActionEvent event) throws IOException
    {
        String secQAns = txtSecQAns.getText();
        try
        {
            if (forgotPasswordModel.checkSecAns(secQAns))
            {
                Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/ChangePass.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(createAccParent, 600, 400));
                newStage.show();

                final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.close();
            }
            else
            {
                System.out.println("Wrong Answer");
            }
        }
        catch (Exception e)
        {

        }

    }

    public void viewSecretQuestion(String secQuestion) {
        SecretQuestion.setText("Your secret question is: " + secQuestion);
        SecretQuestion.setVisible(true);
        txtSecQAns.setVisible(true);
        SubmitAnswer.setVisible(true);
        txtSecQAns.setVisible(true);
        UsernameLabel.setVisible(false);
        submitUsernameButton.setVisible(false);
        TXTusername.setVisible(false);
    }
}
