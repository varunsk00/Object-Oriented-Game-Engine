package ooga.view.gui.managers;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.view.application.games.Game;
import ooga.view.gui.userinterface.PlayerSelect;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ResourceBundle;

public class AudioVideoManager {
    private static final String GAME_PACKAGE = Game.class.getPackageName();
    private final String RESOURCES_PACKAGE = "src/ooga/view/gui/resources/";
    private final String RESOURCES_PACKAGE1 = "ooga.view.gui.resources.";
    private final String MUSIC_PACKAGE = RESOURCES_PACKAGE1 + "soundtrack";
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private Game currentGame;
    private PlayerSelect myPlayerSelect;
    private MediaPlayer currentSong;
    private MediaPlayer currentSoundEffect;
    private Map<String, Object> myInPlayGames;

    public AudioVideoManager(){
        myInPlayGames = new HashMap<String, Object>();
    }

    public void switchMusic(StageManager sm){
        if (currentSong != null) {
            currentSong.stop();
        }
        this.currentSong = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sm.getCurrentTitle()) + ".mp3").toURI().toString()));
        playSong(currentSong);
    }

    public void switchGame(StageManager sm, String gameName) throws Exception {
        if (!myInPlayGames.containsKey(gameName)) {
            currentGame = new Game(sm);
            currentGame.loadGame(gameName);
            myInPlayGames.put(gameName, currentGame);
        } else {
            sm.switchScenes(gameName);
        }
    }

    public String playerParser(String id){
        if(id.indexOf('_') == 2){
            return id.substring(3);
        }
        return id;
    }

    public void playSoundEffect(String sound){
        this.currentSoundEffect = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sound) + ".mp3").toURI().toString()));
        currentSoundEffect.seek(Duration.ZERO);
        //currentSoundEffect.setVolume(0.0);
        currentSoundEffect.play();
        currentSoundEffect.setVolume(0);
    }

    private void playSong(MediaPlayer song){
        song.seek(Duration.ZERO);
        song.setCycleCount(MediaPlayer.INDEFINITE);
        //song.setVolume(0.0);
        song.play();
        song.setVolume(0);
    }
}
