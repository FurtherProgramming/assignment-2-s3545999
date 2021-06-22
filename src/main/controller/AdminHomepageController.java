package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.User;
import main.UserHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Homepage of an admin
public class AdminHomepageController implements Initializable {

    private User user;

    @FXML
    private Label TXTwelcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserHolder holder = UserHolder.getInstance();

        user = holder.getUser();
        String name = user.getFirstName();
        String fname = name.substring(0, 1).toUpperCase() + name.substring(1);
        TXTwelcome.setText("Welcome " + fname);
    }

    // Open booking management
    public void bookingManagement(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminManageBookings.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 900, 600));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Open account management
    public void AccountManage(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminAccounts.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Open report page
    public void AdminReports(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminReport.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Open lockdown page
    public void covidLockdown(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminLockdown.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Logout button
    public void logout(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        UserHolder user = UserHolder.getInstance();
        user.setUser(null);

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
