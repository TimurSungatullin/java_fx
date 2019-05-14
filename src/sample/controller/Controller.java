package sample.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import sample.model.Gender;
import sample.model.Model;
import sample.utils.Test;

import java.io.IOException;

public class Controller {

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Model, Long> idColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button refreshButton;

    private ObservableList<Model> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Model> tableUsers;

    @FXML
    private TableColumn<Model, String> nameColumn;

    @FXML
    private TableColumn<Model, Long> ageColumn;

    @FXML
    private TableColumn<Model, Boolean> activeColumn;

    @FXML
    private TableColumn<Model, Gender> genderColumn;

    @FXML
    private void initialize() {
        tableUsers.setEditable(true);
        nameColumn.setOnEditCommit((CellEditEvent<Model, String> event) -> {
            TablePosition<Model, String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            Model model = event.getTableView().getItems().get(row);
            model.setName(newName);
            Test.update(model);
        });
//        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        ageColumn.setOnEditCommit((CellEditEvent<Model, Long> event) -> {
            TablePosition<Model, Long> pos = event.getTablePosition();
            Long newAge = event.getNewValue();
            int row = pos.getRow();
            Model model = event.getTableView().getItems().get(row);
            model.setAge(newAge);
            Test.update(model);
        });
        activeColumn.setCellValueFactory(param -> {
            Model model = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(model.getActive());
            booleanProp.addListener((observable, oldValue, newValue) -> {
                model.setActive(newValue);
                Test.update(model);
            });
            return booleanProp;
        });
        activeColumn.setCellFactory(p -> {
            CheckBoxTableCell<Model, Boolean> cell = new CheckBoxTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        ObservableList<Gender> genderList = FXCollections.observableArrayList(Gender.values());
        genderColumn.setCellValueFactory(param -> {
            Model person = param.getValue();
            String genderCode = person.getGender();
            Gender gender = Gender.getByCode(genderCode);
            return new SimpleObjectProperty<>(gender);
        });
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn(genderList));

        genderColumn.setOnEditCommit((CellEditEvent<Model, Gender> event) -> {
            TablePosition<Model, Gender> pos = event.getTablePosition();

            Gender newGender = event.getNewValue();

            int row = pos.getRow();
            Model model = event.getTableView().getItems().get(row);
            model.setGender(newGender.getCode());
            Test.update(model);
        });
        initData();
    }

    public void initData() {
        usersData = FXCollections.observableArrayList(Test.getAll());
        tableUsers.setItems(usersData);
    }

    public void showDialog(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/edit.fxml"));
        stage.setTitle("Новый пользователь");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.showAndWait();
    }

    public void deleteRow() {
        Model model = tableUsers.getSelectionModel().getSelectedItem();
        Test.deleteById(model.getId());
        initData();
    }
}
