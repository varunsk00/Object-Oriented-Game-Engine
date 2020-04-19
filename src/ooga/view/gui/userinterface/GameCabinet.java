package ooga.view.gui.userinterface;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ooga.view.gui.managers.AudioVideoManager;
import ooga.view.gui.managers.StageManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameCabinet extends Pane {
    private GameSelector gameSelector;
    private List<GamePreview> myGames;
    private AudioVideoManager avManager;
    private StageManager stageManager;
    private TitleScreen myTitleScreen;
    private Map<String, Integer> gameLaunchCount = new HashMap<>();
    private int numPlayers;
    private String currentGame;

    public GameCabinet(StageManager stageManager, AudioVideoManager av) throws Exception { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        this.avManager = av;
        this.stageManager = stageManager;
        this.myTitleScreen = new TitleScreen();
        initGameSelect();
        gameSelector = new GameSelector(myGames);
        this.getChildren().add(gameSelector);
        this.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        updateCurrentGame();
    }

    public void addNewGamePreview(GamePreview newGamePreview) {
        this.myGames.add(newGamePreview);
    }

    private void initGameSelect() throws FileNotFoundException { //FIXME: STREAMLINE INSTANTIATION TO READ FROM A FILE
        GamePreview g1 = new GamePreview(Color.BLUE, "flappybird");
        GamePreview g2 = new GamePreview(Color.RED, "mario1-1");
        GamePreview g3 = new GamePreview(Color.GREEN, "zelda2");
        GamePreview g4 = new GamePreview(Color.YELLOW, "metroid");
        GamePreview g5 = new GamePreview(Color.ORANGE, "varun");
        g1.setGameName("FlappyBird");
        g2.setGameName("Mario");
        g3.setGameName("Zelda");
        g4.setGameName("Metroid");
        g5.setGameName("Varun");
        myGames.add(g1);
        myGames.add(g2);
        myGames.add(g3);
        myGames.add(g4);
        myGames.add(g5);
    }

    public void handleAltScrollInput(KeyCode code) {
        if (code == KeyCode.D) {
            this.gameSelector.scrollRight();
        }
        else if (code == KeyCode.A) {
            this.gameSelector.scrollLeft();
        }
    }

    public void updateCurrentGame() throws Exception {
        if(isHomePressed()){
            avManager.switchMusic(stageManager);
            stageManager.setCurrentTitle("BOOGA");
        }
        for(GamePreview game: myGames){
            if(game.isGamePressed()) {
                currentGame = game.getGameName();
                game.resetGameButton();
                if(isRelaunched(currentGame)){
                    avManager.switchGame(stageManager, currentGame); }
                else{
                    launchTitleScreen(stageManager, currentGame); }
                avManager.switchMusic(stageManager);
            }
            if (myTitleScreen.isPlayerSelected()){
                incrementLaunchCounter(currentGame);
                this.numPlayers = myTitleScreen.playerNumber();
                myTitleScreen.handleMultiplayer(currentGame);
                myTitleScreen.resetButtons();
                avManager.switchGame(stageManager, currentGame);
            }
        }
    }

    private void incrementLaunchCounter(String game){
        Integer count = gameLaunchCount.get(game);
        if(count == null){
            gameLaunchCount.put(game, 1); }
        else{
            gameLaunchCount.put(game, count + 1); }
    }

    private boolean isRelaunched(String game){ //FIXME: IS THIS A FAIL CASE? I'M CHECKING FOR NULL, SO IDK
        if(gameLaunchCount.get(game) != null){
            return gameLaunchCount.get(game) > 0; }
        return false;
    }

    private boolean isHomePressed(){
        return stageManager.getCurrentTitle().equals("GameSelect");
    }

    private void launchTitleScreen(StageManager sm, String gameName) throws Exception {
        myTitleScreen = new TitleScreen(gameName);
        sm.createAndSwitchScenes(myTitleScreen, gameName);
    }

}
