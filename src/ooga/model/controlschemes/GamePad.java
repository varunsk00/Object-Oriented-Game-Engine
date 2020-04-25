package ooga.model.controlschemes;

import ooga.model.actions.Action;
import ooga.util.ActionBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamePad extends ControlScheme {
    private Map<String, List<Action>> controllerBindings;

    public GamePad(List<ActionBundle> bindings) {
        super(bindings);
        controllerBindings = new HashMap<>();
        for(ActionBundle bundle : actionMap){
            controllerBindings.put(bundle.getId(), bundle.getActions());
        }
        currentAction = new ArrayList<>();
    }
    @Override
    public List<Action> getCurrentAction() {
        return currentAction;
    }

    @Override
    public void handleKeyInput(String key) {
        if(controllerBindings.containsKey(key) && !currentAction.containsAll(controllerBindings.get(key))) {
            currentAction.addAll(controllerBindings.get(key));
//      System.out.println(key);
        }
    }

    @Override
    public void handleKeyReleased(String key) {
        //System.out.println(key);
        if (controllerBindings.containsKey(key) && currentAction
                .containsAll(controllerBindings.get(key))) {
            System.out.println("BINDINGS");
            currentAction.removeAll(controllerBindings.get(key));
            //System.out.println(keyEvent.getCode().toString());
        }
    }
}
