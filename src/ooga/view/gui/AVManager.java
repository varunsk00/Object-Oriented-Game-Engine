package ooga.view.gui;

import javafx.stage.Stage;
import ooga.view.application.Game;

import java.lang.reflect.Constructor;

public class AVManager {
    private static final String PACKAGE = Game.class.getPackageName();
    public AVManager(){

    }
    public void switchStage(Stage st, String gameName) throws Exception {
        Class<?> c = Class.forName(PACKAGE + "." + gameName);
        Constructor<?> cons = c.getDeclaredConstructor(Stage.class);
        Object launchGame = cons.newInstance(st);
    }
}
