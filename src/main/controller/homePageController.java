package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import main.User;
import main.UserHolder;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    private User user;

    @FXML
    private Label TXTwelcome;

    @FXML
    private Button cancelBooking;

    @FXML
    private Button checkIn;

    @FXML
    private Button manageAccount;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserHolder holder = UserHolder.getInstance();

        user = holder.getUser();
        String name = user.getFirstName();
        String fname = name.substring(0, 1).toUpperCase() + name.substring(1);
        TXTwelcome.setText("Welcome " + fname);
    }

    public void makeBooking(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/SelectTable.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 900, 600));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void checkIn(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/CheckIn.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
    public void ManageAccount(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/ManageAccount.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void logout(ActionEvent event) throws IOException
    {
        UserHolder userHolder = UserHolder.getInstance();
        userHolder.setUser(null);
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/login.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();
    }
}
