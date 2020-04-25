package ooga.view.gui.userinterface;


import ooga.util.config.Parser;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class GameSelectParserTest extends Parser {
    private final String RESOURCES_PACKAGE = "resources.guiText";
    private static final String TXT_FILEPATH = "src/resources/";
    private final String DEFAULT_GAME_NAME = "GameNameMissing"; //filename
    private final String DEFAULT_GAME_PREVIEW_GIF = "defaultGif"; //filename

    private JSONObject jsonObject;

    @BeforeEach
    void setUp() {
        String gameName;
        String gameFile = "BadUnitTest" + "Select";
        setMyFileName(TXT_FILEPATH + "badunittest" + "/" + gameFile + ".json");
        jsonObject = (JSONObject) readJsonFile();
    }

    @Test
    void readGameName() {
        assertThrows(NullPointerException.class, () -> jsonObject.get("gameName").toString());
    }

    @Test
    void readGamePreviewGIF() {
        assertThrows(NullPointerException.class, () -> jsonObject.get("gamePreviewGIF").toString());
    }

    @Test
    void readSaveStatus() {
        assertThrows(NullPointerException.class, () -> jsonObject.get("savingEnabled").toString());
    }

    @Test
    void readButtonArrangement() {
        assertThrows(NullPointerException.class, () -> jsonObject.get("buttonArrangement").toString());
    }

    @Test
    void defaultGameName() {
        String parsedGame;
        try {
            parsedGame = jsonObject.get("gameName").toString();
        }
        catch (NullPointerException e) {
            parsedGame = DEFAULT_GAME_NAME;
        }
        assertEquals("GameNameMissing", parsedGame);
    }

    @Test
    void defaultGameGIF() {
        String parsedGameGIF;
        try {
            parsedGameGIF = jsonObject.get("gamePreviewGIF").toString();
        }
        catch (NullPointerException e) {
            parsedGameGIF = DEFAULT_GAME_PREVIEW_GIF;
        }
        assertEquals("defaultGif", parsedGameGIF);
    }

}