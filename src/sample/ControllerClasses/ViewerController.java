package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Database.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ViewerController {
    public static Team currentTeam=null;
    public static Map<String, List<Table>> map=null;
    @FXML
    private TableView<Table> table_view;

    @FXML
    private Button show_players;

    @FXML
    private Button show_coaches;

    @FXML
    private Button show_managers;
    @FXML
    private Button get_back;
    @FXML
    private TextField name_searc_f;

    @FXML
    private Button search;

    @FXML
    void initialize() {
        name_searc_f.setPromptText("Enter name to be searched");
        get_back.setVisible(false);
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Team.initializeViewer(table_view);
        table_view.setOnMouseClicked(mouseEvent -> {
            if (table_view.getSelectionModel().getSelectedItem() != null) {
                currentTeam=(Team) table_view.getSelectionModel().getSelectedItem();
            }
        });
        show_players.setOnAction(this::showPlayerOnAction);
        show_coaches.setOnAction(this::showCoachesOnAction);
        show_managers.setOnAction(this::showManagersOnAction);
        get_back.setOnAction(this::getBack);
        search.setOnAction(this::searchButtonOnAction);
    }
    private void showCoachesOnAction(ActionEvent event){
        get_back.setVisible(true);
        Coach.initializeViewer(table_view);
    }
    private void showManagersOnAction(ActionEvent event){
        get_back.setVisible(true);
        Manager.initializeViewer(table_view);
    }
    private  void showPlayerOnAction(ActionEvent event){
        Parent interfaceViewParent=null;
        try{
            URL url=new File("src/sample/design/PlayerView.fxml").toURL();
            interfaceViewParent= FXMLLoader.load(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        currentTeam=null;
        }
        Scene interfaceViewScene = new Scene(interfaceViewParent, 517, 571);
        Stage window = new Stage();
        window.setScene(interfaceViewScene);
        window.getIcons().add(new Image("file:C://Users//MaksPC/IdeaProjects//NBAdatabase//image_icon.png"));
        window.sizeToScene();
        window.show();
        currentTeam=null;
    }
    private void getBack(ActionEvent e){
        Team.initializeViewer(table_view);
        get_back.setVisible(false);
        currentTeam=null;
    }
    private void searchButtonOnAction(ActionEvent event){
        String txt=name_searc_f.getText();
        DBController controller=new DBController();
        controller.Connect();
        try {
            map=controller.searchByName(txt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parent interfaceViewParent=null;
        try{
            URL url=new File("src/sample/design/choose_search.fxml").toURL();
            interfaceViewParent= FXMLLoader.load(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene interfaceViewScene = new Scene(interfaceViewParent, 225, 197);
        Stage window = new Stage();
        window.setOnHidden(windowEvent -> {
            if (ControllerSeach.currectSituation == 1) {
                get_back.setVisible(true);
                Coach.initializeView(table_view, map.get("Coach"));
            }
            else if (ControllerSeach.currectSituation == 2) {
                get_back.setVisible(true);
                Manager.initializeView(table_view, map.get("Manager"));
            }
            else{
                Team.initializeViewer(table_view);
            }
            ControllerSeach.currectSituation=0;
        });
        window.setScene(interfaceViewScene);
        window.getIcons().add(new Image("file:C://Users//MaksPC/IdeaProjects//NBAdatabase//image_icon.png"));
        window.sizeToScene();
        window.show();

    }
}
