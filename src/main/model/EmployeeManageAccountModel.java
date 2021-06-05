package main.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.SQLConnection;
import main.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class EmployeeManageAccountModel {
    Connection connection;

    public EmployeeManageAccountModel(){
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    public List<String> getSecQuestions()
    {
        List<String> questions = FXCollections.observableArrayList();
        try {
            String query = "select * from SecurityQuestions";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next())
            {
                questions.add(resultSet.getString("question"));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return questions;
    }

}
