package ooga.view.gui.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ooga.exceptions.ParameterMissingException;
import ooga.view.application.games.Game;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class AudioVideoManagerTest {
    private final String RESOURCES_PACKAGE = "test/resources/sounds/";
    private final String MUSIC_PACKAGE = "resources.sounds.soundtrack";
    private final String MUSIC_EXTENSION = ".mp3";
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private File currentSong;
    private MediaPlayer currentSoundEffect;


    @Test
    void switchMusic() {
        String testSong = "TEXT_NOT_IN_PROPERTIES";
        try {
            currentSong = new File(RESOURCES_PACKAGE + myMusic.getString(testSong) + MUSIC_EXTENSION);
        }
        catch (MissingResourceException e) {
            currentSong = new File(RESOURCES_PACKAGE + "default_sound" + MUSIC_EXTENSION);
        }
        assertEquals("default_sound.mp3", currentSong.getName());
    }

    @Test
    void switchMusicException() {
        String testSong = "TEXT_NOT_IN_PROPERTIES";
        assertThrows(MissingResourceException.class, () ->  currentSoundEffect = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(testSong) + MUSIC_EXTENSION).toURI().toString())));
    }
}