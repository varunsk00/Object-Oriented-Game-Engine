package ooga.controller;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.util.EntityJSONParser;
import ooga.util.GameParser;
import org.json.simple.JSONObject;

import java.util.*;

public class ControlSchemeSwitcher extends VBox {
    private final String RESOURCES_PACKAGE = "resources.params";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private List<String> ids = new ArrayList<>();
    private List<EntityJSONParser> parsers = new ArrayList<>();
    private Map<String,String> actionMap;
    private List<TextField> input = new ArrayList<>();
    private VBox myActions = new VBox();
    private VBox myKeys = new VBox();
    private JSONObject characterData;

    public ControlSchemeSwitcher(GameParser gp){
        populateIDs(gp.getPlayerList());
        createParsers();
        //updateControls(KeyCode.K);
        Text config = new Text("Configuration Menu\n");
        getChildren().add(config);
        this.actionMap = parseAction2KeyMap();
        loadDefaultControls();
        for(TextField field: input){
            String oldBind = field.getText();
            field.setOnAction(e-> updateControls(oldBind, field.getText()));
        }
        myActions.setSpacing(30);
        getChildren().add(myActions);
    }

    public void updateControls(String oldBind, String newBind){
        for(EntityJSONParser p: parsers){
            for (Map.Entry<String,String> entry : actionMap.entrySet()){
                if(entry.getValue().equals(oldBind)) {
                    p.updateControls(entry.getKey(), newBind, true);
                }
            }
        }
        Text restartMsg = new Text("Controls Successfully Updated! Reboot System for Changes to Take Effect.");
        getChildren().add(restartMsg);
    }

    private void loadDefaultControls(){
        for (Map.Entry<String,String> entry : actionMap.entrySet()){
            if(myResources.containsKey(entry.getKey())) {
                Text action = new Text(myResources.getString(entry.getKey()));
                TextField bind = new TextField(entry.getValue());
                bind.setAlignment(Pos.CENTER);
                bind.setMaxWidth(75);//FIXME: MAGIC NUMBER MOVE TO CSS FILE
                this.input.add(bind);
                getChildren().addAll(action, bind);
            }
        }
    }

    private Map parseAction2KeyMap(){
        List<String> actionList = new ArrayList<>();
        List<String> keyList = new ArrayList<>();
        Map<String,String> action2Key = new HashMap<>();
        for(EntityJSONParser p: parsers){
            List<String> controls = p.updateControls("","", false);
            for(int i = 0; i < controls.size(); i++) {
                if (i % 2 == 0) {
                    String param = findParam(controls.get(i));
                    actionList.add(param); }
                if (i % 2 == 1) {
                    keyList.add(controls.get(i)); } }
            for (int i=0; i<actionList.size(); i++) {
                action2Key.put(actionList.get(i), keyList.get(i)); } }
        return action2Key;
    }

    private String findParam(String controlString){
        String param = controlString.substring(controlString.lastIndexOf("param\":\""), controlString.lastIndexOf("\","));
        return param.substring(param.lastIndexOf("\"")+1);
    }

    private void populateIDs(List<EntityWrapper> characters){
        for(EntityWrapper entity: characters){
            this.ids.add(entity.getEntityID());
        }
    }

    private void createParsers(){
        for(String s: this.ids){
            String[] gameAndName = s.split("\\.");
            EntityJSONParser entityParser = new EntityJSONParser(gameAndName[0], gameAndName[1]);
            this.parsers.add(entityParser);
        }
    }
}
