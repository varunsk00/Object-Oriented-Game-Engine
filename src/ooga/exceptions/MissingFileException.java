package ooga.exceptions;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MissingFileException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;
    private String alertMessage;

    /**
     * Create an exception based on an issue in our code.
     */
    public MissingFileException(String message, Object ... values) {
        super(String.format(message, values));
        alertMessage = message + " not found in resource directory! \n" +
            "Check your Resource Files to make sure all image and sound files are present!";
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public MissingFileException(Throwable cause, String message, Object ... values) {
        alertMessage = message + " not found in resource directory! \n" +
                cause + " was caught.\n" +
                "Check your Resource Files to make sure all image and sound files are present!";
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public MissingFileException(Throwable cause) {
        super(cause);
        alertMessage = "Some image or audio resources missing from resources. \n" +
            "Check your Resource Files to make sure all image and sound files are present!";
    }

    public void displayAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FILE Missing Error");
        alert.setHeaderText(alertMessage);
        alert.show();
    }
}
