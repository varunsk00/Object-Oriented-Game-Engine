package ooga.view.gui.managers;

import java.util.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.util.config.ParameterMissingException;
import ooga.view.application.games.Game;

import java.io.File;

public class AudioVideoManager {
    private final String RESOURCES_PACKAGE = "src/resources/sounds/";
    private final String MUSIC_PACKAGE = "resources.sounds.soundtrack";
    private final String MUSIC_EXTENSION = ".mp3";
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private Game currentGame;
    private MediaPlayer currentSong;
    private MediaPlayer currentSoundEffect;
    private boolean destroyed;
    private Map<String, Object> myInPlayGames;

    //FIXME: FIX THIS ATROCIOUS METHOD OF CLOSING AVMANAGER WHEN RESETTING

    public AudioVideoManager(){
        myInPlayGames = new HashMap<>();
    }

    public void close(){
        this.destroyed = true;
    }

    public void switchMusic(StageManager sm){
        if (currentSong != null) {
            currentSong.stop(); }
        try {
            this.currentSong = new MediaPlayer
                    (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sm.getCurrentTitle()) + MUSIC_EXTENSION).toURI().toString()));
        }
        catch (MissingResourceException e) {
            new ParameterMissingException(e, "myMusicPropertiesKey");
            this.currentSong = new MediaPlayer
                    (new Media(new File(RESOURCES_PACKAGE + "default_sound" + MUSIC_EXTENSION).toURI().toString()));
        }
        if(!destroyed) {
            playSong(currentSong); }
    }

    public void switchGame(StageManager sm, String gameName, boolean loadGame) throws Exception {
        if (loadGame || !myInPlayGames.containsKey(gameName)) {
            currentGame = new Game(sm);
            currentGame.loadGame(gameName, loadGame);
            myInPlayGames.put(gameName, currentGame); }
        else {
            sm.switchScenes(gameName); }
    }

    public String playerParser(String id){ //FIXME: MAGIC NUMBERS? IT'S PARSING P2_, so idk
        if(id.indexOf('_') == 2){
            return id.substring(3); }
        return id;
    }

    public void playSoundEffect(String sound){
        if (!myMusic.getString(sound).equals("NOSOUND")) {
            this.currentSoundEffect = new MediaPlayer
                    (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sound) + ".mp3").toURI().toString()));
            currentSoundEffect.seek(Duration.ZERO);
            currentSoundEffect.setVolume(0.1);
            currentSoundEffect.play(); }
    }

    private void playSong(MediaPlayer song){
        song.seek(Duration.ZERO);
        song.setCycleCount(MediaPlayer.INDEFINITE);
        song.setVolume(0.1);
        song.play();
    }
}
