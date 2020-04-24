package ooga.controller;


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
import ooga.view.gui.managers.StageManager;

public class ViewManager implements ViewExternalAPI {
  private BorderPane testPane;
  private Group EntityGroup;

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

  private Scene testScene;

  /**
   * NEED TO REFACTOR
   * @Deprecated fuck
   */
  public ViewManager(StageManager stageManager, InfiniteLevelBuilder builder){
    this.menu = new InGameMenu();
    //TODO: Quick and dirty nodes for testing purpose -- replace with Entity stuff
    currentStage = stageManager;
    level = builder.generateLevel();

    testPane = level;
    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);

    EntityGroup = new Group();
    level.getChildren().add(EntityGroup);

    this.testScene = stageManager.getCurrentScene();
  }

  public Pane getLevel() {
    return level;
  }

  public void setUpCamera(List<EntityWrapper> node) { camera = new Camera(currentStage.getStage(), level, node); }

  public StageManager getCurrentStage() {
    return currentStage;
  }
  public void updateEntityGroup(Node node) {
    EntityGroup.getChildren().add(node);
  }
  public void removeEntityGroup(Node node) {
    EntityGroup.getChildren().remove(node);
  }

  public Scene getTestScene() {
    return testScene;
  }

  public void setLevel(BorderPane levelBuilt) {
    this.level = levelBuilt;
  }

  public void updateValues() {
    camera.update(menu);
  }

  @Override
  public void updateEntityPosition(int id, double newx, double newy) {

  }

  @Override
  public void removeEntity(Node node) {
    EntityGroup.getChildren().remove(node);
  }

  @Override
  public void addEntity(Node node) {
    EntityGroup.getChildren().add(node);
  }

  @Override
  public void updateEntity(int id, String newValue) {

  }

  @Override
  public void setUpGameView(String gameSelect) {

  }

  @Override
  public void checkCollisions() {

  }

  public void step() {

  }

  public void addEntityRender(Node node) {
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

  public void addScene(String title) {

  }
  public void handleReleaseInput (KeyCode code) {
  }

  public boolean getSaveGame() {
    return saveGame;
  }


  public void handleMenuInput(GameParser gp) throws Exception { //TODO: REFACTOR
    if (menu.getResumePressed()) {
      unPauseGame();
      menu.setResumeOff();
    }
    if (menu.getSavePressed()) {
      saveGame=true;
      unPauseGame();
      menu.setSaveOff();
    }
    if (menu.getExitPressed()) { //FIXME: FIX THIS BUT I DIDN'T WANT TO BREAK SHRUTHI'S SAVE POINTS, ideally should independently go home
      handlePressInput(KeyCode.H);
      menu.setExitOff();
    }
    if (menu.getControlsPressed() && configCounter < 1){
      launchConfigMenu(gp);
      menu.setControlsOff();
    }
    if (menu.getRebootPressed()){
      menu.setRebootOff();
      currentStage.reboot();
    }
  }

  private void goHome(String state){
    currentStage.updateCurrentScene(currentStage.getCurrentTitle(), currentStage.getCurrentScene());
    currentStage.updateCurrentScene(state, currentStage.getPastScene());
    currentStage.switchScenes("GameSelect");
  }

  public void saveResetScenes(String state) {
    currentStage.saveResetGameScenes(state, currentStage.getCurrentScene());
  }

  public void resetLevelScene(String gameName) {
    currentStage.switchRestartScenes(gameName);

  }

  public void endGame() {
    //need to reset game;
    goHome(KeyCode.H.getChar());
  }


  private void pauseGame(){
    BoxBlur bb = new BoxBlur();
    EntityGroup.setEffect(bb);
    isGamePaused = true;
    testPane.setLeft(menu);
    escCounter++;
  }

  private void launchConfigMenu(GameParser gp){
    config = new ControlSchemeSwitcher(gp);
    menu.getChildren().add(config);
    configCounter++;
  }

  private void unPauseGame(){
    testPane.getChildren().remove(menu);
    menu.getChildren().remove(config);
    EntityGroup.setEffect(null);
    isGamePaused = false;
    escCounter--;
    configCounter = 0;
  }

  public boolean getIsGamePaused() {
    return isGamePaused;
  }

  public void setSaveGame() {
    saveGame = !saveGame;
  }

  public boolean getControlSwitcher(){
    return launchControlSwitcher;
  }
}
