package sample.ControllerClasses;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label hbox;

    @FXML
    private Button log_but;

    @FXML
    private Label error;
    @FXML
    void initialize() {
        error.setVisible(false);
       log_but.setOnAction(event -> {
           String name=login_field.getText();
           String password=password_field.getText();
           if(name.equals("admin")&&password.equals("admin")) {
               Parent interfaceViewParent = null;
               try {
                   URL url = new File("src/sample/design/interface.fxml").toURL();
                   interfaceViewParent = FXMLLoader.load(url);
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Scene interfaceViewScene = new Scene(interfaceViewParent, 652, 436);
               Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
               window.setScene(interfaceViewScene);
               window.sizeToScene();
               window.show();
           }else  if(name.equals("viewer")&&password.equals("viewer")){
               Parent interfaceViewParent=null;
               try{
                   URL url=new File("src/sample/design/viewer.fxml").toURL();
                   interfaceViewParent=FXMLLoader.load(url);
               }catch (MalformedURLException e){
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Scene interfaceViewScene = new Scene(interfaceViewParent, 652, 436);
               Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
               window.setScene(interfaceViewScene);
               window.sizeToScene();
               window.show();
           }else if(name.equals("sponsor")&&password.equals("sponsor")){
               Parent interfaceViewParent=null;
               try{
                   URL url=new File("src/sample/design/sponsor_view.fxml").toURL();
                   interfaceViewParent=FXMLLoader.load(url);
               }catch (MalformedURLException e){
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               Scene interfaceViewScene = new Scene(interfaceViewParent, 600, 424);
               Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
               window.setScene(interfaceViewScene);
               window.sizeToScene();
               window.show();
           }
           else{
               error.setVisible(true);
           }
       });

    }

}
