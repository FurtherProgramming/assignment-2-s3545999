package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.User;
import main.UserHolder;
import main.model.AdminReportModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminReportController implements Initializable {

    AdminReportModel adminReportModel = new AdminReportModel();
    @FXML
    DatePicker fromDate;

    @FXML
    DatePicker toDate;

    @FXML
    TextField fileName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void back(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    public void BookingReport(ActionEvent event) {
        if(toDate.getValue() != null && fromDate != null)
        {
            String[] header = {"BookingID", "Booked Date", "Desk ID", "Employee ID", "Checked In", "Admin Accepted"};
        }
    }

    public void accountReport(ActionEvent event) throws IOException {
        List<String[]> users = adminReportModel.getAllUsers();
        printCSV(users);
    }

    public void printCSV(List<String[]> list)
    {
        File file = new File(fileName.getText());
        try
        {
            FileWriter output = new FileWriter(file);
            for(int i = 0; i < list.size(); i++)
            {
                for(int j = 0; j < list.get(i).length; j++)
                {
                    output.write(list.get(i)[j]);
                    output.write(", ");
                }
                output.write("\n");
            }

        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File name is invalid!");
            alert.show();
        }

    }

}
