package ooga.model.controlschemes.controlSchemeExceptions;

import javafx.scene.control.Alert;
import ooga.exceptions.DisplayExceptions;

public class InvalidControlSchemeException extends DisplayExceptions {
  private final String type = "Invalid control scheme used in data file.";

  /**
   * Create an Exception based on an issue during parsing
   *
   * @param message a String representing the message to be displayed, including %d, %f, or %s for customizable formatted items
   * @param values additional arguments representing items to fill the String.
   */
  public InvalidControlSchemeException(String message, Object... values) {
    super(String.format(message, values));
  }

  /**
   * Create an Exception based on a caught exception with a different message.
   *
   * @param cause the caught Exception
   * @param message a String representing the message to be displayed, including %d, %f, or %s for customizable formatted items
   * @param values additional arguments representing items to fill the String.
   */
  public InvalidControlSchemeException(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create an exception based on a caught exception, with no additional message.
   *
   * @param cause the caught Exception
   */
  public InvalidControlSchemeException(Throwable cause) {
    super(cause);

  }

  @Override
  public void displayAlert(){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(type);
    alert.show();
  }

}



