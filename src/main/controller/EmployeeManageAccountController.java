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
import main.CreateManageHolder;
import main.User;
import main.UserHolder;
import main.model.EmployeeManageAccountModel;
import main.model.cancelBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeManageAccountController implements Initializable {

    EmployeeManageAccountModel employeeManageAccountModel = new EmployeeManageAccountModel();

    User user;
    boolean adminUpdate;
    @FXML
    Label HeaderTXT;
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
    @FXML
    Label AdminTXT;
    @FXML
    ChoiceBox<String> admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> questions = employeeManageAccountModel.getSecQuestions();
        secQuestion.getItems().addAll(questions);
        CreateManageHolder holder = CreateManageHolder.getInstance();

        if(holder.getNewAccount() == true)
        {
            HeaderTXT.setText("Create an Account");
        }
        else if(holder.getUser() != null)
        {
            user = holder.getUser();
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            username.setText(user.getUserName());
            password.setText(user.getPassword());
            confirmPassword.setText(user.getPassword());
            secQuestion.getSelectionModel().select(0);
            secAnswer.setText(user.getSecretQAns());
            confirmAnswer.setText(user.getSecretQAns());
        }

        if(holder.getAdmin() != true)
        {
            AdminTXT.setVisible(false);
            admin.setVisible(false);
        }
        else
        {
            admin.getItems().add("Yes");
            admin.getItems().add("No");
        }
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
        System.out.println(password.getText());
        System.out.println(confirmPassword.getText());
        System.out.println(secAnswer.getText());
        System.out.println(confirmAnswer.getText());

        if(password.getText().equals(confirmPassword.getText())
        && secAnswer.getText().equals(confirmAnswer.getText()))
        {
            System.out.println("Hello");

            boolean newUser = false;
            if(user == null)
            {
                newUser = true;
                user = new User();
            }
            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setUserName(username.getText());
            user.setPassword(password.getText());
            user.setSecretQAns(secQuestion.getValue());
            user.setSecretQAns(secAnswer.getText());
            if(adminUpdate == true && admin.getValue() == "yes")
            {
                user.setAdmin(true);
            }
            if(newUser)
            {
                employeeManageAccountModel.addUser(user);
            }
            else
            {
                employeeManageAccountModel.updateUser(user);
            }
            if(user.getEmployeeId() == UserHolder.getInstance().getUser().getEmployeeId())
            {
                UserHolder.getInstance().setUser(user);
            }
        }
    }
}
