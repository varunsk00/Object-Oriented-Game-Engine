package ooga.view.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.view.gui.managers.AudioVideoManager;
import ooga.view.gui.managers.StageManager;
import ooga.view.gui.userinterface.GameCabinet;
import ooga.view.gui.userinterface.Welcome;

import java.io.FileNotFoundException;

public class ProgramLauncher {

    private static final double FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private final String RESOURCES_PACKAGE = this.getClass().getPackageName() + ".resources.";
    private Timeline animation;
    private Welcome welcomeScreen = new Welcome();
    private GameCabinet library;
    private StageManager stageManager;
    private AudioVideoManager audioVideoManager;

    public ProgramLauncher(Stage primaryStage) throws FileNotFoundException {
        this.stageManager = new StageManager(primaryStage);
        this.audioVideoManager = new AudioVideoManager();
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
            stageManager.createAndSwitchScenes(library, "GameSelect");
            stageManager.updateCurrentScene("GameSelect", library.getScene());
        }
        library.updateCurrentGame();

    }
}
