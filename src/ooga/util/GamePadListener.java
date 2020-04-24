package ooga.util;

import com.github.strikerx3.jxinput.*;
import com.github.strikerx3.jxinput.enums.XInputAxis;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;

public class GamePadListener {
    private String LEFT_THUMBSTICK_RIGHT = "LEFT_THUMBSTICK_RIGHT";
    private String LEFT_THUMBSTICK_LEFT = "LEFT_THUMBSTICK_LEFT";
    private String RIGHT_THUMBSTICK = "RIGHT_THUMBSTICK";
    private String A_BUTTON = "A_BUTTON";
    private String B_BUTTON = "B_BUTTON";
    private String X_BUTTON;
    private String Y_BUTTON;
    private GamePadState myState;
    private boolean released=true;
    private boolean a_pressed = false;
    private boolean leftthumbstick_pressed_right = false;
    private boolean leftthumbstick_pressed_left = false;
    private boolean rightthumbstick_pressed = false;
    private boolean b_pressed = false;
    public GamePadListener() throws XInputNotLoadedException {
        XInputDeviceListener listener = new XInputDeviceListener() {
            @Override
            public void connected() {

            }

            @Override
            public void disconnected() {

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
                XInputComponentsDelta d = device.getDelta();
                XInputAxesDelta a = d.getAxes();
                XInputComponents components = device.getComponents();
                XInputButtons buttons = components.getButtons();
                XInputAxes axes = components.getAxes();
                //System.out.println(axes.get(XInputAxis.LEFT_THUMBSTICK_X));
                //System.out.println(buttons.a);
                System.out.println(a_pressed);
                if (buttons.a) {
                    GamePadState g = new GamePadState(A_BUTTON, false);
                    a_pressed = true;
                    sendInput(g);
                    break;
                }
                if (a_pressed) {
                    GamePadState g = new GamePadState(A_BUTTON, true);
                    sendInput(g);
                    a_pressed = false;
                    break;
                }
                if (buttons.b) {
                    GamePadState g = new GamePadState(B_BUTTON, false);
                    b_pressed = true;
                    sendInput(g);
                    device.setVibration(65535, 65535);
                    break;
                }
                if (b_pressed) {
                    GamePadState g = new GamePadState(B_BUTTON, true);
                    sendInput(g);
                    device.setVibration(0, 0);
                    b_pressed = false;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) > .3) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, false);
                    sendInput(g);
                    leftthumbstick_pressed_right = true;
                    break;
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) < -.3) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_LEFT, false);
                    sendInput(g);
                    leftthumbstick_pressed_left = true;
                    break;
                }
                if (leftthumbstick_pressed_right) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, true);
                    sendInput(g);
                    leftthumbstick_pressed_right = false;
                    break;
                }
                if (leftthumbstick_pressed_left) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_LEFT, true);
                    sendInput(g);
                    leftthumbstick_pressed_left = false;
                    break;
                }


//                else if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) > -.3 || axes.get(XInputAxis.LEFT_THUMBSTICK_X) < .3) {
//                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, true);
//                    sendInput(g);
//                }

                // use device.getPlayerNum() if you need to know which player this device is associated with
            } else {
                // controller is not connected
                // in this situation games typically ask the player to reconnect the controller and pause if possible
            }
        }
    }
    public void sendInput(GamePadState g) {
           // System.out.println(g.getControl() + g.getPressed());
            this.myState = g;
    }
    public GamePadState getState() {
        return myState;
    }
    public boolean getReleased() {
        return this.released;
    }

    public class GamePadState {
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
