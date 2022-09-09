package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import sample.Database.Player;
import sample.Database.PlayerStats;

public class ControllerPlayerView {
    public   Player currentPlayer;
    @FXML
    private Button show_button;

    @FXML
    private TableView<?> player_table;
    @FXML
    private TableView<?> player_stats;

    @FXML
    void initialize() {
        player_stats.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        if(ControllerSeach.lp==null){
           Player.initializePlayerView(player_table);}
        else{
            Player.initializePlayerView(player_table,ControllerSeach.lp);
            ControllerSeach.lp=null;
        }
        player_table.setOnMouseClicked(mouseEvent -> {
            if (player_table.getSelectionModel().getSelectedItem() != null) {
                currentPlayer=(Player) player_table.getSelectionModel().getSelectedItem();
            }
        });
        show_button.setOnAction(event -> {
            PlayerStats.initializePlayrStatsView(player_stats,currentPlayer);
            currentPlayer=null;
        });
}
}
