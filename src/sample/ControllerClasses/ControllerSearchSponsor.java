package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.Database.Table;

import java.util.List;
import java.util.Map;

public class ControllerSearchSponsor {
    public static int currentSituation=0;
    @FXML
    private Label tables;

    @FXML
    private Button show_players;

    @FXML
    private Button show_sponsor;

    @FXML
    void initialize() {
        show_players.setDisable(true);
        show_sponsor.setDisable(true);
        tables.setText(getCountOfTables(ControllerSponsorView.map) + " tables");
        show_players.setOnAction(this::btnShowPlayer);
        show_sponsor.setOnAction(this::btnShowSponsor);

    }
    private int getCountOfTables(Map<String,List<Table>> map) {
        int count = 0;
        for (Map.Entry<String, List<Table>> entry : map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                count++;
                if (entry.getKey().equals("Player")) {
                    show_players.setDisable(false);
                } else if (entry.getKey().equals("Contract Player")) {
                    show_sponsor.setDisable(false);
                }
            }

        }
        return count;
    }
    private void btnShowPlayer(javafx.event.ActionEvent event){
        currentSituation=1;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    private void btnShowSponsor(ActionEvent event){
        currentSituation=2;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
