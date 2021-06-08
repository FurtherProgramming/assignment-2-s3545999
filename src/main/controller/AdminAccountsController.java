package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.CreateManageHolder;
import main.User;
import main.model.AdminAccountsModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminAccountsController implements Initializable {

    AdminAccountsModel manageAccount = new AdminAccountsModel();

    @FXML
    private TableView<User> accountTable;
    @FXML
    private TableColumn<User, Integer> employeeIdCol;
    @FXML
    private TableColumn<User, String> fNameCol;
    @FXML
    private TableColumn<User, String> surnameCol;
    @FXML
    private TableColumn<User, String> usernameCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> users = manageAccount.getAllUsers();
        accountTable.setItems(users);
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        accountTable.setItems(users);
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent, 600, 400));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void select(ActionEvent event) throws IOException {

        if(accountTable.getSelectionModel().getSelectedItem() != null)
        {
            User user = manageAccount.getUser(accountTable.getSelectionModel().getSelectedItem().getEmployeeId());
            CreateManageHolder holder = CreateManageHolder.getInstance();
            holder.setNewAccount(false);
            holder.setAdmin(true);
            holder.setUser(user);

            Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/CreateManageAccount.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(createAccParent));
            newStage.show();

            final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        }
    }

}
