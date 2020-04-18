package ooga.view.gui.userinterface;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ooga.view.gui.managers.AudioVideoManager;
import ooga.view.gui.managers.StageManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameCabinet extends Pane {
    private GameSelector gameSelector;
    private List<GamePreview> myGames;
    private AudioVideoManager avManager;
    private StageManager stageManager;
    private PlayerSelect myPlayerSelect;
    private int numPlayers;
    private String currentGame;

    public GameCabinet(StageManager stageManager, AudioVideoManager av) throws Exception { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        this.avManager = av;
        this.stageManager = stageManager;
        this.myPlayerSelect = new PlayerSelect();
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
        g3.setGameName("TestSandboxGreen");
        g4.setGameName("TestSandboxYellow");
        g5.setGameName("TestSandboxOrange");
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
                game.resetGameName();
                launchPlayerSelect(stageManager, currentGame);
                disableUnselectedButton();
                avManager.switchMusic(stageManager);
            }
            if (myPlayerSelect.isPlayerSelected()){
                this.numPlayers = myPlayerSelect.playerNumber();
                myPlayerSelect.handleMultiplayer(currentGame);
                myPlayerSelect.resetButtons();
                avManager.switchGame(stageManager, currentGame);
            }
        }
    }

    private boolean isHomePressed(){
        return stageManager.getCurrentTitle().equals("GameSelect");
    }


    private void disableUnselectedButton(){ //FIXME: HORRENDOUS CODE
        if (numPlayers == 2){
            myPlayerSelect.disableP1Button();
        }
        if (numPlayers == 1){
            myPlayerSelect.disableP2Button();
        }
    }
    private void launchPlayerSelect(StageManager sm, String gameName) throws Exception {
        myPlayerSelect = new PlayerSelect(gameName);
        sm.createAndSwitchScenes(myPlayerSelect, gameName);
    }
}
