package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Database.Player;
import sample.Database.Table;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ControllerSeach {
    public static List<Table> lp = null;
    public static int currectSituation=0;
    @FXML
    private Label tables;

    @FXML
    private Button show_players;

    @FXML
    private Button show_coach;

    @FXML
    private Button show_manager;

    @FXML
    void initialize() {
        show_manager.setDisable(true);
        show_coach.setDisable(true);
        show_players.setDisable(true);
        tables.setText(getCountOfTables() + " tables");
        show_players.setOnAction(this::showPlayerButtonAction);
        show_coach.setOnAction(this::showCoachButtonAction);
        show_manager.setOnAction(this::showManagerButtonAction);
    }

    private int getCountOfTables() {
        int count = 0;
        for (Map.Entry<String, List<Table>> entry : ViewerController.map.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                count++;
                if (entry.getKey().equals("Player")) {
                    show_players.setDisable(false);
                } else if (entry.getKey().equals("Coach")) {
                    show_coach.setDisable(false);
                } else if (entry.getKey().equals("Manager")) {
                    show_manager.setDisable(false);
                }
            }

        }
        return count;
    }
    private void showPlayerButtonAction(ActionEvent event) {
        lp = ViewerController.map.get("Player");
        Parent interfaceViewParent=null;
        try{
            URL url=new File("src/sample/design/PlayerView.fxml").toURL();
            interfaceViewParent= FXMLLoader.load(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene interfaceViewScene = new Scene(interfaceViewParent, 517, 571);
        Stage window =  (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(interfaceViewScene);
        window.getIcons().add(new Image("file:C://Users//MaksPC/IdeaProjects//NBAdatabase//image_icon.png"));
        window.sizeToScene();
        window.show();

    }
    private void showCoachButtonAction(ActionEvent event){
       // ((Node)(event.getSource())).getScene().getWindow().hide();
        currectSituation=1;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    private void showManagerButtonAction(ActionEvent event){
        currectSituation=2;
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}

