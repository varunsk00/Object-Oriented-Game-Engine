package ooga.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class GameCabinet {
    private static final int HEIGHT = 576;
    private static final int WIDTH = 1080;
    private static final String RESOURCES = "resources/languages";
    private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES + "/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES + ".";
    private static final String STYLESHEET = "resources/stylesheets/default.css";

    private ResourceBundle myResources;
    private HBox customizationPanel = new HBox();
    private BorderPane mainView = new BorderPane();
    private BorderPane mainFrame = new BorderPane();


    public GameCabinet(Stage stage, ResourceBundle myResources) {
        stage.setTitle(myResources.getString("Title"));
    }
    public Scene setupUI() {
        setFooter();
        mainView.setTop(customizationPanel);
        mainFrame.setCenter(mainView);

        Scene myScene = new Scene(mainFrame, WIDTH, HEIGHT);
        myScene.getStylesheets().add(STYLESHEET);
        return myScene;
    }

    private void setFooter() {

    }

}
