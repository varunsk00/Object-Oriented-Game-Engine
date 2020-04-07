package ooga;
import javafx.embed.swing.JFXPanel;
import ooga.view.gui.startup.Boot;

import java.io.FileNotFoundException;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main {
    /**
     * Start of the program.
     */
  public static void main (String[] args) throws FileNotFoundException {
      JFXPanel fxPanel = new JFXPanel();
      Boot arcade = new Boot(args);
  }
}
