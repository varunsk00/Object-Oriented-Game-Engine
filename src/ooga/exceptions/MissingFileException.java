package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MissingFileException extends DisplayExceptions {

    // for serialization
    private static final long serialVersionUID = 1L;
    public static final String NOT_FOUND_IN_RESOURCE_DIRECTORY = " not found in resource directory or a resource file parameter is missing! \n";
    public static final String CHECK_RESOURCES = "Check your Resource Files to make sure all image and sound files are present!";
    public static final String type = "File Missing Error";
    private String alertMessage;

    /**
     * Create an exception based on an issue in our code.
     */
    public MissingFileException(String message, Object ... values) {
        super(String.format(message, values));
        alertMessage = message + NOT_FOUND_IN_RESOURCE_DIRECTORY +
            CHECK_RESOURCES;
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public MissingFileException(Throwable cause, String message, Object ... values) {
        super(String.format(message, values), cause);
        alertMessage = message + NOT_FOUND_IN_RESOURCE_DIRECTORY +
            cause + " was caught.\n" +
            CHECK_RESOURCES;
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public MissingFileException(Throwable cause) {
        super(cause);
        alertMessage = "Some image or audio resources missing from resources. \n" +
            CHECK_RESOURCES;
    }

    @Override
    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(type);
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
