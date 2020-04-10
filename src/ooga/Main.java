package ooga;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import ooga.view.gui.startup.Driver;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
    /**
     * Start of the program.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        JFXPanel fxPanel = new JFXPanel();
        Driver programDriver = new Driver(primaryStage);
        programDriver.start();
    }
    public static void main (String[] args) {
        launch(args);
    }
}
