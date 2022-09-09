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

public class Team extends Table {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty city;
    private IntegerProperty countOfChampions;
    private IntegerProperty season_place;
    private IntegerProperty play_off_place;

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

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public int getCountOfChampions() {
        return countOfChampions.get();
    }

    public IntegerProperty countOfChampionsProperty() {
        return countOfChampions;
    }

    public void setCountOfChampions(int countOfChampions) {
        this.countOfChampions.set(countOfChampions);
    }

    public int getSeason_place() {
        return season_place.get();
    }

    public IntegerProperty season_placeProperty() {
        return season_place;
    }

    public void setSeason_place(int season_place) {
        this.season_place.set(season_place);
    }

    public int getPlay_off_place() {
        return play_off_place.get();
    }

    public IntegerProperty play_off_placeProperty() {
        return play_off_place;
    }

    public void setPlay_off_place(int play_off_place) {
        this.play_off_place.set(play_off_place);
    }

    public Team(int id, String name, String city, int
            countOfChampions, int season_place, int play_off_place){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
        this.city=new SimpleStringProperty(city);
        this.countOfChampions=new SimpleIntegerProperty(countOfChampions);
        this.season_place=new SimpleIntegerProperty(season_place);
        this.play_off_place=new SimpleIntegerProperty(play_off_place);
    }
    public Team(int id, String name){
        this.id=new SimpleIntegerProperty(id);
        this.name=new SimpleStringProperty(name);
    }
    public Team(){};
    public static void  initialize(TableView<Table> table_view){
        table_view.setEditable(true);
        DBController dbController=new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table,Number> id =new TableColumn<>("ID");
        TableColumn <Table,Number> countOfChampions =new TableColumn<>("Count of Champions");
        TableColumn <Table,Number> season_place =new TableColumn<>("Season Place");
        TableColumn <Table,Number> play_off_place =new TableColumn<>("PlayOffPlace");
        TableColumn <Table,String> name =new TableColumn<>("Name");
        TableColumn <Table,String> city =new TableColumn<>("City");
        id.setCellValueFactory(cellData->((Team)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Team)cellData.getValue()).nameProperty());
        city.setCellValueFactory(cellData->((Team)cellData.getValue()).cityProperty());
        countOfChampions.setCellValueFactory(cellData->((Team)cellData.getValue()).countOfChampionsProperty());
        season_place.setCellValueFactory(cellData->((Team)cellData.getValue()).season_placeProperty());
        play_off_place.setCellValueFactory(cellData->((Team)cellData.getValue()).play_off_placeProperty());
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t->((Team)t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        city.setCellFactory(TextFieldTableCell.forTableColumn());
        city.setOnEditCommit(t->((Team)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCity(t.getNewValue()));
        countOfChampions.setCellValueFactory(p -> new SimpleIntegerProperty(((Team)p.getValue()).getCountOfChampions()));
        countOfChampions.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        countOfChampions.setOnEditCommit(t->((Team)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountOfChampions(t.getNewValue().intValue()));
        season_place.setCellValueFactory(p -> new SimpleIntegerProperty(((Team)p.getValue()).getSeason_place()));
        season_place.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        season_place.setOnEditCommit(t->((Team)t.getTableView().getItems().get(t.getTablePosition().getRow())).setSeason_place(t.getNewValue().intValue()));
        play_off_place.setCellValueFactory(p -> new SimpleIntegerProperty(((Team)p.getValue()).getPlay_off_place()));
        play_off_place.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        play_off_place.setOnEditCommit(t->((Team)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlay_off_place(t.getNewValue().intValue()));
        table_view.getColumns().addAll(id,name,city,countOfChampions,season_place,play_off_place);
        try {
            table_view.getItems().addAll(dbController.getTeam());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }
    public static void initializeTeam(TableView<Table> tableView){
        DBController dbController=new DBController();
        dbController.Connect();
        tableView.getItems().clear();
        tableView.getColumns().clear();
        TableColumn<Table,Number> id =new TableColumn<>("ID");
        TableColumn <Table,String> name =new TableColumn<>("Name");
        id.setCellValueFactory(cellData->((Team)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Team)cellData.getValue()).nameProperty());
        tableView.getColumns().addAll(id,name);
        try {
            tableView.getItems().addAll(dbController.additionalTeam());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void initializeViewer(TableView<Table> tableView){
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBController dbController=new DBController();
        dbController.Connect();
        tableView.getItems().clear();
        tableView.getColumns().clear();
        TableColumn<Table,Number> id =new TableColumn<>("ID");
        TableColumn <Table,Number> countOfChampions =new TableColumn<>("Count of Champions");
        TableColumn <Table,Number> season_place =new TableColumn<>("Season Place");
        TableColumn <Table,Number> play_off_place =new TableColumn<>("PlayOffPlace");
        TableColumn <Table,String> name =new TableColumn<>("Name");
        TableColumn <Table,String> city =new TableColumn<>("City");
        id.setVisible(false);
        id.setCellValueFactory(cellData->((Team)cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData->((Team)cellData.getValue()).nameProperty());
        city.setCellValueFactory(cellData->((Team)cellData.getValue()).cityProperty());
        countOfChampions.setCellValueFactory(cellData->((Team)cellData.getValue()).countOfChampionsProperty());
        season_place.setCellValueFactory(cellData->((Team)cellData.getValue()).season_placeProperty());
        play_off_place.setCellValueFactory(cellData->((Team)cellData.getValue()).play_off_placeProperty());
        tableView.getColumns().addAll(name,city,countOfChampions,season_place,play_off_place);
        try {
            tableView.getItems().addAll(dbController.getTeam());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
        //utilFX.autoResizeColumns(tableView);
    }


}
