package ooga.model.actions.actionExceptions;

import javafx.scene.control.Alert;
import ooga.exceptions.DisplayExceptions;

public class InvalidActionException extends DisplayExceptions {
  public static final String type = "Action not valid in data file or properties file";
  /**
   * Create an Exception based on an issue during parsing
   *
   * @param message a String representing the message to be displayed, including %d, %f, or %s for customizable formatted items
   * @param values additional arguments representing items to fill the String.
   */
  public InvalidActionException(String message, Object... values) {
    super(String.format(message, values));
  }


  /**
   * Create an Exception based on a caught exception with a different message.
   *
   * @param cause the caught Exception
   * @param message a String representing the message to be displayed, including %d, %f, or %s for customizable formatted items
   * @param values additional arguments representing items to fill the String.
   */
  public InvalidActionException(Throwable cause, String message, Object... values) {
    super(String.format(message, values), cause);

  }

  /**
   * Create an exception based on a caught exception, with no additional message.
   *
   * @param cause the caught Exception
   */
  public InvalidActionException(Throwable cause) {
    super(cause);

  }

  @Override
  public void displayAlert(){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(type);
    alert.show();
  }


}
