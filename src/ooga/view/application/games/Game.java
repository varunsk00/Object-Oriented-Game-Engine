package ooga.view.application.games;

import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import ooga.controller.GameController;
import ooga.view.gui.managers.StageManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Game {
    private final String RESOURCES_PACKAGE = "ooga.view.application.games.resources.";
    private final String CONTROLLER_PACKAGE = "ooga.controller.";
    private final String CONTROLLER = "Controller";
    private final String GAME_PACKAGE = RESOURCES_PACKAGE + "GameTypes";
    private ResourceBundle myGameTypes = ResourceBundle.getBundle(GAME_PACKAGE);
    private Pane myBackgroundPane;
    private StageManager stageManager;
    private Scene oldScene;
    private String game;
    private Scene currentScene;
    private GameController mainController;
    private Map<String, String> gameTypes;


    public Game(StageManager stageManager) {
        this.stageManager = stageManager;
        this.oldScene = stageManager.getPastScene();
        this.currentScene = stageManager.getCurrentScene();
        this.myBackgroundPane = new Pane();
        this.gameTypes = new HashMap<>();
        loadGameTypes();
        initStage();
    }

    public void setBackground(BackgroundFill bg){
        myBackgroundPane.setBackground(new Background(bg));
    }

    public void loadGame(String gameName) throws XInputNotLoadedException {
        stageManager.setCurrentTitle(gameName);
//        game = gameName;
        this.mainController = new GameController(stageManager, gameName); //FIXME MAGIC VALUE
    }

    private void loadGameTypes(){
        Enumeration<String> keys = myGameTypes.getKeys();
        List<String> keysAsList = new ArrayList<>();
        while(keys.hasMoreElements()){
            keysAsList.add(keys.nextElement());
        }
        for (String key : keysAsList) {
            String value = myGameTypes.getString(key);
            gameTypes.put(key, value);
        }
    }

    private void initStage() {
        stageManager.createAndSwitchScenes(myBackgroundPane, game);
        System.out.println("GAME CLASS:" + game);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(StageManager sm) {
        this.currentScene = sm.getCurrentScene();
    }
}