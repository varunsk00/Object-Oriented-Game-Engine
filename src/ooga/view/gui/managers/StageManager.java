package ooga.view.gui.managers;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ooga.view.application.games.Game;

//TODO: REFACTOR, CLEAN UP MAGIC, REMOVE UNNECESSARY METHODS
public class StageManager {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private Stage stage;
    private Scene currentScene;
    private Scene pastScene;
    private Map<String, Scene> lastScene;


    public StageManager(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("BOOGA");
        //stage.setWidth(1280);
        //stage.setHeight(720);
        stage.show();
        stage.setFullScreen(true);
        lastScene = new HashMap<String, Scene>();
        //stage.setResizable(false);
    }

    /**
     * overloaded method
     * @param
     */
    public void switchScenes(String title) {
        pastScene = stage.getScene();
        stage.setScene(lastScene.get(title));
        stage.setTitle(title);
        currentScene = lastScene.get(title);
        currentScene.getStylesheets().add("ooga/view/styling/default.css");
    }

    public void switchRoot(Parent parent) {
        stage.getScene().setRoot(parent);
    }
    public Scene getCurrentScene() {
        return currentScene;
    }
    public Scene getPastScene() {
        return pastScene;
    }
    public void createAndSwitchScenes(Parent parentNode) {
        createAndSwitchScenes(parentNode, stage.getTitle());
    }
    public void createAndSwitchScenes(Parent parentNode, String title) {
        pastScene = stage.getScene();
        currentScene = new Scene(parentNode, SCENE_WIDTH, SCENE_HEIGHT);
        currentScene.getStylesheets().add("ooga/view/styling/default.css");
        stage.setScene(currentScene);
        stage.setTitle(title);
    }
    public String getCurrentTitle() {
        return stage.getTitle();
    }
    public void setCurrentTitle(String gameName) {
        stage.setTitle(gameName);
    }

    public Stage getStage(){return stage;}

    public void updateCurrentScene(String title, Scene saveScene) {
        lastScene.put(title, saveScene);
    }
}
