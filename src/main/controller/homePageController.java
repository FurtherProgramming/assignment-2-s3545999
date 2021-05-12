package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.User;
import main.UserHolder;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    private User user;

    @FXML
    private Label TXTwelcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserHolder holder = UserHolder.getInstance();

        user = holder.getUser();
        System.out.println(user.getfName());

        TXTwelcome.setText("Welcome " + user.getfName());
    }

    public homePageController() {

    }



}
