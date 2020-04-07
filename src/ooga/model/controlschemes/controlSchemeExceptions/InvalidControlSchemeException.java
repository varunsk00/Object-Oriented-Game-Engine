package ooga.model.controlschemes.controlSchemeExceptions;

public class InvalidControlSchemeException extends RuntimeException{

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

  }



