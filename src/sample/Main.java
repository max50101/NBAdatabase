package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("design/sample.fxml"));
        primaryStage.setTitle("NBA league");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getIcons().add(new Image("file:C:\\Users\\maks\\IdeaProjects\\NBAdatabase\\image_icon.png"));
        primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }
}
