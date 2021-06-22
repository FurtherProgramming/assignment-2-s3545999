package main.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.User;
import main.model.ForgotPasswordModel;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Forgot password update
public class ForgotPasswordController implements Initializable {
    public ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel();

    boolean userName;
    boolean secQuestion;
    boolean password;
    User user;

    @FXML
    Label labelHeader;
    @FXML
    TextField textBox1;
    @FXML
    TextField textBox2;
    @FXML
    Label textBox1Label;
    @FXML
    Label textBox2Label;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        userName = true;
        secQuestion = false;
        password = false;

        textBox2.setVisible(false);
        textBox2Label.setVisible(false);
        labelHeader.setText("Enter your username");
    }

    // when submitted
    public void submitAnswer(ActionEvent event){

        if(userName == true)
        {
            userNameInput();
        }
        else if(secQuestion == true)
        {
            secQuestionInput();
        }
        else if(password == true)
        {
            passwordInput(event);
        }
    }

    // Take username input and validate
    public void userNameInput()
    {
        if(forgotPasswordModel.userExists(textBox1.getText())) {

            user = forgotPasswordModel.getUser(textBox1.getText());
            userName = false;
            secQuestion = true;
            String txt = "Your secret question is: " + user.getSecretQ();
            labelHeader.setText(txt);
            textBox1Label.setText("Answer");
            textBox1.promptTextProperty().set("Security Question Answer");
            textBox1.setText("");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username does not exist!");
            alert.show();
        }
    }


    // Get security question input
    private void secQuestionInput()
    {
        if(textBox1.getText().equals(user.getSecretQAns()))
        {
            secQuestion = false;
            password = true;

            textBox1.setText("");
            textBox1.promptTextProperty().set("New Password");
            labelHeader.setText("Input new password");

            textBox2Label.setVisible(true);
            textBox2Label.setText("Confirm New Password");
            textBox2.setVisible(true);
            textBox1Label.setText("New Password");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Security answer does not match!");
            alert.show();
        }
    }

    // Take password update
    private void passwordInput(ActionEvent event)
    {
        if(textBox1.getText().equals(textBox2.getText()) &&
                !textBox1.getText().contains(" ") &&
                !textBox1.getText().equals(""))
        {
            forgotPasswordModel.setPassword(textBox1.getText(), user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Password Successfully Changed!");
            alert.showAndWait();
            backButton(event);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Passwords do not match!");
            alert.show();
        }
    }

    // Go back
    public void backButton(ActionEvent event){
        Parent createAccParent = null;
        try {
            createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene createAccScene = new Scene(createAccParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccScene);
        window.show();
    }
}
