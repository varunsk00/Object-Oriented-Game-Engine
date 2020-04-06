package ooga.view.gui;

import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import ooga.view.gui.GamePreview;

import java.util.List;

public class GameSelectionMenu extends Group {
    Polygon leftScrollArrow;
    Polygon rightScrollArrow;
    List<GamePreview> playableGamesList;
    public GameSelectionMenu(List<GamePreview> playableGames) {
        this.playableGamesList = playableGames;
        initLeftArrow();
        initRightArrow();
        leftScrollArrow.setOnMouseClicked(e -> scrollLeft());
       // leftScrollArrow.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        rightScrollArrow.setOnMouseClicked(e -> scrollRight());
       // rightScrollArrow.setOnKeyPressed(e -> handleAltScrollInput(e.getCode()));
        initializePreviewPos();
    }
    public void addNewGamePreview(GamePreview newGamePreview) {
        this.getChildren().add(newGamePreview);
        this.playableGamesList.add(newGamePreview);

    }
    public void scrollLeft() {
        GamePreview temp = playableGamesList.get(0);
        for (int i = 0; i < playableGamesList.size()-1; i ++) {
            playableGamesList.set(i, playableGamesList.get(i+1));
            double oldXPos = playableGamesList.get(i).getXPos()+playableGamesList.get(i).getWidth()/4;
            PathTransition p = new PathTransition();
            MoveTo m = new MoveTo(oldXPos+playableGamesList.get(i).getWidth()/4, 325);
            LineTo l = new LineTo(oldXPos+playableGamesList.get(i).getWidth()/4-(800/playableGamesList.size()-playableGamesList.get(i).getWidth()/4), 325);
            Path path = new Path();
            path.getElements().add(m);
            path.getElements().add(l);
            p.setNode(playableGamesList.get(i));
            p.setDuration(Duration.millis(2000));
            p.setPath(path);
            p.play();
        }
        playableGamesList.set(playableGamesList.size()-1, temp);
        PathTransition p = new PathTransition();
        MoveTo m = new MoveTo(playableGamesList.get(playableGamesList.size()-1).getXPos()+playableGamesList.get(0).getWidth()/4, 325);
        LineTo l = new LineTo(playableGamesList.get(playableGamesList.size()-1).getXPos()+playableGamesList.get(0).getWidth()/4+(playableGamesList.size()*(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4)+10*(playableGamesList.size()-1))-(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4), 325);
        Path path = new Path();
        path.getElements().add(m);
        path.getElements().add(l);
        p.setNode(playableGamesList.get(playableGamesList.size()-1));
        p.setDuration(Duration.millis(2000));
        p.setPath(path);
        p.play();
        reinitializePreviewPos();
    }
    public void scrollRight() {
        GamePreview temp = playableGamesList.get(playableGamesList.size()-1);
        for (int i = playableGamesList.size()-1; i > 0; i --) {
            playableGamesList.set(i, playableGamesList.get(i-1));
            double oldXPos = playableGamesList.get(i).getXPos()+playableGamesList.get(i).getWidth()/4;
            PathTransition p = new PathTransition();
            MoveTo m = new MoveTo(oldXPos+playableGamesList.get(i).getWidth()/4, 325);
            LineTo l = new LineTo(oldXPos+playableGamesList.get(i).getWidth()/4+(800/playableGamesList.size()-playableGamesList.get(i).getWidth()/4), 325);
            Path path = new Path();
            path.getElements().add(m);
            path.getElements().add(l);
            p.setNode(playableGamesList.get(i));
            p.setDuration(Duration.millis(2000));
            p.setPath(path);
            p.play();
        }
        playableGamesList.set(0, temp);
        PathTransition p = new PathTransition();
        MoveTo m = new MoveTo(playableGamesList.get(0).getXPos()+playableGamesList.get(0).getWidth()/4, 325);
        LineTo l = new LineTo(((playableGamesList.get(0).getXPos()+playableGamesList.get(0).getWidth()/4)-(playableGamesList.size()*(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4)+10*(playableGamesList.size()-1)-(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4))), 325);
        Path path = new Path();
        path.getElements().add(m);
        path.getElements().add(l);
        p.setNode(playableGamesList.get(0));
        p.setDuration(Duration.millis(2000));
        p.setPath(path);
        p.play();
        reinitializePreviewPos();
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,0.0, 50.0);
        leftScrollArrow.setFill(Color.WHITE);
        leftScrollArrow.setStroke(Color.RED);
        leftScrollArrow.setTranslateX(50);
        leftScrollArrow.setTranslateY(275);
        this.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setFill(Color.WHITE);
        rightScrollArrow.setStroke(Color.RED);
        rightScrollArrow.setTranslateX(850);
        rightScrollArrow.setTranslateY(275);
        this.getChildren().add(rightScrollArrow);
    }
    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos((i+1)*(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4)+(10*i));
        }
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setScaleX(1.0/playableGamesList.size()*4);
            playableGamesList.get(i).setScaleY(1.0/playableGamesList.size()*4);
            playableGamesList.get(i).setX((i+1)*(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4)+10*i);
            playableGamesList.get(i).setXPos((i+1)*(800/playableGamesList.size()-playableGamesList.get(0).getWidth()/4)+(10*i));
            System.out.println(playableGamesList.get(i).getXPos());
            this.getChildren().add(playableGamesList.get(i));
        }
    }


}
