package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.User;
import main.UserHolder;
import main.model.cancelBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeManageAccountController implements Initializable {

    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    PasswordField confirmPassword;
    @FXML
    ChoiceBox<String> secQuestion;
    @FXML
    PasswordField secAnswer;
    @FXML
    PasswordField confirmAnswer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = UserHolder.getInstance().getUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        username.setText(user.getUserName());
        password.setText(user.getPassword());
        confirmPassword.setText(user.getPassword());
        secAnswer.setText(user.getSecretQAns());
        confirmAnswer.setText(user.getSecretQAns());
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/Welcome.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void Submit(ActionEvent event)
    {

    }
}
