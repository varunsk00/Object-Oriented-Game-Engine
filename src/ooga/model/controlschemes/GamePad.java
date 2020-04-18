package ooga.model.controlschemes;

import com.github.strikerx3.jxinput.*;
import com.github.strikerx3.jxinput.enums.XInputAxis;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;

public class GamePad {
    private String LEFT_THUMBSTICK_RIGHT = "LEFT_THUMBSTICK_RIGHT";
    private String LEFT_THUMBSTICK_LEFT = "LEFT_THUMBSTICK_LEFT";
    private String RIGHT_THUMBSTICK = "RIGHT_THUMBSTICK";
    private String A_BUTTON = "A_BUTTON";
    private String B_BUTTON;
    private String X_BUTTON;
    private String Y_BUTTON;
    private GamePadState myState;
    private boolean released=true;
    public GamePad() throws XInputNotLoadedException {
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
                if (buttons.a) {
                    GamePadState g = new GamePadState(A_BUTTON, false);
                    sendInput(g);
                    break;
                }
                else if (!buttons.a) {
                    GamePadState g = new GamePadState(A_BUTTON, true);
                    sendInput(g);
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) > .3) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, false);
                    sendInput(g);
                    break;
                }
                else if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) <= .3) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_RIGHT, true);
                    sendInput(g);
                }
                if (axes.get(XInputAxis.LEFT_THUMBSTICK_X) < 0) {
                    GamePadState g = new GamePadState(LEFT_THUMBSTICK_LEFT, false);
                    sendInput(g);
                    break;
                }

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
