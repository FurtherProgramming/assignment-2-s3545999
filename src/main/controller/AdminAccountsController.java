package main.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.CreateManageHolder;
import main.User;
import main.UserHolder;
import main.model.AdminAccountsModel;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    @FXML
    private TableColumn<User, Boolean> adminCol;
    @FXML
    private TableColumn<User, Boolean> active;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<User> users = manageAccount.getAllUsers();
        accountTable.setItems(users);
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        adminCol.setCellValueFactory(new PropertyValueFactory<>("admin"));
        active.setCellValueFactory(new PropertyValueFactory<>("active"));
        accountTable.setItems(users);
    }

    public void back(ActionEvent event) throws IOException {

        Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminHomepage.fxml"));
        Stage newStage = new Stage();
        newStage.setScene(new Scene(createAccParent));
        newStage.show();

        final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void select(ActionEvent event){

        if(accountTable.getSelectionModel().getSelectedItem() != null)
        {
            User user = manageAccount.getUser(accountTable.getSelectionModel().getSelectedItem().getEmployeeId());
            CreateManageHolder holder = CreateManageHolder.getInstance();
            holder.setNewAccount(false);
            holder.setAdmin(true);
            holder.setUser(user);

            try {
                Parent createAccParent = FXMLLoader.load(getClass().getResource("../ui/CreateManageAccount.fxml"));
                Stage newStage = new Stage();
                newStage.setScene(new Scene(createAccParent));
                newStage.show();

                final Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.close();
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }

    public void activate(ActionEvent event){

        if(accountTable.getSelectionModel().getSelectedItem() != null)
        {
            User user = accountTable.getSelectionModel().getSelectedItem();

            boolean active = !user.getActive();

            String message = "";
            if(active == true)
            {
                message = "Are you sure you want to activate this account?";
            }
            else
            {
                message = "Are you sure you want to deactive this account?";
            }

            if(confirmUpdate(user, message))
            {
                manageAccount.updateActivityUser(active, user);
                reload(event);
            }
        }
    }

    public void add(ActionEvent event) throws IOException {

        CreateManageHolder holder = CreateManageHolder.getInstance();
        holder.setNewAccount(true);
        holder.setAdmin(true);
        holder.setUser(null);

        try {
            Parent createAccParent = null;
            createAccParent = FXMLLoader.load(getClass().getResource("../ui/CreateManageAccount.fxml"));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(createAccParent));
            newStage.show();

            final Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(ActionEvent event){

        if(accountTable.getSelectionModel().getSelectedItem() != null)
        {
            int currentUser = UserHolder.getInstance().getUser().getEmployeeId();

            if(accountTable.getSelectionModel().getSelectedItem().getEmployeeId() != currentUser) {

                User user = accountTable.getSelectionModel().getSelectedItem();

                String message = "Are you sure you want to delete this account?";

                if (confirmUpdate(user, message)) {
                    manageAccount.deleteUser(user.getEmployeeId());
                    reload(event);
                }
            }

        }

    }

    private boolean confirmUpdate(User user, String message)
    {
        int currentUser = UserHolder.getInstance().getUser().getEmployeeId();
        boolean confirmed = false;
        if(accountTable.getSelectionModel().getSelectedItem().getEmployeeId() != currentUser)
        {
            String text = message + "\n" +
                    "Employee ID: " +
                    user.getEmployeeId() + "\n" + "Name: " +
                    user.getFirstName() + " " + user.getLastName() + "\n" +
                    "Username: " + user.getUserName() + "\n"
                    + "Admin Status: " + user.getAdmin();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(text);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)
            {
                confirmed = true;
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You can't delete or deactivate your own account!");
            alert.show();
        }
        return confirmed;
    }

    private void reload(ActionEvent event)
    {
        try {
        Parent createAccParent = null;
        createAccParent = FXMLLoader.load(getClass().getResource("../ui/AdminAccounts.fxml"));
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
