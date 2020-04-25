package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ParameterMissingException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;
    private String alertMessage;


    /**
     * Create an exception based on an issue in our code.
     */
    public ParameterMissingException(String message, Object ... values) {
        alertMessage = message + " parameter not found in respective GameSelect.json file! \n" +
                "NullPointerException" + " would have been caught.\n" +
                "Loaded default " + message + " instead.";

    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ParameterMissingException(Throwable cause, String message, Object ... values) {
        alertMessage = message + " parameter not found in respective GameSelect.json file! \n" +
                cause + " was caught.\n" +
                "Loaded default " + message + " instead.";
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ParameterMissingException(Throwable cause) {
        super(cause);
        alertMessage = "";
    }

    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Parameter Missing Error");
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
