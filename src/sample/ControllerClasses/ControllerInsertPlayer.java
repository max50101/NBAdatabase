package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Database.DBController;
import sample.Database.Player;
import sample.Database.Table;
import sample.Database.Team;

public class ControllerInsertPlayer {
    @FXML
    private TextField name;

    @FXML
    private TextField age;

    @FXML
    private TextField nationality;

    @FXML
    private TextField player_position;

    @FXML
    private TextField height;

    @FXML
    private Button submit_button;

    @FXML
    private TextField weight;

    @FXML
    private TextField team_id;
    @FXML
    private TableView<Table> team_table;

    @FXML
    void initialize() {
        team_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Team.initializeTeam(team_table);
        submit_button.setOnAction(event -> {
            DBController controller =new DBController();
            controller.Connect();
            String player_name=name.getText();
            String playr_position1=player_position.getText();
            String player_nationality=nationality.getText();
            String player_height=height.getText();
            Integer player_age=Integer.parseInt(age.getText());
            Integer player_weight=Integer.parseInt(weight.getText());
            Integer player_team_id=Integer.parseInt(team_id.getText());
            Player player=new Player(10,player_name,player_team_id,player_age,player_weight,playr_position1,
                    player_nationality,player_height);
            controller.insertPlayer(player);

            controller.closeConnection();

        });

    }
}
