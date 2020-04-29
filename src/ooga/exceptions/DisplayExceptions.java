package ooga.exceptions;

import javafx.scene.control.Alert;

public abstract class DisplayExceptions extends RuntimeException {

  /**
   * Create an exception based on an issue in our code.
   */
  public DisplayExceptions(String message, Object ... values) {
    super(String.format(message, values));

  }

  /**
   * Create an exception based on a caught exception with a different message.
   */
  public DisplayExceptions(Throwable cause, String message, Object ... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create an exception based on a caught exception, with no additional message.
   */
  public DisplayExceptions(Throwable cause) {
    super(cause);
  }


  public abstract void displayAlert();

}
