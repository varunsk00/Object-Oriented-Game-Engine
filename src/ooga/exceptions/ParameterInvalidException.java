package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ParameterInvalidException extends DisplayExceptions {

    // for serialization
    private static final long serialVersionUID = 1L;
    public static final String VALUE = "The value for the ";
    public static final String PARAMETER_WAS_INVALID = " parameter was invalid! \n";
    public static final String CHECK_JSON = "Check your game JSON files. \n";
    public static final String LOADED_DEFAULT = "Loaded default ";
    private String alertMessage;
    private final String type = "Parameter Invalid in Data File";


    /**
     * Create an exception based on an issue in our code.
     */
    public ParameterInvalidException(String message, Object ... values) {
        super(String.format(message, values));
        alertMessage = VALUE + message + PARAMETER_WAS_INVALID +
            CHECK_JSON;
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public ParameterInvalidException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
        alertMessage = VALUE + message + PARAMETER_WAS_INVALID +
            CHECK_JSON +
            cause + " was caught.\n";
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public ParameterInvalidException(Throwable cause) {
        super(cause);
        alertMessage = alertMessage = "The value for a parameter was invalid! \n" +
            CHECK_JSON +
            cause + " was caught.\n";
    }

    @Override
    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(type);
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
