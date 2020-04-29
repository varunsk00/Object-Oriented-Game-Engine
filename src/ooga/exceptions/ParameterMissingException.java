package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ParameterMissingException extends DisplayExceptions {
    // for serialization
    private static final long serialVersionUID = 1L;
    private String alertMessage;
    private final String type = "Parameter missing from data file";


    /**
     * Create an exception based on an issue in our code.
     */
    public ParameterMissingException(String message, Object ... values) {
        super(String.format(message, values));
        alertMessage = message + " parameter not found in respective Game.json file! \n" +
            "NullPointerException" + " would have been caught.\n";
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ParameterMissingException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
        alertMessage = message + " parameter not found in respective Game.json file! \n" +
            cause + " was caught.\n";
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ParameterMissingException(Throwable cause) {
        super(cause);
        alertMessage = "";

    }

    @Override
    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(type);
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
