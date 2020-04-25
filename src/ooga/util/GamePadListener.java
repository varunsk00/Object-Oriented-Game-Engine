package ooga.util;

import com.github.strikerx3.jxinput.*;
import com.github.strikerx3.jxinput.enums.XInputAxis;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;
import javafx.scene.control.Alert;

import java.util.ResourceBundle;

public class GamePadListener {
    private final String RESOURCES_PACKAGE = "resources.guiText";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private final String LEFT_THUMBSTICK_RIGHT = "LEFT_THUMBSTICK_RIGHT";
    private final String LEFT_THUMBSTICK_LEFT = "LEFT_THUMBSTICK_LEFT";
    private final String LEFT_THUMBSTICK_UP = "LEFT_THUMBSTICK_UP";
    private final String LEFT_THUMBSTICK_DOWN = "LEFT_THUMBSTICK_DOWN";
    private final String RIGHT_THUMBSTICK_RIGHT = "RIGHT_THUMBSTICK_RIGHT";
    private final String RIGHT_THUMBSTICK_LEFT = "RIGHT_THUMBSTICK_LEFT";
    private final String RIGHT_THUMBSTICK_UP = "RIGHT_THUMBSTICK_UP";
    private final String RIGHT_THUMBSTICK_DOWN = "RIGHT_THUMBSTICK_DOWN";
    private final String D_PAD_RIGHT = "D_PAD_RIGHT";
    private final String D_PAD_LEFT = "D_PAD_LEFT";
    private final String D_PAD_UP = "D_PAD_UP";
    private final String D_PAD_DOWN = "D_PAD_DOWN";
    private final String A_BUTTON = "A_BUTTON";
    private final String B_BUTTON = "B_BUTTON";
    private final String X_BUTTON = "X_BUTTON";
    private final String Y_BUTTON = "Y_BUTTON";
    private final int MAX_RUMBLE = 65535;
    private final int NO_RUMBLE = 0;
    private final double THUMBSTICK_THRESHHOLD = 0.3;
    private GamePadState myState;
    private boolean released=true;
    private boolean a_pressed = false;
    private boolean b_pressed = false;
    private boolean x_pressed = false;
    private boolean y_pressed = false;
    private boolean leftthumbstick_pressed_right = false;
    private boolean leftthumbstick_pressed_left = false;
    private boolean leftthumbstick_pressed_up = false;
    private boolean leftthumbstick_pressed_down = false;
    private boolean rightthumbstick_pressed_right = false;
    private boolean rightthumbstick_pressed_left = false;
    private boolean rightthumbstick_pressed_up = false;
    private boolean rightthumbstick_pressed_down = false;
    public GamePadListener() throws XInputNotLoadedException {
        XInputDeviceListener listener = new XInputDeviceListener() {
            @Override
            public void connected() {
                createConnectedStateAlert(true);
            }

            @Override
            public void disconnected() {
                createConnectedStateAlert(false);
            }

            @Override
            public void buttonChanged(XInputButton xInputButton, boolean b) {
                if (xInputButton == XInputButton.A) {
                }
            }
        };
        for (XInputDevice device : XInputDevice.getAllDevices()) {
            device.addListener(listener);
        }
    }
    public void update() throws XInputNotLoadedException {
        released=true;
        for (XInputDevice device : XInputDevice.getAllDevices()) {
            if (device.poll()) {
                XInputComponents components = device.getComponents();
                XInputButtons buttons = components.getButtons();
                XInputAxes axes = components.getAxes();
                if (buttons.a) {
                    GamePadState g = new GamePadState(A_BUTTON, false);
                    a_pressed = true;
                    sendInput(g);
                    device.setVibration(MAX_RUMBLE, MAX_RUMBLE);
                    break;
                }
                if (a_pressed) {
                    GamePadState g = new GamePadState(A_BUTTON, true);
                    sendInput(g);
                    device.setVibration(NO_RUMBLE, NO_RUMBLE);
                    a_pressed = false;
                    break;
                }
                if (buttons.b) {
                    GamePadState g = new GamePadState(B_BUTTON, false);
                    b_pressed = true;
                    sendInput(g);
                    device.setVibration(MAX_RUMBLE, MAX_RUMBLE);
                    break;
                }
                if (b_pressed) {
                    GamePadState g = new GamePadState(B_BUTTON, true);
                    sendInput(g);
                    device.setVibration(NO_RUMBLE, NO_RUMBLE);
                    b_pressed = false;
                    break;
                }
                if (buttons.x) {
                    GamePadState g = new GamePadState(X_BUTTON, false);
                    x_pressed = true;
                    sendInput(g);
                    device.setVibration(MAX_RUMBLE, MAX_RUMBLE);
                    break;
                }
                if (x_pressed) {
                    GamePadState g = new GamePadState(X_BUTTON, true);
                    sendInput(g);
                    device.setVibration(NO_RUMBLE, NO_RUMBLE);
                    x_pressed = false;
                    break;
                }
                if (buttons.y) {
                    GamePadState g = new GamePadState(Y_BUTTON, false);
                    y_pressed = true;
                    sendInput(g);
                    device.setVibration(MAX_RUMBLE, MAX_RUMBLE);
                    break;
                }
                if (y_pressed) {
                    GamePadState g = new GamePadState(B_BUTTON, true);
                    sendInput(g);
                    device.setVibration(NO_RUMBLE, NO_RUMBLE);
                    y_pressed = false;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) > THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, false);
                    sendInput(g);
                    leftthumbstick_pressed_right = true;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) < -THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_LEFT, false);
                    sendInput(g);
                    leftthumbstick_pressed_left = true;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_Y) > THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_UP, false);
                    sendInput(g);
                    leftthumbstick_pressed_up = true;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_Y) < -THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_DOWN, false);
                    sendInput(g);
                    leftthumbstick_pressed_down = true;
                    break;
                }
                if (axes.get(XInputAxis.RIGHT_THUMBSTICK_X) > THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(RIGHT_THUMBSTICK_RIGHT, false);
                    sendInput(g);
                    rightthumbstick_pressed_right = true;
                    break;
                }
                if (axes.get(XInputAxis.RIGHT_THUMBSTICK_X) < -THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(RIGHT_THUMBSTICK_LEFT, false);
                    sendInput(g);
                    rightthumbstick_pressed_left = true;
                    break;
                }
                if (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) > THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(RIGHT_THUMBSTICK_UP, false);
                    sendInput(g);
                    rightthumbstick_pressed_up = true;
                    break;
                }
                if (axes.get(XInputAxis.RIGHT_THUMBSTICK_Y) < -THUMBSTICK_THRESHHOLD) {
                    GamePadState g = new GamePadState(RIGHT_THUMBSTICK_DOWN, false);
                    sendInput(g);
                    rightthumbstick_pressed_down = true;
                    break;
                }
                if (leftthumbstick_pressed_right) {
                    GamePadState gamePadReleasedEvent = new GamePadState(LEFT_THUMBSTICK_RIGHT, true);
                    sendInput(gamePadReleasedEvent);
                    leftthumbstick_pressed_right = false;
                    break;
                }
                if (leftthumbstick_pressed_left) {
                    GamePadState gamePadReleasedEvent = new GamePadState(LEFT_THUMBSTICK_LEFT, true);
                    sendInput(gamePadReleasedEvent);
                    leftthumbstick_pressed_left = false;
                    break;
                }
                if (leftthumbstick_pressed_down) {
                    GamePadState gamePadReleasedEvent = new GamePadState(LEFT_THUMBSTICK_DOWN, true);
                    sendInput(gamePadReleasedEvent);
                    leftthumbstick_pressed_down = false;
                    break;
                }
                if (leftthumbstick_pressed_up) {
                    GamePadState gamePadReleasedEvent = new GamePadState(LEFT_THUMBSTICK_UP, true);
                    sendInput(gamePadReleasedEvent);
                    leftthumbstick_pressed_up = false;
                    break;
                }
            }
        }
    }
    private void createConnectedStateAlert(boolean isConnected) {
        Alert controllerPopup = new Alert(Alert.AlertType.CONFIRMATION);
        if (isConnected) {
            controllerPopup.setTitle(myResources.getString("Connected"));
            controllerPopup.setContentText(myResources.getString("ConnectedMessage"));
        }
        else {
            controllerPopup.setTitle(myResources.getString("Disconnected"));
            controllerPopup.setContentText(myResources.getString("DisconnectedMessage"));
        }
        controllerPopup.show();
    }
    public void sendInput(GamePadState g) {
            this.myState = g;
    }
    public GamePadState getState() {
        return myState;
    }

    public static class GamePadState {
        private String control;
        private boolean pressed;
        public GamePadState(String s, boolean b) {
            this.control = s;
            this.pressed = b;
        }
        public String getControl() {
            return this.control;
        }
        public boolean getPressed() {
            return this.pressed;
        }
    }

}
