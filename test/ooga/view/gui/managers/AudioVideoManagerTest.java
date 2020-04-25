package ooga.view.gui.managers;

import javafx.scene.media.MediaPlayer;
import ooga.view.application.games.Game;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoManagerTest {
    private final String RESOURCES_PACKAGE = "src/resources/sounds/";
    private final String MUSIC_PACKAGE = "resources.sounds.soundtrack";
    private final String MUSIC_EXTENSION = ".mp3";
    private final double DEFAULT_VOLUME = 0.1;
    private double songVolume = DEFAULT_VOLUME;
    private double soundEffectVolume = DEFAULT_VOLUME;
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private Game currentGame;
    private MediaPlayer currentSong;
    private MediaPlayer currentSoundEffect;
    private boolean destroyed;
    private Map<String, Object> myInPlayGames;

    @BeforeEach
    void setUp() {
        String testSong;

    }

    @Test
    void switchMusic() {
    }
}