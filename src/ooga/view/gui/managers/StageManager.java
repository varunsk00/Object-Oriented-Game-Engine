package ooga.view.gui.managers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ooga.exceptions.MissingFileException;
import ooga.view.gui.ProgramLauncher;

//TODO: REFACTOR, CLEAN UP MAGIC, REMOVE UNNECESSARY METHODS
public class StageManager {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private final String DEFAULT_TITLE = "BOOGA";
    private final String GAME_SELECT_TITLE = "BOOGA";
    private Stage stage;
    private Scene currentScene;
    private Scene pastScene;
    private AudioVideoManager avManager;
    private String pastTitle;
    private Map<String, Scene> lastScene;
    private Map<String, Scene> resetGameScenes;


    public StageManager(Stage primaryStage, AudioVideoManager avManager) {
        this.stage = primaryStage;
        this.avManager = avManager;
        stage.setTitle(DEFAULT_TITLE);
        stage.show();
        stage.setResizable(false);
        lastScene = new HashMap<String, Scene>();
        resetGameScenes = new HashMap<String, Scene>();
    }

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
    public void createAndSwitchScenes(Parent parentNode) { createAndSwitchScenes(parentNode, stage.getTitle()); }
    public void createAndSwitchScenes(Parent parentNode, String title) {
        pastScene = stage.getScene();
        pastTitle = stage.getTitle();
        currentScene = new Scene(parentNode, SCENE_WIDTH, SCENE_HEIGHT);
        currentScene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (Exception fileNotFoundException) {
                new MissingFileException(fileNotFoundException, "resourceFile"); } });
        currentScene.getStylesheets().add("ooga/view/styling/default.css");
        stage.setScene(currentScene);
        stage.setTitle(title);
    }

    private void handleKeyInput (KeyCode code) throws Exception {
        if(code == KeyCode.H){
            stage.setScene(pastScene);
            stage.setTitle(GAME_SELECT_TITLE); }
        if(code == KeyCode.R){
            reboot(); }
    }

    public String getCurrentTitle() {
        return stage.getTitle();
    }
    public void setCurrentTitle(String gameName) {
        stage.setTitle(gameName);
    }

    public AudioVideoManager getAvManager() {
        return avManager;
    }

    public Stage getStage(){return stage;}

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
