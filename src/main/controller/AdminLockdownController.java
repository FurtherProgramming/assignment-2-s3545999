package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.CreateManageHolder;
import main.User;
import main.UserHolder;
import main.model.AdminAccountsModel;
import main.model.AdminLockdownModel;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminLockdownController implements Initializable {

    AdminLockdownModel lockdownModel = new AdminLockdownModel();

    Boolean isLockedDown;
    Boolean isAvailable;

    @FXML
    Label situation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isLockedDown = lockdownModel.isLockdown();
        isAvailable = lockdownModel.isAllAvailable();
        if(isAvailable)
        {
            situation.setText("All desks are available");
        }
        else if(isLockedDown)
        {
            situation.setText("All desks are lockedDown");
        }
        else
        {
            situation.setText("Covid safe desk allocation");
        }
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void covidSafe(ActionEvent event){
        if(isLockedDown || isAvailable)
        {
            if(confirmChange())
            {
                if(isAvailable)
                {
                    lockdownModel.cancelAllBookings();
                }
                lockdownModel.setCovidSafe();
                reload(event);
            }
        }
        else
        {
            currentInfo();
        }
    }

    public void covidLockdown(ActionEvent event){
        if(!isLockedDown)
        {
            if(confirmChange())
            {
                lockdownModel.cancelAllBookings();
                lockdownModel.setLockdown();
                reload(event);
            }
        }
        else
        {
            currentInfo();
        }
    }

    public void allDesks(ActionEvent event) {
        if(!isAvailable)
        {
            if(confirmChange())
            {
                lockdownModel.setAllAvailable();
                reload(event);
            }
        }
        else
        {
            currentInfo();
        }

    }

    private boolean confirmChange()
    {
        boolean confirmed = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String text = "Are you sure you want to change the covidsafe settings?\n" +
                "Increasing the covidsafe level will cancel all bookings!";
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK)
        {
            confirmed = true;
        }
        return confirmed;
    }

    private void currentInfo()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String text = "This is your current lockdown level";

        alert.setContentText(text);
        alert.show();
    }

    private void reload(ActionEvent event)
    {
        try {
        Parent createAccParent = null;
        createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminLockdown.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
