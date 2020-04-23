package ooga.view.gui.managers;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ooga.view.application.games.Game;
import ooga.view.gui.userinterface.TitleScreen;

import java.io.File;
import java.util.ResourceBundle;

public class AudioVideoManager {
    private static final String GAME_PACKAGE = Game.class.getPackageName();
    private final String RESOURCES_PACKAGE = "src/ooga/view/gui/resources/";
    private final String RESOURCES_PACKAGE1 = "ooga.view.gui.resources.";
    private final String MUSIC_PACKAGE = RESOURCES_PACKAGE1 + "soundtrack";
    private ResourceBundle myMusic = ResourceBundle.getBundle(MUSIC_PACKAGE);
    private Game currentGame;
    private MediaPlayer currentSong;
    private MediaPlayer currentSoundEffect;
    private boolean destroyed;
    private Map<String, Object> myInPlayGames;
    //TODO: FIX THIS DUMB IMPLEMENTATION OF DISABLING THE AV MANAGER FOR THE GLOBAL RESET

    public AudioVideoManager(){
        myInPlayGames = new HashMap<String, Object>();
    }

    public void close(){
        this.destroyed = true;
    }

    public void switchMusic(StageManager sm){
        if (currentSong != null) {
            currentSong.stop();
        }
        this.currentSong = new MediaPlayer
                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sm.getCurrentTitle()) + ".mp3").toURI().toString()));
        if(!destroyed) {
            playSong(currentSong);
        }
    }

    public void switchGame(StageManager sm, String gameName, boolean loadGame) throws Exception {

        if (loadGame || !myInPlayGames.containsKey(gameName)) {
            System.out.println("BOOLEAN switch game:" + loadGame);

            currentGame = new Game(sm);
            currentGame.loadGame(gameName, loadGame);
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
//        this.currentSoundEffect = new MediaPlayer
//                (new Media(new File(RESOURCES_PACKAGE + myMusic.getString(sound) + ".mp3").toURI().toString()));
//        currentSoundEffect.seek(Duration.ZERO);
//        currentSoundEffect.setVolume(0.1);
//        currentSoundEffect.play();
    }

    private void playSong(MediaPlayer song){
        song.seek(Duration.ZERO);
        song.setCycleCount(MediaPlayer.INDEFINITE);
        song.setVolume(0.1);
        song.play();
    }
}
