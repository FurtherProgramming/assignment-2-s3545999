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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

// Allow admins to make reports and view desks
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
        fromDate.setValue(LocalDate.now());
        toDate.setValue(LocalDate.now());
    }

    // Back
    public void back(ActionEvent event) throws IOException {
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    // View desk allocations
    public void desks(ActionEvent event) throws IOException{
        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/ManageBooking.fxml"));
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    // Get a booking report
    public void BookingReport(ActionEvent event) {
        if(toDate.getValue() != null && fromDate != null)
        {
            ArrayList<ArrayList<String>> bookings = adminReportModel.getBookingBetweenDates(fromDate.getValue(), toDate.getValue());
            printCSV(bookings);
        }
    }

    // Get alll account report
    public void accountReport(ActionEvent event) throws IOException {
        ArrayList<ArrayList<String>> users = adminReportModel.getAllUsers();
        printCSV(users);
    }

    // Print a CSV file
    public void printCSV(ArrayList<ArrayList<String>> list)
    {
        String fileToWrite = fileName.getText();
        if(fileToWrite.endsWith(".csv"))
        {
            String fileWithFolder = "Reports/" + fileToWrite;
            File file = new File(fileWithFolder);
            try
            {
                FileWriter output = new FileWriter(file);
                for(int i = 0; i < list.size(); i++)
                {
                    for(int j = 0; j < list.get(i).size(); j++)
                    {
                        if(j != 0)
                        {
                            output.write(", ");
                        }
                        output.write(list.get(i).get(j));
                    }
                    output.write("\n");
                }
                output.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String alertText = "File has been written to: " + fileWithFolder;
                alert.setContentText(alertText);
                alert.show();
            }
            catch(Exception e)
            {
                System.out.println(e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("File name is invalid!");
                alert.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File must end with '.csv'");
            alert.show();
        }
    }
}
