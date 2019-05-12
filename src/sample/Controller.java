package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

import javax.swing.*;
import java.io.IOException;

public class Controller {

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
    private TableColumn<Model, Integer> ageColumn;

    @FXML
    private TableColumn<Model, Boolean> activeColumn;

    @FXML
    private TableColumn<Model, Gender> genderColumn;

    @FXML
    private void initialize() {
        initData();
        tableUsers.setEditable(true);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Model, String>("Name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit((CellEditEvent<Model, String> event) -> {
            TablePosition<Model, String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            Model model = event.getTableView().getItems().get(row);
            model.setName(newName);
        });
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ageColumn.setOnEditCommit((CellEditEvent<Model, Integer> event) -> {
            TablePosition<Model, Integer> pos = event.getTablePosition();
            Integer newAge = event.getNewValue();
            int row = pos.getRow();
            Model model = event.getTableView().getItems().get(row);
            model.setAge(newAge);
        });
        activeColumn.setCellValueFactory(param -> {
            Model person = param.getValue();
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(person.getActive());
            booleanProp.addListener((observable, oldValue, newValue) -> person.setActive(newValue));
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
            Model person = event.getTableView().getItems().get(row);

            person.setGender(newGender.getCode());
        });
        tableUsers.setItems(usersData);
    }

    private void initData() {
        usersData.add(new Model("Qwe", 1, Boolean.TRUE, Gender.FEMALE.getCode()));
        usersData.add(new Model("Qwe", 1, Boolean.TRUE, Gender.FEMALE.getCode()));
        usersData.add(new Model("Qwe", 1, Boolean.TRUE, Gender.FEMALE.getCode()));
        usersData.add(new Model("Qwe", 1, Boolean.TRUE, Gender.FEMALE.getCode()));
        usersData.add(new Model("Qwe", 1, Boolean.TRUE, Gender.FEMALE.getCode()));
    }

    public void showDialog(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("edit.fxml"));
        stage.setTitle("Новый пользователь");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
        stage.show();
    }
}
