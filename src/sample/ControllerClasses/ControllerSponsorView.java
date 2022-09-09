package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Database.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ControllerSponsorView {
    public static Map<String, List<Table>> map=null;
    public static Team currentTeam=null;
    public static Player currentPlayer=null;
    public static int currentSituation=0;
    @FXML
    private Button get_back_team;

    @FXML
    private TableView<Table> table_team;

    @FXML
    private TextField search_field_team;

    @FXML
    private Button search_button;

    @FXML
    private Button show_sponsors_team;

    @FXML
    private TableView<Table> player_table;

    @FXML
    private TextField name_search;

    @FXML
    private Button btn_search;

    @FXML
    private Button get_back_player;

    @FXML
    private Button show_statistic;

    @FXML
    private Button show_sponsors_player;

    @FXML
    void initialize() {
        get_back_team.setVisible(false);
        get_back_player.setVisible(false);
        Team.initializeViewer(table_team);
        Player.initializePlayerView(player_table);
        table_team.setOnMouseClicked(mouseEvent -> {
            if (table_team.getSelectionModel().getSelectedItem() != null) {
                currentTeam=(Team) table_team.getSelectionModel().getSelectedItem();
            }
        });
        player_table.setOnMouseClicked(mouseEvent -> {
            if(player_table.getSelectionModel().getSelectedItem()!=null){
                currentPlayer=(Player) player_table.getSelectionModel().getSelectedItem();
            }
        });
        show_sponsors_team.setOnAction(this::butShowSponsorTeam);
        get_back_team.setOnAction(this::getBackTeam);
        show_sponsors_player.setOnAction(this::btnShowSponsorPlayer);
        get_back_player.setOnAction(this::getBackPlayer);
        show_statistic.setOnAction(this::btnShowPlayerStats);
        search_button.setOnAction(this::BtnSearchTeam);
        btn_search.setOnAction(this::BtnSearchPlayer);
    }

    private void butShowSponsorTeam(javafx.event.ActionEvent event){
        get_back_team.setVisible(true);
        currentSituation=1;
        if(currentTeam!=null){
            ContractSponsor.initializeView(table_team,currentTeam,null);
        }else{
            ContractSponsor.initializeView(table_team,null,null);
        }
        currentSituation=0;
    }
    private void btnShowSponsorPlayer(ActionEvent event){
        get_back_player.setVisible(true);
        currentSituation=2;
        if(currentPlayer!=null){
            ContractSponsor.initializeView(player_table,null,currentPlayer);
        }else{
            ContractSponsor.initializeView(player_table,null,null);
        }
        currentSituation=0;
    }
    private void btnShowPlayerStats(ActionEvent event){
        get_back_player.setVisible(true);
        if(currentPlayer!=null){
            PlayerStats.initializePlayrStatsView(player_table,currentPlayer);
        }else{
            PlayerStats.initializePlayrStatsView(player_table,null);
        }
        currentPlayer=null;

    }

    private  void getBackTeam(ActionEvent event){
        Team.initializeViewer(table_team);
        currentTeam=null;
        get_back_team.setVisible(false);
    }

    private void getBackPlayer(ActionEvent event){
        Player.initializePlayerView(player_table);
        currentPlayer=null;
        get_back_player.setVisible(false);
    }

    private void BtnSearchTeam(ActionEvent event){
        currentSituation=1;
        ContractSponsor.initializeView(table_team,search_field_team.getText());
        currentSituation=0;
        get_back_team.setVisible(true);
    }
    private void BtnSearchPlayer(ActionEvent event){
        DBController controller=new DBController();
        controller.Connect();
        try {
            map=controller.searchByNameSponsor(name_search.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Parent interfaceViewParent=null;
        try{
            URL url=new File("src/sample/design/search_sponsor.fxml").toURL();
            interfaceViewParent= FXMLLoader.load(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene interfaceViewScene = new Scene(interfaceViewParent, 225, 142);
        Stage window = new Stage();
        window.setScene(interfaceViewScene);
        window.getIcons().add(new Image("file:C://Users//MaksPC/IdeaProjects//NBAdatabase//image_icon.png"));
        window.sizeToScene();
        window.setOnHidden(windowEvent -> {
            if(ControllerSearchSponsor.currentSituation==1){
                Player.initializePlayerView(player_table,map.get("Player"));
                get_back_player.setVisible(true);
                map=null;
            }else if(ControllerSearchSponsor.currentSituation==2){
                ContractSponsor.initializeView(player_table,map.get("Contract Player"));
                get_back_player.setVisible(true);
                map=null;
            }
            ControllerSearchSponsor.currentSituation=0;
        });
        window.show();
    }
}
