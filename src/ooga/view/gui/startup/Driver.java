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
import ooga.view.gui.GameCabinet;
import ooga.view.gui.StageManager;

import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private static final double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Timeline animation;
    private AnimationTimer animationTimer;
    private BorderPane mainFrame = new BorderPane();
    private Stage myStage;
    private Welcome welcomeScreen = new Welcome();
    private GameCabinet library;
    private MediaPlayer welcomeMusic;
    private StageManager stageManager;
    public Driver(Stage primaryStage) throws FileNotFoundException {
        this.stageManager = new StageManager(primaryStage);
    }
    //TODO: make better css
    public void start() throws Exception {
        library = new GameCabinet(stageManager);
        initBootupScreen();
        startAnimationLoop();
        
        Scene scene = new Scene(mainFrame, SCENE_WIDTH, SCENE_HEIGHT);
//        scene.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        scene.getStylesheets().add("ooga/view/styling/default.css");
    }
//    public void handleAltScrollInput(KeyCode code) {
//        System.out.println(code);
//        if (code == KeyCode.RIGHT) {
//            library.scrollRight();
//        }
//        else if (code == KeyCode.LEFT) {
//            library.scrollLeft();
//        }
//    }

    private void initBootupScreen(){ //FIXME: filepath declared as variable
       // mainFrame.setCenter(welcomeScreen);
        stageManager.createAndSwitchScenes(welcomeScreen);
        welcomeMusic = new MediaPlayer (new Media(new File("src/resources/sample_menu_music.wav").toURI().toString())); //FIXME: CHANGE TO NON-COPYRIGHTED MUSIC
        playSound(welcomeMusic);
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() { //FIXME: Please fix this monstrosity of if statements
        if(welcomeScreen.getPlayPressed()){
            welcomeScreen.setPlayPressedOff();
            mainFrame.setCenter(library);
            mainFrame.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        }
//        if(!myStage.getTitle().equals("BOOGA")){
    //        welcomeMusic.stop();
  //      }
        //library.updateCurrentGame(myStage);
    }

    private void playSound(MediaPlayer sound){
        sound.seek(Duration.ZERO);
        sound.play();
    }

}
