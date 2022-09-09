package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Database.*;

public class InsertCoachController {
    @FXML
    private TextField name;

    @FXML
    private TextField speciality;

    @FXML
    private TextField team_id;

    @FXML
    private Button submit_button;

    @FXML
    private TableView<Table> team_table;

    @FXML
    void initialize() {
        team_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Team.initializeTeam(team_table);
        if (InterfaceController.currentSituation) {
            speciality.setVisible(true);
        } else {
            speciality.setVisible(false);
        }
        submit_button.setOnAction(event -> {
           if(InterfaceController.currentSituation){
           String _name=name.getText();
           String _speciality=speciality.getText();
           String _team_id=team_id.getText();
           DBController controller=new DBController();
           controller.Connect();
           controller.insertCoach(new Coach(1,_name,_speciality,Integer.parseInt(_team_id)));
           controller.closeConnection();}
           else {
               String _name=name.getText();
               String _team_id=team_id.getText();
               DBController controller=new DBController();
               controller.Connect();
               controller.insertManager(new Manager(1,_name,Integer.parseInt(_team_id)));
               controller.closeConnection();
           }
           });
       };

    }

