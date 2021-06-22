package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
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
import java.util.*;

public class CreateManageAccountController implements Initializable {

    CreateManageAccountModel createManageAccountModel = new CreateManageAccountModel();

    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

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
    TextField role;
    @FXML
    Label lastDeskLabel;
    @FXML
    CheckBox lastDeskCheck;
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

        if(adminUpdate != true || (holder.getUser() != null &&
                user.getEmployeeId() == UserHolder.getInstance().getUser().getEmployeeId()))
        {
            AdminTXT.setVisible(false);
            admin.setVisible(false);
            lastDeskLabel.setVisible(false);
            lastDeskCheck.setVisible(false);
        }
        else
        {
            admin.getItems().add("Yes");
            admin.getItems().add("No");
            if(user != null && user.getAdmin() == true)
            {
                admin.getSelectionModel().select("Yes");
            }
            else
            {
                admin.getSelectionModel().select("No");
            }
        }

        if(adminUpdate == true && holder.getUser().getAdmin() == false)
        {
            if(createManageAccountModel.checkIfLastDesk(holder.getUser()))
            {
                lastDeskLabel.setVisible(true);
                lastDeskCheck.setVisible(true);
            }
        }

        if(newAccount == true)
        {
            HeaderTXT.setText("Create an Account");
        }
        else if(user != null)
        {
            setUser();
        }
        initialiseValidation();
    }

    private void setUser()
    {
        CreateManageHolder holder = CreateManageHolder.getInstance();
        user = holder.getUser();
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        username.setText(user.getUserName());
        role.setText(user.getRole());
        password.setText(user.getPassword());
        confirmPassword.setText(user.getPassword());
        secQuestion.getSelectionModel().select(user.getSecretQ());
        secAnswer.setText(user.getSecretQAns());
        confirmAnswer.setText(user.getSecretQAns());
    }



    public void back(ActionEvent event){
        try {
            Parent createAccParent;
            if (adminUpdate == true) {
                createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminAccounts.fxml"));
            }
            else if (newAccount == true) {
                createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
            } else {
                createAccParent = FXMLLoader.load(getClass().getResource("../ui/EmployeeHomepage.fxml"));
            }
            Stage newStage = new Stage();
            newStage.setScene(new Scene(createAccParent));

            newStage.show();
            final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void Submit(ActionEvent event)
    {
        if(validateSubmission() && confirmation())
        {
            if(newAccount == true)
            {
                user = new User();
                user.setAdmin(false);
            }
            CreateManageHolder holder = CreateManageHolder.getInstance();


            user.setFirstName(firstName.getText());
            user.setLastName(lastName.getText());
            user.setUserName(username.getText());
            user.setRole(role.getText());
            user.setPassword(password.getText());
            user.setSecretQ(secQuestion.getValue());
            user.setSecretQAns(secAnswer.getText());


            if(adminUpdate == true)
            {
                System.out.println(admin.getSelectionModel().getSelectedIndex());
                if(admin.getSelectionModel().getSelectedIndex() == 0)
                {
                    user.setAdmin(true);
                }
                else
                {
                    user.setAdmin(false);
                }
            }
            try {
                if (newAccount == true) {
                    createManageAccountModel.addUser(user);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Account Successfully created!");

                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.show();
                    stage.setAlwaysOnTop(true);
                    stage.toFront();
                } else {

                    if(adminUpdate == true && holder.getUser().getAdmin() == false)
                    {
                        if(lastDeskCheck.isSelected())
                        {
                            createManageAccountModel.releaseLast(user);
                        }
                    }
                    createManageAccountModel.updateUser(user);
                    if (user.getEmployeeId() == UserHolder.getInstance().getUser().getEmployeeId()) {
                        UserHolder.getInstance().setUser(user);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
            back(event);
        }
    }

    private boolean confirmation()
    {
        boolean confirmed = true;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if(newAccount == true)
        {
            alert.setContentText("Are you sure you want to create the account?");
        }
        else if(adminUpdate == true)
        {
            alert.setContentText("Are you sure you want to update the account?");
        }
        else
        {
            alert.setContentText("Are you sure you want to update your account?");
        }

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() != ButtonType.OK)
        {
            confirmed = false;
        }
        return confirmed;
    }

    private boolean validateSubmission()
    {
        boolean check = true;
        String alertMessage = "";

        if(firstName.getText().equals("") || firstName.getText().contains(" ")||
                lastName.getText().equals("") || lastName.getText().contains(" "))
        {
            validate(firstName);
            validate(lastName);
            alertMessage = "Must have valid name!";
            check = false;
        }
        else if(username.getText().equals("") || username.getText().contains(" "))
        {
            validate(username);
            alertMessage = "Must have valid username!";
            check = false;
        }
        else if(role.getText().equals(""))
        {
            validate(role);
            alertMessage = "Must have a role!";
            check = false;
        }
        else if(user != null && !username.getText().equals(user.getUserName()) &&
                createManageAccountModel.checkusername(username.getText())
        || user == null && createManageAccountModel.checkusername(username.getText()))
        {
            alertMessage = "Username is taken!";
            username.pseudoClassStateChanged(errorClass, true);
            check = false;
        }
        else if(!password.getText().equals(confirmPassword.getText())
        || password.getText().equals("") || password.getText().contains(" "))
        {
            validate(password);
            validate(confirmPassword);
            alertMessage = "Invalid password!";
            check = false;
        }
        else if(secQuestion.getValue() == null)
        {
            alertMessage = "Invalid Security question!";
            check = false;
        }
        else if(!secAnswer.getText().equals(confirmAnswer.getText())
                || secAnswer.getText().equals("") || secAnswer.getText().contains(" "))
        {
            validate(secAnswer);
            validate(confirmAnswer);
            alertMessage = "Invalid security answer!";
            check = false;
        }

        if(check == false)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(alertMessage);
            alert.show();
        }

        return check;
    }


    private void initialiseValidation()
    {
        setUpValidation(firstName);
        setUpValidation(lastName);
        setUpValidation(username);
        setUpValidation(role);
        setUpValidation(password);
        setUpValidation(confirmPassword);
        setUpValidation(secAnswer);
        setUpValidation(confirmAnswer);
    }

    // Following code modified from ValidatingTextFieldExample, James D
    // found at https://stackoverflow.com/questions/24231610/javafx-red-border-for-text-field

    // Designed to set up the listener which watched for changes in the text fields
    // Enables errors to be shown on the text boxes
    private void setUpValidation(final TextField textField)
    {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                validate(textField);
            }
        });
    }

    // Updates the class to psuedoclass of the textfield if an error
    private void validate(TextField textField)
    {
        if(textField.getText().equals("") || textField.getText().contains(" "))
        {
            textField.pseudoClassStateChanged(errorClass, true);
        }
        else if(textField.equals(password) || textField.equals(confirmPassword))
        {
            if(!password.getText().equals(confirmPassword.getText()))
            {
                password.pseudoClassStateChanged(errorClass, true);
                confirmPassword.pseudoClassStateChanged(errorClass, true);
            }
            else
            {
                password.pseudoClassStateChanged(errorClass, false);
                confirmPassword.pseudoClassStateChanged(errorClass, false);
            }
        }
        else if(textField.equals(secAnswer) || textField.equals(confirmAnswer))
        {
            if(!secAnswer.getText().equals(confirmAnswer.getText()))
            {
                secAnswer.pseudoClassStateChanged(errorClass, true);
                confirmAnswer.pseudoClassStateChanged(errorClass, true);
            }
            else
            {
                secAnswer.pseudoClassStateChanged(errorClass, false);
                confirmAnswer.pseudoClassStateChanged(errorClass, false);
            }
        }
        else
        {
            textField.pseudoClassStateChanged(errorClass, false);
        }
    }
}
