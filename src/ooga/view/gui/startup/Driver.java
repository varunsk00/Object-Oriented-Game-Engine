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
    private static final double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final String RESOURCES_PACKAGE = this.getClass().getPackageName() + ".resources.";
    private Timeline animation;
    private AnimationTimer animationTimer;
    private Welcome welcomeScreen = new Welcome();
    private GameCabinet library;
    private MediaPlayer welcomeMusic;
    private StageManager stageManager;
    private AVManager audioVideoManager;
    
    public Driver(Stage primaryStage) throws FileNotFoundException {
        this.stageManager = new StageManager(primaryStage);
    }
    //TODO: make better css
    public void start() throws Exception {
        library = new GameCabinet(stageManager);
        initBootupScreen();
        startAnimationLoop();
    }

    private void initBootupScreen(){ //FIXME: filepath declared as variable
        stageManager.createAndSwitchScenes(welcomeScreen);
        welcomeMusic = new MediaPlayer (new Media(new File("src/ooga/view/gui/resources/menu.mp3").toURI().toString())); //FIXME: CHANGE TO NON-COPYRIGHTED MUSIC
        playSound(welcomeMusic);
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
        library.updateCurrentGame(stageManager);

        if(!stageManager.getCurrentTitle().equals("BOOGA")){
            welcomeMusic.stop();
        }
    }

    private void playSound(MediaPlayer sound){
        sound.seek(Duration.ZERO);
        sound.setCycleCount(MediaPlayer.INDEFINITE);
        sound.play();
    }

}
