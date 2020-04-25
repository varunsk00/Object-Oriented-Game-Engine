package ooga.controller;


import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;


import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import ooga.apis.view.ViewExternalAPI;
import ooga.model.levels.InfiniteLevelBuilder;
import ooga.util.GameParser;
import ooga.view.application.Camera;
import ooga.view.application.menu.InGameMenu;
import ooga.view.entity.EntityView;
import ooga.view.gui.managers.StageManager;

public class ViewManager implements ViewExternalAPI {
  private BorderPane testPane;
  private Group EntityGroup;

  private List<Node> overlay = new ArrayList<>();
  private InGameMenu menu;
  private ControlSchemeSwitcher config;
  private int escCounter = 0;
  private int configCounter = 0;

  private StageManager currentStage;
  private BorderPane level;
  private Camera camera;
  private boolean launchControlSwitcher;
  private boolean saveGame = false;

  private boolean isGamePaused = false;
  private final String DEFAULT_MENU_TEXT = "Game in Progress";

  private Scene testScene;

  public ViewManager(StageManager stageManager, InfiniteLevelBuilder builder, List<EntityWrapper> playerList){
    this.menu = new InGameMenu();
    this.config = new ControlSchemeSwitcher(playerList);
    this.overlay.add(menu);
    this.overlay.add(config);

    currentStage = stageManager;
    setUpScene(builder);
    this.testScene = stageManager.getCurrentScene();
  }

  private void setUpScene(InfiniteLevelBuilder builder) {
    level = builder.generateLevel();
    testPane = level;

    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);

    EntityGroup = new Group();
    level.getChildren().add(EntityGroup);
  }

  public Pane getLevel() {
    return level;
  }

  public void setUpCamera(List<EntityWrapper> node, int scrollStatusX, int scrollStatusY) { camera = new Camera(currentStage.getStage(), level, node, scrollStatusX, scrollStatusY); }

//  public StageManager getCurrentStage() {
//    return currentStage;
//  }

  public Scene getTestScene() {
    return testScene;
  }

  public void setLevel(BorderPane levelBuilt) {
    this.level = levelBuilt;
  }

  public void updateValues() {
    camera.update(overlay);
  }


  @Override
  public void removeEntity(Node node) {
    EntityGroup.getChildren().remove(node);
  }

  @Override
  public void addEntity(Node node) {
    EntityGroup.getChildren().add(node);
  }

  public void handlePressInput (KeyCode code) {
    if (code == KeyCode.ESCAPE && escCounter < 1) {
      pauseGame();
    } else if (code == KeyCode.Q && escCounter == 1) {
      unPauseGame();
    }
    else if (code == KeyCode.H) {
      pauseGame();
      goHome(code.getChar());
    } else if(code == KeyCode.X) {
      saveGame = true;
    }

  }

//  public void addScene(String title) {
//
//  }
//  public void handleReleaseInput (KeyCode code) {
//  }

  public boolean getSaveGame() {
    return saveGame;
  }


  public void handleMenuInput() throws Exception { //TODO: REFACTOR
    resumeGame();
    saveGame();
    exitGame();
    editControls();
    rebootGame();
  }

  private void rebootGame() throws Exception {
    if (menu.getRebootPressed()){
      menu.setRebootOff();
      currentStage.reboot();
    }
  }

  private void editControls() {
    if (menu.getControlsPressed()){
      menu.setControlsOff();
      if(configCounter < 1 && menu.getStatus().equals(DEFAULT_MENU_TEXT)){
        launchConfigMenu(); } }
    if(config.getExitPressed()){
      config.setExitOff();
      handlePressInput(KeyCode.Q); }
  }

  private void exitGame() {
    if (menu.getExitPressed()) {
      handlePressInput(KeyCode.H);
      menu.setExitOff();
    }
  }

  private void saveGame() {
    if (menu.getSavePressed()) {
      saveGame=true;
      unPauseGame();
      menu.setSaveOff();
    }
  }

  private void resumeGame() {
    if (menu.getResumePressed()) {
      unPauseGame();
      menu.setResumeOff();
    }
  }

  private void goHome(String state){
    currentStage.updateCurrentScene(currentStage.getCurrentTitle(), currentStage.getCurrentScene());
    currentStage.updateCurrentScene(state, currentStage.getPastScene());
    currentStage.switchScenes("GameSelect");
  }

//  public void saveResetScenes(String state) {
//    currentStage.saveResetGameScenes(state, currentStage.getCurrentScene());
//  }
//
//  public void resetLevelScene(String gameName) {
//    currentStage.switchRestartScenes(gameName);
//
//  }

  public void endGame() {
    //need to reset game;
    goHome(KeyCode.H.getChar());
  }


  public void pauseGame(){
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    isGamePaused = true;
    testPane.setLeft(menu);
    escCounter++;
  }

  private void launchConfigMenu(){
    testPane.setCenter(config);
    configCounter++;
  }

  private void unPauseGame(){
    updateMenu(DEFAULT_MENU_TEXT);
    testPane.getChildren().remove(config);
    testPane.getChildren().remove(menu);
    EntityGroup.setEffect(null);
    isGamePaused = false;
    escCounter--;
    configCounter = 0;
  }

  public void updateMenu(String text) {
    menu.updateGameResult(text);
  }

  public boolean getIsGamePaused() {
    return isGamePaused;
  }

  public void setGamePaused() {
    isGamePaused = true;
  }

  public void setSaveGame() {
    saveGame = !saveGame;
  }

  public boolean getControlSwitcher(){
    return launchControlSwitcher;
  }

  public Camera getCamera(){
    return camera;
  }
}
