package ooga.view.gui.managers;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ooga.view.application.games.Game;
import ooga.view.gui.ProgramLauncher;
import ooga.view.gui.userinterface.Welcome;

//TODO: REFACTOR, CLEAN UP MAGIC, REMOVE UNNECESSARY METHODS
public class StageManager {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private Stage stage;
    private Scene currentScene;
    private Scene pastScene;
    private AudioVideoManager avManager;
    private String pastTitle;
    private Map<String, Scene> lastScene;
    private Map<String, Scene> resetGameScenes;


    public StageManager(Stage primaryStage, AudioVideoManager avManager) throws FileNotFoundException {
        this.stage = primaryStage;
        this.avManager = avManager;
        stage.setTitle("BOOGA");
        stage.show();
        stage.setResizable(false);
        lastScene = new HashMap<String, Scene>();
        resetGameScenes = new HashMap<String, Scene>();
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

    public void switchRestartScenes(String title) {
        pastScene = stage.getScene();
        stage.setScene(resetGameScenes.get(title));
        stage.setTitle(title);
        currentScene = resetGameScenes.get(title);
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
    public void createAndSwitchScenes(Parent parentNode) { createAndSwitchScenes(parentNode, stage.getTitle()); }
    public void createAndSwitchScenes(Parent parentNode, String title) {
        pastScene = stage.getScene();
        pastTitle = stage.getTitle();
        currentScene = new Scene(parentNode, SCENE_WIDTH, SCENE_HEIGHT);
        currentScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (Exception fileNotFoundException) {
                fileNotFoundException.printStackTrace();//FIXME: TO AVOID FAILING CLASS
            }
        });;
        currentScene.getStylesheets().add("ooga/view/styling/default.css");
        stage.setScene(currentScene);
//        resetGameScenes.put(title, currentScene);
        stage.setTitle(title);
    }

    private void handleKeyInput (KeyCode code) throws Exception { //TODO: COMBINE WITH HOME AND SUSPEND POINTS
        if(code == KeyCode.H){
            stage.setScene(pastScene);
            stage.setTitle("GameSelect");
        }
        if(code == KeyCode.R){
            reboot();
        }
    }

    public String getCurrentTitle() {
        return stage.getTitle();
    }
    public void setCurrentTitle(String gameName) {
        stage.setTitle(gameName);
    }

    public Stage getStage(){return stage;}

    public void saveResetGameScenes(String title, Scene resetScene) {
        resetGameScenes.put(title, resetScene);
    }

    public void updateCurrentScene(String title, Scene saveScene) {
        lastScene.put(title, saveScene);
    }

    public void reboot() throws Exception {
        ProgramLauncher launcher = new ProgramLauncher(stage);
        avManager.close();
        avManager.switchMusic(this);
        launcher.start();
    }
}
