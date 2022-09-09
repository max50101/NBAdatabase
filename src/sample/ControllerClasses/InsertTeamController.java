package sample.ControllerClasses;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Database.DBController;
import sample.Database.Team;

public class InsertTeamController {


    @FXML
    private TextField name;

    @FXML
    private TextField city;

    @FXML
    private TextField count_of_champions;

    @FXML
    private TextField season_place;

    @FXML
    private TextField play_off_place;

    @FXML
    private Button submit_button;

    @FXML
    void initialize() {
      submit_button.setOnAction(event -> {
          String teamName=name.getText();
          String cityName=city.getText();
          Integer count_of_ch=Integer.parseInt(count_of_champions.getText());
          Integer season_placec=Integer.parseInt(season_place.getText());
          Integer play_off=Integer.parseInt(play_off_place.getText());
          Team team=new Team(10,teamName,cityName,count_of_ch,season_placec,play_off);
          DBController dbController=new DBController();
          dbController.Connect();
          dbController.insertTeam(team);
          dbController.closeConnection();
      });

    }
}
