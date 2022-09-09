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
import sample.ControllerClasses.ControllerPlayerView;

import java.sql.SQLException;

public class PlayerStats extends Table {
    private IntegerProperty player_id;
    private IntegerProperty progress_id;
    private StringProperty value;
    private  IntegerProperty old_player_id;
    private IntegerProperty old_reference_id;
    private StringProperty name;
    private StringProperty progressName;

    public String getProgressName() {
        return progressName.get();
    }

    public StringProperty progressNameProperty() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName.set(progressName);
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

    public PlayerStats (String name, String value, String progress_Name){
        this.name=new SimpleStringProperty(name);
        this.value=new SimpleStringProperty(value);
        this.progressName=new SimpleStringProperty(progress_Name);
    }
    public  PlayerStats(){}
    public int getPlayer_id() {
        return player_id.get();
    }

    public IntegerProperty player_idProperty() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {this.player_id.set(player_id); }

    public int getProgress_id() {
        return progress_id.get();
    }

    public IntegerProperty progress_idProperty() {
        return progress_id;
    }

    public int getOld_player_id() {
        return old_player_id.get();
    }

    public IntegerProperty old_player_idProperty() {
        return old_player_id;
    }

    public void setOld_player_id(int old_player_id) {
        this.old_player_id.set(old_player_id);
    }

    public int getOld_reference_id() {
        return old_reference_id.get();
    }

    public IntegerProperty old_reference_idProperty() {
        return old_reference_id;
    }

    public void setOld_reference_id(int old_reference_id) {
        this.old_reference_id.set(old_reference_id);
    }

    public void setProgress_id(int progress_id) {
        this.progress_id.set(progress_id);
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }
    public PlayerStats(int player_id,String value, int progress_id){
        this.player_id=new SimpleIntegerProperty(player_id);
        this.value=new SimpleStringProperty(value);
        this.progress_id=new SimpleIntegerProperty(progress_id);
        this.old_player_id=new SimpleIntegerProperty(player_id);
        this.old_reference_id=new SimpleIntegerProperty(progress_id);
        this.name=new SimpleStringProperty("name");
        this.progressName=new SimpleStringProperty("progress");
    }
    public static void  initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, Number> player_id = new TableColumn<>("player_id");
        TableColumn<Table, Number> progres_id = new TableColumn<>("progress_id");
        TableColumn<Table, String> value = new TableColumn<>("Value");

        player_id.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).player_idProperty());
        progres_id.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).progress_idProperty());
        value.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).valueProperty());
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        value.setCellFactory(TextFieldTableCell.forTableColumn());
        value.setOnEditCommit(t->((PlayerStats)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setValue(t.getNewValue()));
        player_id.setCellValueFactory(p -> new SimpleIntegerProperty(((PlayerStats)p.getValue()).getPlayer_id()));
        player_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        player_id.setOnEditCommit(t->{
            ((PlayerStats) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOld_player_id(t.getOldValue().intValue());
            ((PlayerStats)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlayer_id(t.getNewValue().intValue());
        });
        progres_id.setCellValueFactory(p -> new SimpleIntegerProperty(((PlayerStats)p.getValue()).getProgress_id()));
        progres_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        progres_id.setOnEditCommit(t->{
                ((PlayerStats) t.getTableView().getItems().get(t.getTablePosition().getRow())).setOld_reference_id(t.getOldValue().intValue());
                ((PlayerStats)t.getTableView().getItems().get(t.getTablePosition().getRow())).setProgress_id(t.getNewValue().intValue());
        });
        table_view.getColumns().addAll(player_id,progres_id,value);
        try{
            table_view.getItems().addAll(dbController.getPlayerStats());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }
    public static void initializePlayrStatsView(TableView table_view,Player player){
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table_view.setEditable(false);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, String> player_id = new TableColumn<>("Player Name");
        TableColumn<Table, String>  progress_name = new TableColumn<>("Progress Name");
        TableColumn<Table, String> value = new TableColumn<>("Value");
        //player_id.setVisible(false);
        player_id.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).nameProperty());
        progress_name.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).progressNameProperty());
        value.setCellValueFactory(cellData->((PlayerStats)cellData.getValue()).valueProperty());
        table_view.getColumns().addAll(player_id,progress_name,value);
        if(player!=null) {
            table_view.getItems().addAll(dbController.showPlayerStats(player));
        }else{
            table_view.getItems().addAll(dbController.showPlayerStats());
        }
        dbController.closeConnection();
    }
}
