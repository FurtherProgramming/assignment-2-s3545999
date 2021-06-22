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
import main.Booking;
import main.User;
import main.model.AdminManageBookingModel;

import java.awt.print.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminManageBookingController implements Initializable {

    AdminManageBookingModel adminManageBookingModel = new AdminManageBookingModel();
    private ArrayList<Booking> theBookings;

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<User, Integer> bookingNumCol;
    @FXML
    private TableColumn<User, Integer> tableNumCol;
    @FXML
    private TableColumn<User, Integer> employeeIDCol;
    @FXML
    private TableColumn<User, String> firstNameCol;
    @FXML
    private TableColumn<User, String> lastNameCol;
    @FXML
    private TableColumn<User, Date> dateCol;
    @FXML
    private TableColumn<User, Boolean> acceptCol;
    @FXML
    private TableColumn<User, Boolean> cancelCol;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingTable.setPlaceholder(new Label("There are no bookings to display!"));
        bookingNumCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        tableNumCol.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("adminAccepted"));
        cancelCol.setCellValueFactory(new PropertyValueFactory<>("canceled"));
        refreshTable();
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirm(ActionEvent event){

        if(bookingTable.getSelectionModel().getSelectedItem() != null)
        {

            Booking booking = bookingTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if(booking.isAdminAccepted())
            {
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setContentText("This booking is already accepted!");
                alertInfo.show();
            }
            else if(adminManageBookingModel.checkNotRejected(booking.getEmployeeID()))
            {
                String text = booking.getFirstName() + " " +booking.getLastName() +
                        " has a booking which has not been accepted or rejected.";
                alert.setContentText(text);
                alert.show();
            }
            else if(adminManageBookingModel.checkAccepted(booking.getEmployeeID()))
            {
                String text = booking.getFirstName() + " " +booking.getLastName() +
                        " already has a confirmed booking\n"
                        +"It must be cancelled before another can be accepted!";
                alert.setContentText(text);
                alert.show();
            }
            else
            {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                String text = "Are you sure you want to accept:\n";

                if (confirmation(text, booking)) {
                    int bookingId = booking.getBookingNumber();
                    adminManageBookingModel.confirm(bookingId);
                    refreshTable();
                }
            }
        }
    }

    public void reject(ActionEvent event){
        if(bookingTable.getSelectionModel().getSelectedItem() != null)
        {

            Booking booking = bookingTable.getSelectionModel().getSelectedItem();

            if(booking.isCanceled())
            {
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setContentText("This booking is already cancelled!");
                alertInfo.show();
            }
            else if(confirmation("Are you sure you want to reject:",booking))
            {
                int bookingId = booking.getBookingNumber();
                adminManageBookingModel.reject(bookingId);
                refreshTable();
            }
        }
    }

    public void delete(ActionEvent event)
    {
        if(bookingTable.getSelectionModel().getSelectedItem() != null) {
            Booking booking = bookingTable.getSelectionModel().getSelectedItem();
            int bookingId = booking.getBookingNumber();
            if (confirmation("Are you sure you want to delete", booking))
            {
                    adminManageBookingModel.delete(bookingId);
            }
            refreshTable();
        }
    }

    public boolean confirmation(String firstLine, Booking booking)
    {
        boolean confirm = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        String text = firstLine + "\nName: " +
        booking.getFirstName() + " " + booking.getLastName() +
        "\nDate: " + booking.getDate()
        + "\nTable Number: " + booking.getTableNumber();
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK){
            confirm = true;
        }
        return confirm;
    }


    public void refreshTable()
    {
        ObservableList<Booking> bookings = adminManageBookingModel.getAllBookings();
        bookingTable.setItems(bookings);
    }
}
