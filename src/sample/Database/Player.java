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
import sample.utils.utilFX;

import java.sql.SQLException;
import java.util.List;

public class Player extends  Table {
    private IntegerProperty id;
    private StringProperty name;
    private IntegerProperty team_id;
    private StringProperty player_position;
    private IntegerProperty age;
    private StringProperty nationality;
    private StringProperty height;
    private IntegerProperty weight;
    private StringProperty team_name;

    public Player(int id, String name, int team_id, int
            age, int weight, String player_position, String nationality, String height) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.player_position = new SimpleStringProperty(player_position);
        this.nationality = new SimpleStringProperty(nationality);
        this.height = new SimpleStringProperty(height);
        this.age = new SimpleIntegerProperty(age);
        this.weight = new SimpleIntegerProperty(weight);
        this.team_id = new SimpleIntegerProperty(team_id);

    }

    public Player(int id, String name, String team_Name, int
            age, int weight, String player_position, String nationality, String height) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.player_position = new SimpleStringProperty(player_position);
        this.nationality = new SimpleStringProperty(nationality);
        this.height = new SimpleStringProperty(height);
        this.age = new SimpleIntegerProperty(age);
        this.weight = new SimpleIntegerProperty(weight);
        this.team_name = new SimpleStringProperty(team_Name);

    }

    public Player() {
    }

    ;

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

    public Player(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
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

    public String getPlayer_position() {
        return player_position.get();
    }

    public StringProperty player_positionProperty() {
        return player_position;
    }

    public void setPlayer_position(String player_position) {
        this.player_position.set(player_position);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public String getHeight() {
        return height.get();
    }

    public StringProperty heightProperty() {
        return height;
    }

    public void setHeight(String height) {
        this.height.set(height);
    }

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    public static void initialize(TableView<Table> table_view) {
        table_view.setEditable(true);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> player_position = new TableColumn<>("Player Position");
        TableColumn<Table, Number> age = new TableColumn<>("Age");
        TableColumn<Table, String> nationality = new TableColumn<>("Nationality");
        TableColumn<Table, String> height = new TableColumn<>("Height");
        TableColumn<Table, Number> weight = new TableColumn<>("Weight");
        TableColumn<Table, Number> team_id = new TableColumn<>("Team");
        id.setCellValueFactory(cellData -> ((Player) cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nameProperty());
        age.setCellValueFactory(cellData -> ((Player) cellData.getValue()).ageProperty());
        player_position.setCellValueFactory(cellData -> ((Player) cellData.getValue()).player_positionProperty());
        nationality.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nationalityProperty());
        height.setCellValueFactory(cellData -> ((Player) cellData.getValue()).heightProperty());
        weight.setCellValueFactory(cellData -> ((Player) cellData.getValue()).weightProperty());
        team_id.setCellValueFactory(cellData -> ((Player) cellData.getValue()).team_idProperty());

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get((t.getTablePosition().getRow()))).setName(t.getNewValue()));
        player_position.setCellValueFactory(new PropertyValueFactory<>("player_position"));
        player_position.setCellFactory(TextFieldTableCell.forTableColumn());
        player_position.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPlayer_position(t.getNewValue()));
        nationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        nationality.setCellFactory(TextFieldTableCell.forTableColumn());
        nationality.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNationality(t.getNewValue()));
        height.setCellValueFactory(new PropertyValueFactory<>("height"));
        height.setCellFactory(TextFieldTableCell.forTableColumn());
        height.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setHeight(t.getNewValue()));
        age.setCellValueFactory(p -> new SimpleIntegerProperty(((Player) p.getValue()).getAge()));
        age.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        age.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAge(t.getNewValue().intValue()));
        weight.setCellValueFactory(p -> new SimpleIntegerProperty(((Player) p.getValue()).getWeight()));
        weight.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        weight.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWeight(t.getNewValue().intValue()));
        team_id.setCellValueFactory(p -> new SimpleIntegerProperty(((Player) p.getValue()).getTeam_id()));
        team_id.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
        team_id.setOnEditCommit(t -> ((Player) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTeam_id(t.getNewValue().intValue()));
        table_view.getColumns().addAll(id, name, age, nationality, player_position, height, weight, team_id);
        try {
            table_view.getItems().addAll(dbController.getPlayer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbController.closeConnection();
    }

    public static void initializeAdd(TableView<Table> tableView) {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        DBController dbController = new DBController();
        dbController.Connect();
        tableView.getItems().clear();
        tableView.getColumns().clear();
        TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        id.setCellValueFactory(cellData -> ((Player) cellData.getValue()).idProperty());
        name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nameProperty());
        tableView.getColumns().addAll(id, name);
        try {
            tableView.getItems().addAll(dbController.additionalPlayer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void initializePlayerView(TableView table_view) {
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table_view.setEditable(false);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> player_position = new TableColumn<>("Player Position");
        TableColumn<Table, Number> age = new TableColumn<>("Age");
        TableColumn<Table, String> nationality = new TableColumn<>("Nationality");
        TableColumn<Table, String> height = new TableColumn<>("Height");
        TableColumn<Table, Number> weight = new TableColumn<>("Weight");
        //TableColumn<Table, String> team_Name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData -> ((Player) cellData.getValue()).idProperty());
        id.setVisible(false);
        name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nameProperty());
        age.setCellValueFactory(cellData -> ((Player) cellData.getValue()).ageProperty());
        player_position.setCellValueFactory(cellData -> ((Player) cellData.getValue()).player_positionProperty());
        nationality.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nationalityProperty());
        height.setCellValueFactory(cellData -> ((Player) cellData.getValue()).heightProperty());
        weight.setCellValueFactory(cellData -> ((Player) cellData.getValue()).weightProperty());
      //  team_Name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).team_nameProperty());
        if (ViewerController.currentTeam != null) {
            table_view.getColumns().addAll(id, name, age, nationality, player_position, height, weight);
            try {
                table_view.getItems().addAll(dbController.showPlayers(ViewerController.currentTeam));

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            TableColumn<Table, String> team_Name = new TableColumn<>("Team");
            team_Name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).team_nameProperty());
            table_view.getColumns().addAll(id, name, age, nationality, player_position, height, weight,team_Name);
            try {
                table_view.getItems().addAll(dbController.showPlayers());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            utilFX.autoResizeColumns(table_view);
        }
        dbController.closeConnection();
    }
    public static void initializePlayerView(TableView table_view, List<Table> playerList){
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table_view.setEditable(false);
        DBController dbController = new DBController();
        dbController.Connect();
        table_view.getItems().clear();
        table_view.getColumns().clear();
        TableColumn<Table, Number> id = new TableColumn<>("ID");
        TableColumn<Table, String> name = new TableColumn<>("Name");
        TableColumn<Table, String> player_position = new TableColumn<>("Player Position");
        TableColumn<Table, Number> age = new TableColumn<>("Age");
        TableColumn<Table, String> nationality = new TableColumn<>("Nationality");
        TableColumn<Table, String> height = new TableColumn<>("Height");
        TableColumn<Table, Number> weight = new TableColumn<>("Weight");
        TableColumn<Table, String> team_Name = new TableColumn<>("Team");
        id.setCellValueFactory(cellData -> ((Player) cellData.getValue()).idProperty());
        id.setVisible(false);
        name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nameProperty());
        age.setCellValueFactory(cellData -> ((Player) cellData.getValue()).ageProperty());
        player_position.setCellValueFactory(cellData -> ((Player) cellData.getValue()).player_positionProperty());
        nationality.setCellValueFactory(cellData -> ((Player) cellData.getValue()).nationalityProperty());
        height.setCellValueFactory(cellData -> ((Player) cellData.getValue()).heightProperty());
        weight.setCellValueFactory(cellData -> ((Player) cellData.getValue()).weightProperty());
        team_Name.setCellValueFactory(cellData -> ((Player) cellData.getValue()).team_nameProperty());
        table_view.getColumns().addAll(id, name, age, nationality, player_position, height, weight,team_Name);
        table_view.getItems().addAll(playerList);
        //utilFX.autoResizeColumns(table_view);
}
    }


