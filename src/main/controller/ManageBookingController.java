package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.Booking;
import main.User;
import main.UserHolder;
import main.model.ManageBookingModel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageBookingController implements Initializable {

    ManageBookingModel manageBookingModel = new ManageBookingModel();
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
    private TableColumn<User, String> dateCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingTable.setPlaceholder(new Label("There are no bookings to display!"));
        bookingNumCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        tableNumCol.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        employeeIDCol.setCellValueFactory(new PropertyValueFactory<>("bookingNumber"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateString"));
        refreshTable();
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void confirm(ActionEvent event) throws IOException {
        if(bookingTable.getSelectionModel().getSelectedItem() != null)
        {
            int bookingId = bookingTable.getSelectionModel().getSelectedItem().getBookingNumber();
            manageBookingModel.confirm(bookingId);
            refreshTable();
        }
    }

    public void reject(ActionEvent event) throws IOException {
        if(bookingTable.getSelectionModel().getSelectedItem() != null)
        {
            int bookingId = bookingTable.getSelectionModel().getSelectedItem().getBookingNumber();
            manageBookingModel.reject(bookingId);
            refreshTable();
        }
    }

    public void refreshTable()
    {
        ObservableList<Booking> bookings = manageBookingModel.getAllBookings();
        bookingTable.setItems(bookings);
    }
}
