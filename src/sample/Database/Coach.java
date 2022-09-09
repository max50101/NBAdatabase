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

public class Coach extends Table {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty speciality;
    private IntegerProperty team_id;
    private StringProperty team_Name;

    public String getTeam_Name() {
        return team_Name.get();
    }

    public StringProperty team_NameProperty() {
        return team_Name;
    }

    public void setTeam_Name(String team_Name) {
        this.team_Name.set(team_Name);
    }

    public int getId() {
        return id.get();
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

    public String getSpeciality() {
        return speciality.get();
    }

    public StringProperty specialityProperty() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality.set(speciality);
    }

    public Coach(int id, String  name, String speciality, int team_id) {
        this.id =new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.speciality = new SimpleStringProperty(speciality);
        this.team_id=new SimpleIntegerProperty(team_id);
    }
    public Coach(int id, String  name, String speciality, String team_name) {
        this.id =new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.speciality = new SimpleStringProperty(speciality);
        this.team_Name=new SimpleStringProperty(team_name);
    }
    public Coach(){}
    public static void  initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        javafx.scene.control.TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> speciality = new TableColumn<>("Speciality");
        TableColumn<Table, Number> team_id = new TableColumn<>("Team id");
        id.setCellValueFactory(cellData->((Coach)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Coach)cellData.getValue()).nameProperty());
        speciality.setCellValueFactory(cellData->((Coach)cellData.getValue()).specialityProperty());
        team_id.setCellValueFactory(cellData->((Coach)cellData.getValue()).team_idProperty());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t->((Coach)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        speciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
        speciality.setCellFactory(TextFieldTableCell.forTableColumn());
        speciality.setOnEditCommit(t->((Coach)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSpeciality(t.getNewValue()));
        team_id.setCellValueFactory(p -> new SimpleIntegerProperty(((Coach)p.getValue()).getTeam_id()));
        team_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        team_id.setOnEditCommit(t->((Coach)t.getTableView().getItems().get(t.getTablePosition().getRow())).setTeam_id(t.getNewValue().intValue()));
        table_view.getColumns().addAll(id,name,speciality,team_id);
        try{
            table_view.getItems().addAll(dbController.getCoach());
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
        TableColumn<Table, String> speciality = new TableColumn<>("Speciality");
        TableColumn<Table, String> team_name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData->((Coach)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Coach)cellData.getValue()).nameProperty());
        speciality.setCellValueFactory(cellData->((Coach)cellData.getValue()).specialityProperty());
        team_name.setCellValueFactory(cellData->((Coach)cellData.getValue()).team_NameProperty());
        if(ViewerController.currentTeam!=null) {
            table_view.getColumns().addAll(name, speciality);
            try{
                    table_view.getItems().addAll(dbController.showCoaches(ViewerController.currentTeam));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            table_view.getColumns().addAll(name,speciality,team_name);
            try{
                table_view.getItems().addAll(dbController.showCoaches());
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
        TableColumn<Table, String> speciality = new TableColumn<>("Speciality");
        TableColumn<Table, String> team_name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData->((Coach)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Coach)cellData.getValue()).nameProperty());
        speciality.setCellValueFactory(cellData->((Coach)cellData.getValue()).specialityProperty());
        team_name.setCellValueFactory(cellData->((Coach)cellData.getValue()).team_NameProperty());
        table_view.getColumns().addAll(name,speciality,team_name);
        table_view.getItems().addAll(tl);
        //utilFX.autoResizeColumns(table_view);
    }
}
