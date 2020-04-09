package ooga.view.gui.startup;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
    //TODO: make better css
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("BOOGA");
        myStage = primaryStage;
        library = new GameCabinet(myStage);
        initBootupScreen();
        startAnimationLoop();
        Scene scene = new Scene(mainFrame, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add("ooga/view/styling/default.css");
        myStage.setScene(scene);
        myStage.show();
    }

    private void initBootupScreen(){ //FIXME: filepath declared as variable
        mainFrame.setCenter(welcomeScreen);
        welcomeMusic = new MediaPlayer (new Media(new File("src/resources/menu.wav").toURI().toString())); //FIXME: CHANGE TO NON-COPYRIGHTED MUSIC
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
            mainFrame.setCenter(library);
            mainFrame.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
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
