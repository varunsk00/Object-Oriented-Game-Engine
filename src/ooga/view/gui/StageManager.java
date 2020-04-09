package ooga.view.gui;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StageManager {
    private Stage stage;
    private Scene currentScene;
    private Scene pastScene;

    public StageManager(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("BOOGA");
//        stage.setWidth(1280);
//        stage.setHeight(720);
        stage.show();
        stage.setFullScreen(true);
    }

    /**
     * overloaded method
     * @param newScene
     */
    public void switchScenes(Scene newScene) {
        this.switchScenes(newScene, stage.getTitle());
    }
    public void switchScenes(Scene newScene, String title) {
        pastScene = stage.getScene();
        stage.setScene(newScene);
        stage.setTitle(title);
        currentScene = newScene;
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
        pastScene = stage.getScene();
        currentScene = new Scene(parentNode);
        stage.setScene(currentScene);
        stage.setFullScreen(true);
    }

}
