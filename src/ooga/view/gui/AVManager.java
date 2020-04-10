package ooga.view.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.view.application.games.Game;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

public class AVManager {
    private static final String GAME_PACKAGE = Game.class.getPackageName();
    private final String RESOURCES_PACKAGE = "src/ooga/view/gui/resources/";
    private final String RESOURCES_PACKAGE1 = this.getClass().getPackageName() + ".resources.";
    private final String MUSIC_PACKAGE = RESOURCES_PACKAGE1 + "soundtrack";
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private MediaPlayer currentSong;
    private MediaPlayer oldSong;
    private MediaPlayer currentSoundEffect;

    public AVManager(){
    }

    public void switchMusic(StageManager sm){
        if (currentSong != null) {
            currentSong.stop();
        }
        this.currentSong = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sm.getCurrentTitle()) + ".mp3").toURI().toString()));
        playSong(currentSong);
    }

    public void switchStage(StageManager sm, String gameName) throws Exception {
        Class<?> c = Class.forName(GAME_PACKAGE + "." + gameName);
        Constructor<?> cons = c.getDeclaredConstructor(StageManager.class);
        Object launchGame = cons.newInstance(sm);
    }

    public void playSoundEffect(String sound){
        this.currentSoundEffect = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sound) + ".mp3").toURI().toString()));
        currentSoundEffect.seek(Duration.ZERO);
        currentSoundEffect.play();
    }

    private void playSong(MediaPlayer song){
        song.seek(Duration.ZERO);
        song.setCycleCount(MediaPlayer.INDEFINITE);
        song.play();
    }
}
