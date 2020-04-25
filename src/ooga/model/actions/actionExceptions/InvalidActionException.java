package ooga.model.actions.actionExceptions;

public class InvalidActionException extends RuntimeException {
  public static final String CLASS_NOT_FOUND = "Control Scheme not valid";
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


}
