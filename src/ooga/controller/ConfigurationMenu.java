package ooga.controller;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.util.EntityJSONParser;

import java.util.*;

public class ConfigurationMenu extends HBox{
    private static final int MIN_MUSIC = 0;
    private static final int DEFAULT_MUSIC = 2;
    private static final int MAX_MUSIC = 4;
    private final String RESOURCES_PACKAGE = "resources.params";
    private final String PARAM_START = "param\":\"";
    private final String PARAM_END = "\",\"a";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private int playerNum;
    private List<String> ids = new ArrayList<>();
    private Set<String> actions = new HashSet<>();
    private List<EntityJSONParser> parsers = new ArrayList<>();
    private List<List<String>> playerBindings = new ArrayList<>(
            Arrays.asList(new ArrayList<>(), new ArrayList<>()));
    private List<VBox> bindingDisplay = new ArrayList<>(
            Arrays.asList(new VBox(), new VBox()));
    private List<ComboBox> dropDowns = new ArrayList<>(
            Arrays.asList(new ComboBox(), new ComboBox()));
    private Multimap<String,String> actionMap;
    private Slider[] volumeSliders = new Slider[2];
    private Button exitButton;
    private boolean exitPressed;
    private List<TextField> input = new ArrayList<>();
    private VBox myActions = new VBox();
    private Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public ConfigurationMenu(List<EntityWrapper> playerList){
        this.setId("configMenu");
        populateIDs(playerList);
        createParsers();
        this.playerNum = playerList.size();
        this.actionMap = parseAction2KeyMap();
        loadTitle();
        loadDefaultControls();
        for(VBox box: bindingDisplay){
            for(Node field: box.getChildren()){
                if(box.getChildren().indexOf(field) > 0) {
                    TextField updatedField = (TextField) field;
                    String oldBind = updatedField.getText();
                    updatedField.setOnAction(e -> updateControls(bindingDisplay.indexOf(box), oldBind, updatedField.getText())); } } }
        for(ComboBox cb: dropDowns){
            cb.setOnAction(e-> updateControlType(dropDowns.indexOf(cb), (String) cb.getValue())); }
    }

    public boolean getExitPressed(){
        return this.exitPressed;
    }

    public void setExitOff(){
        this.exitPressed = false;
    }

    public double getMusicVolume(){
        return volumeSliders[0].getValue();
    }

    public double getFXVolume(){
        return volumeSliders[1].getValue();
    }

    private void populateIDs(List<EntityWrapper> characters){
        for(EntityWrapper entity: characters){
            this.ids.add(entity.getEntityID()); }
    }

    private void createParsers(){
        for(String s: this.ids){
            String[] gameAndName = s.split("\\.");
            EntityJSONParser entityParser = new EntityJSONParser(gameAndName[0], gameAndName[1]);
            this.parsers.add(entityParser); }
    }

    private void loadTitle(){
        VBox configTitle = new VBox();
        Text configMessage = new Text(myResources.getString("MenuTitle"));
        this.exitButton = makeButton("Exit", e-> exitPressed = true);
        VBox sliders = renderSliders();
        configTitle.getChildren().addAll(configMessage, exitButton, sliders);
        configTitle.setId("configTitle");
        getChildren().add(configTitle);
    }

    private void loadDefaultControls(){
        List<Map.Entry<String, String>> entryList = new ArrayList<>(actionMap.entries());
        for(int mapIndex = 0; mapIndex < entryList.size(); mapIndex++){
            actions.add(entryList.get(mapIndex).getKey());
            if(playerNum > 1){
                generateMultiplayerBindings(entryList, mapIndex); }
            else{
                playerBindings.get(0).add(entryList.get(mapIndex).getValue()); }
        }
        drawActionsOnScreen();
        for(int playerIndex = 0; playerIndex < playerNum; playerIndex++){
            setVBoxes(playerIndex);
            setControlSchemeDropDowns(playerIndex);
            setTextFields(playerIndex); }
    }

    private void updateControls(int playerIndex, String oldBind, String newBind){
        for (Map.Entry<String,String> entry : actionMap.entries()){
            if(entry.getValue().equals(oldBind)) {
                parsers.get(playerIndex).updateControls(entry.getKey(), newBind, true);
            }
        }
        updateAlert.setContentText(myResources.getString("UpdateBind"));
        updateAlert.show();
    }

    private void updateControlType(int playerIndex, String selectedType){
        parsers.get(playerIndex).updateControlScheme(selectedType);
        updateAlert.setContentText(myResources.getString("UpdateControlType"));
        updateAlert.show();
    }

    private void generateMultiplayerBindings(List<Map.Entry<String, String>> entryList, int index) { //FIXME: figure out algorithm for >2 players
        if(index%2==0){ //evenIndex
            playerBindings.get(0).add(entryList.get(index).getValue()); }//Single-player
        else { //2-Player
            playerBindings.get(1).add(entryList.get(index).getValue()); }
    }

    private void setTextFields(int index) {
        for(String s : playerBindings.get(index)){
            TextField bind = new TextField(s);
            input.add(bind);
            bindingDisplay.get(index).getChildren().add(bind);
        }
    }

    private void drawActionsOnScreen(){
        for(String s: actions){
            if(myResources.containsKey(s)){
                Text action = new Text(myResources.getString(s));
                action.setId("actionList");
                myActions.getChildren().add(action);
            }
        }
        myActions.setId("actionVBox");
        getChildren().add(myActions);
    }

    private void setVBoxes(int index){
        bindingDisplay.get(index).setId("player" + (index+1) + "VBox");
        bindingDisplay.get(index).getChildren().add(dropDowns.get(index));
        getChildren().add(bindingDisplay.get(index));
    }

    private void setControlSchemeDropDowns(int index){
        dropDowns.get(index).getItems().add(myResources.getString("Keyboard"));
        dropDowns.get(index).getItems().add(myResources.getString("GamePad"));
        dropDowns.get(index).setValue(myResources.getString("Player") + (index+1));
    }

    private Multimap parseAction2KeyMap(){
        List<String> actionList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        Multimap<String,String> action2Key = ArrayListMultimap.create();
        for(EntityJSONParser p: parsers){
            List<String> controls = p.updateControls("","", false);
            for(int i = 0; i < controls.size(); i++) {
                if (i % 2 == 0) {
                    String param = findJSONParam(controls.get(i));
                    actionList.add(param); }
                if (i % 2 == 1) {
                    keyList.add(controls.get(i)); } } }
        for (int i=0; i<actionList.size(); i++) {
            action2Key.put(actionList.get(i), keyList.get(i)); }
        return action2Key;
    }

    private String findJSONParam(String controlString){
        String param = controlString.substring(controlString.lastIndexOf(PARAM_START), controlString.lastIndexOf(PARAM_END));
        return param.substring(param.lastIndexOf("\"")+1);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(key);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private VBox renderSliders() {
        VBox sliders = new VBox();
        HBox allLabels = new HBox();
        addLabel("SongSlider", allLabels);
        addLabel("EffectsSlider", allLabels);
        HBox allSliders = new HBox();
        for(int index = 0; index < volumeSliders.length; index++){
            volumeSliders[index] = addAndReturnSlider(MIN_MUSIC, MAX_MUSIC, DEFAULT_MUSIC, allSliders);
        }
        sliders.getChildren().add(allLabels);
        sliders.getChildren().add(allSliders);
        return sliders;
    }

    private void addLabel(String key, HBox text) {
        Label tempLabel = new Label(myResources.getString(key));
        tempLabel.setMaxWidth(Double.MAX_VALUE);
        tempLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(tempLabel, Priority.ALWAYS);
        text.getChildren().add(tempLabel);
    }

    private Slider addAndReturnSlider(int min, int max, int def, HBox sliders) {
        Slider tempSlider = new Slider(min, max, def);
        HBox.setHgrow(tempSlider, Priority.ALWAYS);
        setSliderTicks(tempSlider);
        sliders.getChildren().add(tempSlider);
        return tempSlider;
    }

    private void setSliderTicks(Slider slider) {
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }
}
