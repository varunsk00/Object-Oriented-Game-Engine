package ooga.view.application.menu;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.controller.EntityWrapper;
import ooga.util.EntityJSONParser;

import java.util.*;

public class ConfigurationMenu extends HBox{
    private final String RESOURCES_PACKAGE = "resources.guiText";
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
    private Button exitButton;
    private boolean exitPressed;
    private VolumeSliders sliders;
    private List<TextField> input = new ArrayList<>();
    private VBox myActions = new VBox();
    private Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION);

    public ConfigurationMenu(List<EntityWrapper> playerList){
        this.setId("configMenu");
        populateIDs(playerList);
        createParsers();
        this.playerNum = playerList.size();
        this.actionMap = parseAction2KeyMap();
        this.sliders = new VolumeSliders(2);
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
        return sliders.getSliders()[0].getValue();
    }

    public double getFXVolume(){
        return sliders.getSliders()[1].getValue();
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
        configTitle.getChildren().addAll(configMessage, this.exitButton, this.sliders);
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
                playerBindings.get(0).add(entryList.get(mapIndex).getValue()); } }
        drawActionsOnScreen();
        for(int playerIndex = 0; playerIndex < playerNum; playerIndex++){
            setVBoxes(playerIndex);
            setControlSchemeDropDowns(playerIndex);
            setTextFields(playerIndex); }
    }

    private void updateControls(int playerIndex, String oldBind, String newBind){
        for (Map.Entry<String,String> entry : actionMap.entries()){
            if(entry.getValue().equals(oldBind)) {
                parsers.get(playerIndex).updateControls(entry.getKey(), newBind, true); } }
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
            bindingDisplay.get(index).getChildren().add(bind); }
    }

    private void drawActionsOnScreen(){
        for(String s: actions){
            if(myResources.containsKey(s)){
                Text action = new Text(myResources.getString(s));
                action.setId("actionList");
                myActions.getChildren().add(action); } }
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
}
