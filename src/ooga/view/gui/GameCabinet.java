package ooga.view.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import ooga.view.application.TestSandbox;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameCabinet extends Application {
    private Scene myScene;
    private static final int SCENE_WIDTH = 1280;
    private static final int SCENE_HEIGHT = 720;
    private static final double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private GameSelectionMenu myGameCabinet;
    private List<GamePreview> myGames;
    private Timeline animation;
    private ResourceBundle myResources;
    private HBox customizationPanel = new HBox();
    private BorderPane mainView = new BorderPane();
    private BorderPane mainFrame = new BorderPane();
    private Stage myStage;
    String selectedGame;

    public GameCabinet() { }

    public GameCabinet(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        primaryStage.setTitle("BOOGA");
        myStage = primaryStage;
        startAnimationLoop();
        initGameSelect();
        setupUI();
        handleGameSelect(myStage);
        Scene scene = new Scene(mainFrame, SCENE_WIDTH, SCENE_HEIGHT);
        myStage.setScene(scene);
        myStage.show();
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        updateCurrentGame();
    }

    private void updateCurrentGame(){
        for(GamePreview game: myGames){
            if(game.getGameName() != null) {
                if(game.getGameName().equals("0x008000ff")) {
                    game.resetGameName();
                    new TestSandbox(myStage);
                    myStage.setTitle("TestSandbox");
                    //myStage.setScene(new TestSandbox(myStage));
                }
            }
        }
    }

    public String getSelectedGame(){
        return selectedGame;
    }

    private void initGameSelect(){
        this.myGames = new ArrayList<>();
        GamePreview g1 = new GamePreview(Color.BLUE);
        GamePreview g2 = new GamePreview(Color.RED);
        GamePreview g3 = new GamePreview(Color.GREEN);
        myGames.add(g1);
        myGames.add(g2);
        myGames.add(g3);
        this.myGameCabinet = new GameSelectionMenu(myGames);
    }

    public void setupUI() {
        mainFrame.setCenter(myGameCabinet);
    }

    private void handleGameSelect(Stage primaryStage) {
        for(GamePreview game: myGames){
//            if(!game.getGameName().equals(null)){
//                System.out.println("YEET");
//            }
        }
//        if (selectedGame.equals("0x008000ff")){
//            System.out.println("YEET");
//        }
    }

}
