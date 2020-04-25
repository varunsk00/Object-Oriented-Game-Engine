package ooga.util.config;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class MissingFileException extends RuntimeException {
    // for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Create an exception based on an issue in our code.
     */
    public MissingFileException(String message, Object ... values) {
        super(String.format(message, values));
    }

    /**
     * Create an exception based on a caught exception with a different message.
     */
    public MissingFileException(Throwable cause, String message, Object ... values) {
        String errorMessage = message + " not found in resource directory! \n" +
                cause + " was caught.\n" +
                "Check your Resource Files to make sure all image and sound files are present!";
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FILE Missing Error");
        alert.setHeaderText(errorMessage);
        alert.show();
    }

    /**
     * Create an exception based on a caught exception, with no additional message.
     */
    public MissingFileException(Throwable cause) {
        super(cause);
    }

}
