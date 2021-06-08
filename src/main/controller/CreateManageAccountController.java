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
import main.model.CreateManageAccountModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateManageAccountController implements Initializable {

    CreateManageAccountModel createManageAccountModel = new CreateManageAccountModel();

    User user;
    Boolean adminUpdate;
    Boolean newAccount;
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

        newAccount = CreateManageHolder.getInstance().getNewAccount();

        List<String> questions = createManageAccountModel.getSecQuestions();
        secQuestion.getItems().addAll(questions);
        CreateManageHolder holder = CreateManageHolder.getInstance();
        newAccount = holder.getNewAccount();
        adminUpdate = holder.getAdmin();
        if(holder.getUser() != null)
        {
            user = holder.getUser();
        }

        if(adminUpdate != true)
        {
            AdminTXT.setVisible(false);
            admin.setVisible(false);
        }

        if(newAccount == true)
        {
            HeaderTXT.setText("Create an Account");
        }
        else if(user != null)
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

            if(adminUpdate == true)
            {
                admin.getItems().add("Yes");
                admin.getItems().add("No");
                if(user.getAdmin() == true)
                {
                    admin.getSelectionModel().select(0);
                }
            }
        }
    }


    public void back(ActionEvent event) throws IOException {
        Parent createAccParent;
        if(newAccount == true)
        {
            createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        }
        else if(adminUpdate == true)
        {
            createAccParent = FXMLLoader.load(getClass().getResource("../ui/CreateManageAccount.fxml"));
        }
        else
        {
            createAccParent = FXMLLoader.load(getClass().getResource("../ui/EmployeeHomepage.fxml"));
        }
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));

        newStage.show();
        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void Submit(ActionEvent event)
    {
        if(password.getText().equals(confirmPassword.getText())
        && secAnswer.getText().equals(confirmAnswer.getText())
        && checkAcceptable())
        {

            if(newAccount == true)
            {
                user = new User();
                user.setAdmin(false);
            }

            user.setFirstName(firstName.getText());

            System.out.println(user.getFirstName());

            user.setLastName(lastName.getText());
            user.setUserName(username.getText());
            user.setPassword(password.getText());
            user.setSecretQAns(secQuestion.getValue());
            user.setSecretQAns(secAnswer.getText());
            if(adminUpdate == true)
            {
                if(admin.getValue() == "Yes")
                {
                    user.setAdmin(true);
                }
                else
                {
                    user.setAdmin(false);
                }
            }

            if(newAccount == true)
            {
                System.out.println("Hello");
                createManageAccountModel.addUser(user);
            }
            else
            {
                System.out.println("Hello2");
                createManageAccountModel.updateUser(user);

                if (user.getEmployeeId() == UserHolder.getInstance().getUser().getEmployeeId())
                {
                    UserHolder.getInstance().setUser(user);
                }
            }

        }
    }

    private boolean checkAcceptable()
    {
        boolean accept = true;
        if(firstName.getText().equals(""))
        {
            return false;
        }
        return true;
    }
}
