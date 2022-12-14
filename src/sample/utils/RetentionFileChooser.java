package sample.utils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

public class RetentionFileChooser {
    private static FileChooser instance = null;
    private static SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();

    private RetentionFileChooser(){ }

    private static FileChooser getInstance(){
        if(instance == null) {
            instance = new FileChooser();
           instance.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
          //  instance.setInitialDirectory(new File("E:\\back_up"));
            //Set the FileExtensions you want to allow
            //instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
            instance.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("tar file (*.tar)","*.tar"));

        }
        return instance;
    }

    public static File showOpenDialog(){
        return showOpenDialog(null);
    }

    public static File showOpenDialog(Window ownerWindow){
        File chosenFile = getInstance().showOpenDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }

    public static File showSaveDialog(){
        return showSaveDialog(null);
    }

    public static File showSaveDialog(Window ownerWindow){
        File chosenFile = getInstance().showSaveDialog(ownerWindow);
        if(chosenFile != null){
            //Set the property to the directory of the chosenFile so the fileChooser will open here next
            lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
        }
        return chosenFile;
    }
}
