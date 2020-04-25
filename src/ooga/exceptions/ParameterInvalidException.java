package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ParameterInvalidException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;
    private String alertMessage;


    /**
     * Create an exception based on an issue in our code.
     */
    public ParameterInvalidException(String message, Object ... values) {
        super(String.format(message, values));
        alertMessage = "The value for the " + message + " parameter was invalid! \n" +
            "Check your game JSON files. \n" +
            "Loaded default " + message + " instead.";
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ParameterInvalidException(Throwable cause, String message, Object ... values) {
        alertMessage = "The value for the " + message + " parameter was invalid! \n" +
                "Check your game JSON files. \n" +
                cause + " was caught.\n" +
                "Loaded default " + message + " instead.";
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ParameterInvalidException(Throwable cause) {
        super(cause);
        alertMessage = alertMessage = "The value for a parameter was invalid! \n" +
            "Check your game JSON files. \n" +
            cause + " was caught.\n" +
            "Loaded default value instead.";;
    }

    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parameter Invalid Error");
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
