package sample.ControllerClasses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.Database.*;
import sample.utils.RetentionFileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController {
    public static boolean currentSituation;
    private Team currentTeam=new Team();
    private Player currentPlayer=new Player();
    private Coach currentCoach=new Coach();
    private Manager currentManager=new Manager();
    private ContractSponsor contractSponsor=new ContractSponsor();
    private Progress currentProgress=new Progress();
    private PlayerStats currentPlayerStats=new PlayerStats();
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> combo_box;
    @FXML
    private TableView<Table> table_view;
    @FXML
    private Button open_table;
    @FXML
    private Button insert_into;

    @FXML
    private Button update;

    @FXML
    private Button delete_from;
    @FXML
    private Button get_back;

    @FXML
    private Button full_get_back;

    @FXML
    private Button clear_temp;

    @FXML
    private CheckBox check_box_1;
    @FXML
    private Button btn_import;

    @FXML
    private Button btn_export;
    @FXML
    void initialize() {
        combo_box.getItems().addAll("Team",
                "Player",
                "Coach",
                "Manager",
                "Player statistic",
                "Progress",
                "Sponsor to players",
                "Sponsor to teams");
        combo_box.setValue("Team" +
                "" +
                "");
        table_view.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        get_back.setDisable(true);
        full_get_back.setDisable(true);
        clear_temp.setDisable(true);
        check_box_1.setOnAction(this::handleCheckBox);
        openTable();
       // table_view.setVisible(false);

       open_table.setOnAction(event -> {
           openTable();
       });
       get_back.setOnAction(this::handleGetBackButton);
       insert_into.setOnAction(this::handleBtnInsertAction);
       full_get_back.setOnAction(this::handleFullGetBack);
       clear_temp.setOnAction(this::hanndleClearTemp);
       table_view.setOnMouseClicked(mouseEvent -> {
           if (table_view.getSelectionModel().getSelectedItem() != null) {
               switch (combo_box.getValue()){
                   case "Team":
                       currentTeam= (Team) table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Player":
                       currentPlayer=(Player) table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Coach":
                       currentCoach=(Coach)table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Manager":
                       currentManager=(Manager) table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Sponsor to teams":
                       contractSponsor=(ContractSponsor) table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Sponsor to players":
                       contractSponsor=(ContractSponsor) table_view.getSelectionModel().getSelectedItem();
                        break;
                   case "Progress":
                       currentProgress=(Progress) table_view.getSelectionModel().getSelectedItem();
                       break;
                   case "Player statistic":
                       currentPlayerStats=(PlayerStats)table_view.getSelectionModel().getSelectedItem();
                       break;
               }

           }
       });
       delete_from.setOnAction(event -> {
           deleteFromDB();
       });
       update.setOnAction(event -> {
           updateDB();
       });
       btn_export.setOnAction(this::handleBtnExportAction);
       btn_import.setOnAction(this::handleBtnImportAction);

    }


   private void handleBtnExportAction(ActionEvent event){
        DBController controller=new DBController();
        controller.Connect();
        File file=new DirectoryChooser().showDialog(new Stage());
        controller.exportData(file.getPath());
        controller.closeConnection();
   }
   private void handleBtnImportAction(ActionEvent event){
        DBController controller=new DBController();
        controller.Connect();
        File file= RetentionFileChooser.showOpenDialog(new Stage());
        controller.importData(file.getPath());
        controller.closeConnection();
    }
    @FXML
    private void handleBtnInsertAction(ActionEvent event){
        Parent root;
        int x=0;
        int y=0;
        try{
            URL url=null;
            switch (combo_box.getValue()){
                case "Team":
                    url=new File("src/sample/design/InsertTeam.fxml").toURL();
                    x=310;
                    y=305;
                    break;
                case "Player":
                    url=new File("src/sample/design/insertPlayer.fxml").toURL();
                    x=449;
                    y=327;
                    break;
                case "Coach":
                    currentSituation=true;
                    url=new File("src/sample/design/insertCoach.fxml").toURL();
                    x=450;
                    y=328;
                    break;
                case "Manager":
                    currentSituation=false;
                    url=new File("src/sample/design/insertCoach.fxml").toURL();
                    x=450;
                    y=328;
                    break;
                case "Sponsor to teams":
                    currentSituation=true;
                    url=new File("src/sample/design/insertSponsor.fxml").toURL();
                    x=450;
                    y=328;
                    break;
                case "Sponsor to players":
                    currentSituation=false;
                    url=new File("src/sample/design/insertSponsor.fxml").toURL();
                    x=450;
                    y=328;
                    break;
                case "Progress":
                    url=new File("src/sample/design/insertProgress.fxml").toURL();
                    x=215;
                    y=133;
                    break;
                case "Player statistic":
                    url=new File("src/sample/design/insertPlayerStats.fxml").toURL();
                    x=451;
                    y=327;
                    break;
            }

            assert url != null;
            root = FXMLLoader.load(url);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.getIcons().add(new Image("file:C://Users//MaksPC/IdeaProjects//NBAdatabase//image_icon.png"));
            stage.setScene(new Scene(root, x, y));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteFromDB(){
        DBController dbController=new DBController();
        dbController.Connect();
        switch (combo_box.getValue()){
            case "Team":
                dbController.deleteTeam(currentTeam.getId());
                break;
            case "Player":
                dbController.deletePlayer(currentPlayer);
                break;
            case "Coach":
                dbController.deleteCoach(currentCoach);
                break;
            case "Manager":
                dbController.deleteManager(currentManager);
                break;
            case "Sponsor to teams":
                dbController.deleteContractTeam(contractSponsor);
                break;
            case "Sponsor to players":
                dbController.deleteContractPlayer(contractSponsor);
                break;
            case "Progress":
                dbController.deleteProgress(currentProgress);
                break;
            case "Player statistic":
                dbController.deletePlayerStats(currentPlayerStats);
                break;
        }
        dbController.closeConnection();
        openTable();
    }
    @FXML
    private void updateDB(){
        DBController dbController=new DBController();
        dbController.Connect();
        switch (combo_box.getValue()) {
            case "Team":
                dbController.updateTeam(currentTeam);
                break;
            case "Player":
                dbController.updatePlayer(currentPlayer);
                break;
            case  "Coach":
                dbController.updateCoach(currentCoach);
                break;
            case "Manager":
                dbController.updateManager(currentManager);
                break;
            case "Sponsor to teams":
                dbController.updateContractTeam(contractSponsor);
                break;
            case "Sponsor to players":
                dbController.updateContractPlayer(contractSponsor);
                break;
            case "Progress":
                dbController.updateProgress(currentProgress);
                break;
            case "Player statistic":
                dbController.updatePlayerStats(currentPlayerStats);
                break;
        }
            dbController.closeConnection();
            openTable();
        }

    private void openTable() {
        switch (combo_box.getValue()){
            case "Team": Team.initialize(table_view);break;
            case "Player":Player.initialize(table_view);break;
            case "Coach": Coach.initialize(table_view);break;
            case "Manager":Manager.initialize(table_view);break;
            case "Sponsor to teams": currentSituation=true;ContractSponsor.initialize(table_view);break;
            case "Sponsor to players": currentSituation=false; ContractSponsor.initialize(table_view);break;
            case "Progress": Progress.initialize(table_view);break;
            case "Player statistic":PlayerStats.initialize(table_view);break;
        }


    }


    private void comboBox(){
        switch (combo_box.getValue()) {
            case "Team":
                openTable();
                break;
            case "Player":
                openTable();
                break;
        }
    }
    private void handleGetBackButton(ActionEvent event){
        DBController dbController=new DBController();
        dbController.Connect();
        dbController.getBack();
        dbController.closeConnection();
        openTable();
    }
    private void handleCheckBox(ActionEvent event) {
        if (!check_box_1.isSelected()) {
            get_back.setDisable(true);
            full_get_back.setDisable(true);
            clear_temp.setDisable(true);
        } else {
            get_back.setDisable(false);
            full_get_back.setDisable(false);
            clear_temp.setDisable(false);
        }
    }
    private void handleFullGetBack(ActionEvent event){
            DBController controller=new DBController();
            controller.Connect();
            controller.fullGetBack();
            controller.closeConnection();
            openTable();
    }
    private  void hanndleClearTemp(ActionEvent event){
            DBController controller=new DBController();
            controller.Connect();
            controller.clearTemp();
            controller.closeConnection();
    }
    }



