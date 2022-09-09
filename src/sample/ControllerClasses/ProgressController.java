package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Database.DBController;
import sample.Database.Progress;

public class ProgressController {
    @FXML
    private TextField name;

    @FXML
    private TextField unit;

    @FXML
    private Button submit_button;

    @FXML
    void initialize() {
       submit_button.setOnAction(event -> {
           String _name=name.getText();
           String _unit=unit.getText();
           Progress progress=new Progress(1,_name,_unit);
           DBController controller=new DBController();
           controller.Connect();
           controller.insertProgress(progress);
           controller.closeConnection();
       });

    }
}
