package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.CreateAccModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import main.User;


public class CreateAccController implements Initializable {

    ObservableList list = FXCollections.observableArrayList();


    public CreateAccModel createAcc = new CreateAccModel();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtlastName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtConfirmPass;
    @FXML
    private ChoiceBox SecretQSelect;
    @FXML
    private TextField txtSecretQAns;





    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        fillSecretQ();
    }

    public void backButtonPressed(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Scene createAccScene = new Scene(createAccParent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccScene);
        window.show();
    }

    public void submitButtonPushed(ActionEvent event) throws IOException {
        // go to model return if the account is created successfully

        System.out.println(txtPassword.getText());
        System.out.println(txtConfirmPass.getText());
        boolean error = false;
        String errorMessage = "";

        if (txtFirstName.getText() == null || txtlastName.getText() == null ||
                txtUsername.getText() == null || txtPassword.getText() == null || txtConfirmPass.getText() == null ||
                SecretQSelect.getValue() == null || txtSecretQAns.getText() == null)
        {
            errorMessage = "Required Field Missing";
            error = true;
        }
        else if (!txtPassword.getText().equals(txtConfirmPass.getText()))
        {
            errorMessage = "Fail, passwords do not match";
            error = true;
        }
        else if (createAcc.userTaken(txtUsername.getText()))
        {
            errorMessage = "Fail, username is taken";
            error = true;
        }
        else
        {
            User checkUser = new User(txtFirstName.getText(), txtlastName.getText(),
                    txtUsername.getText(), txtPassword.getText(), (String) SecretQSelect.getValue(), txtSecretQAns.getText());
            System.out.println("User Created");
            boolean created = createAcc.addUser(checkUser);
            if (created)
            {
                // Show success message tab then return to login
                Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/Welcome.fxml"));
                Scene createAccScene = new Scene(createAccParent);

                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(createAccScene);
                window.show();

                Stage popupstage = new Stage();
                popupstage.setTitle("Success");
                popupstage.setScene(new Scene(new TextField("Success"), 100, 100));
                popupstage.show();
                popupstage.toFront();
            }
            else
            {
                errorMessage = "User cannot be added to the database";
                error = true;
            }
        }
        if (error == true)
        {
            Stage popupstage = new Stage();
            popupstage.setTitle("Failure");
            popupstage.setScene(new Scene(new TextField(errorMessage), 200, 200));
            popupstage.show();
        }

    }

    private void fillSecretQ()
    {
        list.removeAll(list);
        String option1 = "What is your Mothers Name?";
        String option2 = "What is your Dad's Name?";
        list.addAll(option1,option2);
        SecretQSelect.getItems().addAll(list);
    }
}
