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
import sample.ControllerClasses.ViewerController;

import java.sql.SQLException;
import java.util.List;

public class Manager extends Table {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty team_id;
    private StringProperty team_name;

    public String getTeam_name() {
        return team_name.get();
    }

    public StringProperty team_nameProperty() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name.set(team_name);
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

    public int getTeam_id() {
        return team_id.get();
    }

    public IntegerProperty team_idProperty() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id.set(team_id);
    }

    public Manager(int id, String  name, int team_id) {
        this.id =new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.team_id=new SimpleIntegerProperty(team_id);
    }
    public Manager(int id, String  name, String team_name) {
        this.id =new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.team_name=new SimpleStringProperty(team_name);
    }
    public Manager(){}
    public static void  initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        javafx.scene.control.TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, Number> team_id = new TableColumn<>("Team id");
        id.setCellValueFactory(cellData->((Manager)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Manager)cellData.getValue()).nameProperty());
        team_id.setCellValueFactory(cellData->((Manager)cellData.getValue()).team_idProperty());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t->((Manager)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        team_id.setCellValueFactory(p -> new SimpleIntegerProperty(((Manager)p.getValue()).getTeam_id()));
        team_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        team_id.setOnEditCommit(t->((Manager)t.getTableView().getItems().get(t.getTablePosition().getRow())).setTeam_id(t.getNewValue().intValue()));
        table_view.getColumns().addAll(id,name,team_id);
        try{
            table_view.getItems().addAll(dbController.getManager());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }
    public static void initializeViewer(TableView<Table> table_view){
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        javafx.scene.control.TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> team_name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData->((Manager)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Manager)cellData.getValue()).nameProperty());
        team_name.setCellValueFactory(cellData->((Manager)cellData.getValue()).team_nameProperty());
        if(ViewerController.currentTeam!=null){
        table_view.getColumns().addAll(name);
        try{

                table_view.getItems().addAll(dbController.showManagers(ViewerController.currentTeam));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        }else{
            table_view.getColumns().addAll(name,team_name);
            try{
                table_view.getItems().addAll(dbController.showMangers());
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        dbController.closeConnection();
    }
    public static void initializeView(TableView<Table> table_view, List<Table> tl){
        table_view.getItems().clear();
        table_view.getColumns().clear();
        javafx.scene.control.TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> team_name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData->((Manager)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Manager)cellData.getValue()).nameProperty());
        team_name.setCellValueFactory(cellData->((Manager)cellData.getValue()).team_nameProperty());
        table_view.getColumns().addAll(name,team_name);
        table_view.getItems().addAll(tl);
       // utilFX.autoResizeColumns(table_view);
    }

}
