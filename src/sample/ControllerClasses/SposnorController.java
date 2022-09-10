package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.Database.*;

import java.util.List;
import java.util.Map;

public class SposnorController {

    @FXML
    private TextField name;

    @FXML
    private TextField Contract_sum;

    @FXML
    private TextField reference_id;

    @FXML
    private Button submit_button;

    @FXML
    private TableView<Table> team_table;

    @FXML
    void initialize() {
        team_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       if (InterfaceController.currentSituation){
           reference_id.setPromptText("Team id");
           Team.initializeTeam(team_table);
       }else{
           reference_id.setPromptText("Player id");
           Player.initializeAdd(team_table);
       }
       submit_button.setOnAction(event -> {
           DBController controller=new DBController();
           controller.Connect();
           String _name=name.getText();
           String contruct_sum=reference_id.getText();
           String _team_id=Contract_sum.getText();
           ContractSponsor contractSponsor=new ContractSponsor(_name,Integer.parseInt(contruct_sum),Integer.parseInt(_team_id));
           if(InterfaceController.currentSituation){
               controller.addContractTeam(contractSponsor);

           }else {
                controller.addContractPlayer(contractSponsor);
           }
           controller.closeConnection();
       });

    }
}
