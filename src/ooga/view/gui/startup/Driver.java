package ooga.view.gui.startup;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.view.gui.AVManager;
import ooga.view.gui.GameCabinet;
import ooga.view.gui.StageManager;

import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    private static final double FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final String RESOURCES_PACKAGE = this.getClass().getPackageName() + ".resources.";
    private Timeline animation;
    private Welcome welcomeScreen = new Welcome();
    private GameCabinet library;
    private StageManager stageManager;
    private AVManager audioVideoManager;

    public Driver(Stage primaryStage) throws FileNotFoundException {
        this.stageManager = new StageManager(primaryStage);
        this.audioVideoManager = new AVManager();
    }
    //TODO: make better css
    public void start() throws Exception {
        library = new GameCabinet(stageManager, audioVideoManager);
        initBootupScreen();
        startAnimationLoop();
    }

    private void initBootupScreen(){ //FIXME: filepath declared as variable
        stageManager.createAndSwitchScenes(welcomeScreen);
        audioVideoManager.switchMusic(stageManager);
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step();
            } catch (Exception ex) { //FIXME: REPLACE WITH STRING
                ex.printStackTrace();
            }
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() throws Exception { //FIXME: Please fix this monstrosity of if statements
        if(welcomeScreen.getPlayPressed()){
            welcomeScreen.setPlayPressedOff();
            stageManager.createAndSwitchScenes(library);
        }
        library.updateCurrentGame();
    }
}
