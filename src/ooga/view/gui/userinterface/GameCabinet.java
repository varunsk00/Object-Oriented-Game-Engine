package ooga.view.gui.userinterface;

import java.io.FileReader;
import java.io.IOException;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.view.gui.managers.AudioVideoManager;
import ooga.view.gui.managers.StageManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameCabinet extends Pane {
    private GameSelector gameSelector;
    private List<GamePreview> myGames;
    private AudioVideoManager avManager;
    private StageManager stageManager;
    private TitleScreen myTitleScreen;
    private Map<String, Integer> gameLaunchCount = new HashMap<>();
    private int numPlayers;
    private String currentGame;
    private String gameListFilePath = "src/resources/GameList.json";


  public GameCabinet(StageManager stageManager, AudioVideoManager av) throws Exception { //FIXME ADD ERROR HANDLING
        this.myGames = new ArrayList<>();
        this.avManager = av;
        this.stageManager = stageManager;
        this.myTitleScreen = new TitleScreen();
        List<String> gameList = this.readGameList();
        initGameSelect(gameList);
        gameSelector = new GameSelector(myGames);
        this.getChildren().add(gameSelector);
        this.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        updateCurrentGame();
    }

    private List<String> readGameList(){
      List<String> gameList = new ArrayList<>();
      JSONObject gameListJSONObject = (JSONObject) readJsonFile();
      JSONArray gameListJSONArray = (JSONArray) gameListJSONObject.get("gameList");
      for(int i = 0; i < gameListJSONArray.size(); i++){
        gameList.add(gameListJSONArray.get(i).toString());
      }
      return gameList;
    }

  public Object readJsonFile() {
    try {
      FileReader reader = new FileReader(gameListFilePath);
      JSONParser jsonParser = new JSONParser();
      return jsonParser.parse(reader);
    } catch (IOException | ParseException e) {
      throw new InvalidControlSchemeException(e);
    }
  }

    public void addNewGamePreview(GamePreview newGamePreview) {
        this.myGames.add(newGamePreview);
    }

    private void initGameSelect(List<String> gameList) throws FileNotFoundException { //FIXME: STREAMLINE INSTANTIATION TO READ FROM A FILE
      for(int i = 0; i < gameList.size(); i++){
        GameSelectParser newGameParser = new GameSelectParser(gameList.get(i));
        GamePreview newGame = new GamePreview(Color.BLUE, newGameParser.readGamePreviewGIF());
        newGame.setGameName(newGameParser.readGameName());
        myGames.add(newGame);
      }
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
                    avManager.switchGame(stageManager, currentGame, false); }
                else{
                    launchTitleScreen(stageManager, currentGame); }
                avManager.switchMusic(stageManager);
            }
            if (myTitleScreen.isPlayerSelected()){
                incrementLaunchCounter(currentGame);
                if(!myTitleScreen.isLoadSavedGame()) {
                    this.numPlayers = myTitleScreen.playerNumber();
                    myTitleScreen.handleMultiplayer(currentGame);
                }

                avManager.switchGame(stageManager, currentGame, myTitleScreen.isLoadSavedGame());
                myTitleScreen.resetButtons();


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
