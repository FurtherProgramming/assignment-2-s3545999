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

public class AdminReportController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void BookingReport(ActionEvent event) throws IOException {


    }

    public void AccountReport(ActionEvent event) throws IOException {

    }
}
