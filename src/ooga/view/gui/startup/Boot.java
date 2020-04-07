package ooga.view.gui.startup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.view.gui.GameCabinet;

import java.io.File;
import java.io.FileNotFoundException;


public class Boot extends Application {
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private static final double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Timeline animation;
    private BorderPane mainFrame = new BorderPane();
    private Stage myStage;
    private Welcome welcomeScreen = new Welcome();
    private GameCabinet library;
    private MediaPlayer welcomeMusic;
    public Boot() throws FileNotFoundException {
    }

    public Boot(String[] args) throws FileNotFoundException {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BOOGA");
        myStage = primaryStage;
        library = new GameCabinet(myStage);
        initBootupScreen();
        startAnimationLoop();
        Scene scene = new Scene(mainFrame, SCENE_WIDTH, SCENE_HEIGHT);
        myStage.setScene(scene);
        myStage.show();
    }

    private void initBootupScreen(){ //FIXME: filepath declared as variable
        mainFrame.setCenter(welcomeScreen);
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
            mainFrame.setCenter(library.getLibrary());
        }
        if(!myStage.getTitle().equals("BOOGA")){
            welcomeMusic.stop();
        }
        library.updateCurrentGame(myStage);
    }

    private void playSound(MediaPlayer sound){
        sound.seek(Duration.ZERO);
        sound.play();
    }

}
