package sample.controller;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Gender;
import sample.model.Model;
import sample.utils.Test;


public class ModalEditController {

    @FXML
    private TextField name;

    @FXML
    private TextField age;

    @FXML
    private ComboBox<Gender> gender;

    @FXML
    private CheckBox active;

    @FXML
    public void initialize() {
        ObservableList<Gender> genderList = FXCollections.observableArrayList(Gender.values());
        gender.setItems(genderList);
    }


    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addUser(ActionEvent actionEvent) {
        Long count =  Test.getLastId();
        Model model = new Model(
                count + 1,
                name.getText(),
                Long.parseLong(age.getText()),
                active.isSelected(),
                gender.getValue().getCode());
        System.out.println(model.toString());
        Test.postModel(model);

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
