package ooga.view.gui.userinterface;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import ooga.exceptions.ParameterInvalidException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class GamePreviewTest {
    private ImagePattern gamePreviewGif;
    private final String RESOURCES_PATH = "test/resources/";
    private final String DEFAULT_GIF = "defaultGIF.gif";

    void setUp() throws FileNotFoundException {
        try {
            gamePreviewGif = new ImagePattern((new Image(new FileInputStream(RESOURCES_PATH + "failString"))));
        }
        catch (FileNotFoundException e) {
            this.gamePreviewGif = new ImagePattern((new Image(new FileInputStream(RESOURCES_PATH + DEFAULT_GIF))));
        }
    }
    @Test
    void testBadFileName()  {
        assertThrows(FileNotFoundException.class, () -> setUp());
    }

}