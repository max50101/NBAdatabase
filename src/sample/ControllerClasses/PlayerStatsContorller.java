package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Database.*;

public class PlayerStatsContorller {
    @FXML
    private TextField value;

    @FXML
    private TextField player_id;

    @FXML
    private TextField progress_id;

    @FXML
    private Button submit_button;

    @FXML
    private TableView<Table> team_table;

    @FXML
    private TableView<Table> player_id_table;

    @FXML
    void initialize() {
        Progress.initialize(team_table);
        Player.initializeAdd(player_id_table);
        submit_button.setOnAction(event -> {
            String _player_id=player_id.getText();
            String _value=value.getText();
            String _progress_id=progress_id.getText();
            PlayerStats playerStats=new PlayerStats(Integer.parseInt(_player_id),_value,Integer.parseInt(_progress_id));
            DBController controller=new DBController();
            controller.Connect();
            controller.insertPlayerStats(playerStats);
            controller.closeConnection();
        });

    }
}
