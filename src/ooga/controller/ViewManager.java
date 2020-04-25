package ooga.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.*;
import javax.swing.text.html.parser.Entity;
import ooga.apis.view.ViewExternalAPI;
import ooga.view.application.Camera;
import ooga.view.application.menu.ConfigurationMenu;
import ooga.view.application.menu.InGameMenu;
import ooga.view.gui.managers.StageManager;

public class ViewManager implements ViewExternalAPI {
  private static final String RESOURCES_PACKAGE = "resources.guiText";
  private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
  private final String DEFAULT_MENU_TEXT = myResources.getString("defaultStatus");
  private BorderPane testPane;
  private Group entityGroup;
  private List<Node> overlay = new ArrayList<>();
  private InGameMenu menu;
  private ConfigurationMenu config;
  private StageManager currentStage;
  private BorderPane level;
  private Camera camera;
  private Scene testScene;
  private int escCounter = 0;
  private int configCounter = 0;
  private boolean saveGame = false;
  private boolean isGamePaused = false;

  public ViewManager(StageManager stageManager, List<EntityWrapper> playerList){
    menu = new InGameMenu();
    config = new ConfigurationMenu(playerList);
    overlay.add(menu);
    overlay.add(config);
    currentStage = stageManager;
    level = new BorderPane();
    testPane = level;
    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);
    entityGroup = new Group();
    level.getChildren().add(entityGroup);
    setUpPane();
    testScene = stageManager.getCurrentScene();
  }

  private void setUpPane(){
    level = new BorderPane();
    testPane = level;
    testScene = currentStage.getCurrentScene();
    testScene.setRoot(testPane);
    entityGroup = new Group();
    level.getChildren().add(entityGroup);
  }


  public Pane getLevel() {
    return level;
  }

  public void setUpCamera(List<EntityWrapper> node, int scrollStatusX, int scrollStatusY) { camera = new Camera(currentStage.getStage(), level, node, scrollStatusX, scrollStatusY); }

  public Scene getTestScene() {
    return testScene;
  }

  public void setLevel(BorderPane levelBuilt) {
    this.level = levelBuilt;
  }

  public void updateCamera() {
    camera.update(overlay);
  }

  public void updateEntityRenders(List<EntityWrapper> currentEntityList, List<EntityWrapper> entitiesToDespawn){
    for(EntityWrapper targetEntity : currentEntityList){
      if(!entityGroup.getChildren().contains(targetEntity.getRender())){
        addEntity(targetEntity.getRender());
      }
    }
    for(EntityWrapper despawnedEntity : entitiesToDespawn){
      removeEntity(despawnedEntity.getRender());
    }
  }

  @Override
  public void removeEntity(Node node) {
    entityGroup.getChildren().remove(node);
  }

  @Override
  public void addEntity(Node node) {
    entityGroup.getChildren().add(node);
  }

  public void handlePressInput (KeyCode code) {
    if (code == KeyCode.ESCAPE && escCounter < 1) {
      pauseGame(); }
    else if (code == KeyCode.Q && escCounter == 1) {
      unPauseGame(); }
    else if (code == KeyCode.H) {
      pauseGame();
      goHome(code.getChar()); }
    else if(code == KeyCode.X) {
      saveGame = true; }
  }

  public boolean getSaveGame() {
    return saveGame;
  }

  public void handleMenuInput() throws Exception {
    resumeGame();
    saveGame();
    exitGame();
    editControls();
    rebootGame();
    editVolume();
  }

  private void editVolume() {
    currentStage.getAvManager().setMusicVolume(config.getMusicVolume());
    currentStage.getAvManager().setFXVolume(config.getFXVolume());
  }

  private void rebootGame() throws Exception {
    if (menu.getRebootPressed()){
      menu.setRebootOff();
      currentStage.reboot(); }
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
      menu.setExitOff(); }
  }

  private void saveGame() {
    if (menu.getSavePressed()) {
      saveGame=true;
      unPauseGame();
      menu.setSaveOff(); }
  }

  private void resumeGame() {
    if (menu.getResumePressed()) {
      unPauseGame();
      menu.setResumeOff(); }
  }

  private void goHome(String state){
    currentStage.updateCurrentScene(currentStage.getCurrentTitle(), currentStage.getCurrentScene());
    currentStage.updateCurrentScene(state, currentStage.getPastScene());
    currentStage.switchScenes(myResources.getString("GameSelect"));
  }

  public void endGame() {
    goHome(KeyCode.H.getChar());
  }

  public void pauseGame(){
    BoxBlur bb = new BoxBlur();
    entityGroup.setEffect(bb);
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
    entityGroup.setEffect(null);
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

  public Camera getCamera(){
    return camera;
  }
}
