package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;

import static javafx.scene.control.cell.ComboBoxTableCell.*;

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
        System.out.println(name.getText());
    }

}
