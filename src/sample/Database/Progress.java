package sample.Database;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;

import java.sql.SQLException;

public class Progress extends  Table {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty unit;
    public Progress(int id, String name, String unit){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.unit=new SimpleStringProperty(unit);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getUnit() {
        return unit.get();
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }
    public Progress(){}
    public static void  initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        javafx.scene.control.TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> unit = new TableColumn<>("Unit");
        id.setCellValueFactory(cellData->((Progress)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Progress)cellData.getValue()).nameProperty());
        unit.setCellValueFactory(cellData->((Progress)cellData.getValue()).unitProperty());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t->((Progress)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
        unit.setCellFactory(TextFieldTableCell.forTableColumn());
        unit.setOnEditCommit(t->((Progress)t.getTableView().getItems().get(t.getTablePosition().getRow())).setUnit(t.getNewValue()));
        table_view.getColumns().addAll(id,name,unit);
        try{
            table_view.getItems().addAll(dbController.getProgress());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }

}
