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
import sample.ControllerClasses.ControllerSponsorView;
import sample.ControllerClasses.InterfaceController;

import java.sql.SQLException;
import java.util.List;

public class ContractSponsor extends Table {
    private StringProperty name;
    private IntegerProperty reference_id;
    private IntegerProperty summ;
    private StringProperty oldName;
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

    public ContractSponsor(){}
    public int getSumm() {
        return summ.get();
    }

    public IntegerProperty summProperty() {
        return summ;
    }

    public void setSumm(int summ) {
        this.summ.set(summ);
    }

    public ContractSponsor(String name,int sum,String team_name){
        this.name=new SimpleStringProperty(name);
        this.summ=new SimpleIntegerProperty(sum);
        this.team_name=new SimpleStringProperty(team_name);
    }
    public ContractSponsor(String name, int reference_id,int sum){
        this.name=new SimpleStringProperty(name);
        this.reference_id=new SimpleIntegerProperty(reference_id);
        this.summ=new SimpleIntegerProperty(sum);
        this.oldName=new SimpleStringProperty(name);
    }

    public String getOldName() {
        return oldName.get();
    }

    public StringProperty oldNameProperty() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName.set(oldName);
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

    public int getReference_id() {
        return reference_id.get();
    }

    public IntegerProperty reference_idProperty() {
        return reference_id;
    }

    public void setReference_id(int reference_id) {
        this.reference_id.set(reference_id);
    }
    public static void  initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, Number> conruct_summ = new TableColumn<>("Contruct Sum $");
        TableColumn<Table,Number> reference_id;
        if(InterfaceController.currentSituation) {
            reference_id  = new TableColumn<>("Team id");
        }else{
            reference_id=new TableColumn<>("Player id");
        }
        name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).nameProperty());
        reference_id.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).reference_idProperty());
        conruct_summ.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).summProperty());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        //name.setOnEditCommit(t->((ContractSponsor)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        name.setOnEditCommit(tableStringCellEditEvent -> {
            ((ContractSponsor) tableStringCellEditEvent.getTableView().getItems()
                    .get(tableStringCellEditEvent.getTablePosition().getRow()))
                    .setOldName(tableStringCellEditEvent.getOldValue());
            ((ContractSponsor) tableStringCellEditEvent.getTableView().getItems()
                    .get(tableStringCellEditEvent.getTablePosition().getRow()))
                    .setName(tableStringCellEditEvent.getNewValue());
        });
        reference_id.setCellValueFactory(p -> new SimpleIntegerProperty(((ContractSponsor)p.getValue()).getReference_id()));
        reference_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        reference_id.setOnEditCommit(t->((ContractSponsor)t.getTableView().getItems().get(t.getTablePosition().getRow())).setReference_id(t.getNewValue().intValue()));
        conruct_summ.setCellValueFactory(p -> new SimpleIntegerProperty(((ContractSponsor)p.getValue()).getSumm()));
        conruct_summ.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        conruct_summ.setOnEditCommit(t->((ContractSponsor)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSumm(t.getNewValue().intValue()));
        table_view.getColumns().addAll(name,conruct_summ,reference_id);
        try{
            if(InterfaceController.currentSituation){
            table_view.getItems().addAll(dbController.getContractTeam());}
            else  table_view.getItems().addAll(dbController.getContractPlayer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }
    public static void  initializeView(TableView<Table> table_view,Team team, Player player) {
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, Number> conruct_summ = new TableColumn<>("Contruct Sum $");
        TableColumn<Table,String> team_Name=null;
        if(ControllerSponsorView.currentSituation==1) {
            team_Name  = new TableColumn<>("Team name");
        }else if (ControllerSponsorView.currentSituation==2){
            team_Name=new TableColumn<>("Player name");
        }
        name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).nameProperty());
        team_Name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).team_nameProperty());
        conruct_summ.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).summProperty());
        table_view.getColumns().addAll(name,conruct_summ,team_Name);
        try{
            if(ControllerSponsorView.currentSituation==1&&team!=null){
                table_view.getItems().addAll(dbController.showSponsorTeam(team));}
            else  if (ControllerSponsorView.currentSituation==2&&player!=null)
                table_view.getItems().addAll(dbController.showSponsorPlayer(player));
            else  if (ControllerSponsorView.currentSituation==1){
                table_view.getItems().addAll(dbController.showSponsorTeam());
            }else if(ControllerSponsorView.currentSituation==2){
                table_view.getItems().addAll(dbController.showSponsorPlayer());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
       // utilFX.autoResizeColumns(table_view);
    }
    public  static void initializeView(TableView table_view,String txt){
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, Number> conruct_summ = new TableColumn<>("Contruct Sum $");
        TableColumn<Table,String> team_Name=null;
        if(ControllerSponsorView.currentSituation==1) {
            team_Name  = new TableColumn<>("Team name");
        }else if (ControllerSponsorView.currentSituation==2){
            team_Name=new TableColumn<>("Player name");
        }
        name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).nameProperty());
        team_Name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).team_nameProperty());
        conruct_summ.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).summProperty());
        table_view.getColumns().addAll(name,conruct_summ,team_Name);
        try {
            table_view.getItems().addAll(dbController.showSponsorTeam(txt));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }
    public  static void initializeView(TableView table_view, List<Table> tableList){
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, Number> conruct_summ = new TableColumn<>("Contruct Sum $");
        TableColumn<Table,String> team_Name=null;
        team_Name=new TableColumn<>("Player name");

        name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).nameProperty());
        team_Name.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).team_nameProperty());
        conruct_summ.setCellValueFactory(cellData->((ContractSponsor)cellData.getValue()).summProperty());
        table_view.getColumns().addAll(name,conruct_summ,team_Name);
        table_view.getItems().addAll(tableList);
        dbController.closeConnection();
    }
}
