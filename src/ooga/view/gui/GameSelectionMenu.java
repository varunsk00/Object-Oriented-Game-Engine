package ooga.view.gui;

import javafx.animation.PathTransition;
import javafx.scene.Group;
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
        rightScrollArrow.setOnMouseClicked(e -> scrollRight());
        initializePreviewPos();
    }
    public void addNewGamePreview(GamePreview newGamePreview) {
        this.getChildren().add(newGamePreview);
        this.playableGamesList.add(newGamePreview);

    }
    private void scrollLeft() {
        GamePreview temp = playableGamesList.get(0);
        double e = temp.getXPos();
        for (int i = 0; i < playableGamesList.size()-1; i ++) {
            playableGamesList.set(i, playableGamesList.get(i+1));
            double oldXPos = playableGamesList.get(i).getXPos()+playableGamesList.get(i).getWidth()/2;
            PathTransition p = new PathTransition();
            MoveTo m = new MoveTo(playableGamesList.get(i).getXPos()+playableGamesList.get(i).getWidth()/2, 300);
            LineTo l = new LineTo(oldXPos-250, 300);
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
        MoveTo m = new MoveTo(playableGamesList.get(playableGamesList.size()-1).getXPos()+playableGamesList.get(playableGamesList.size()-1).getWidth()/2, 300);
        LineTo l = new LineTo(playableGamesList.get(playableGamesList.size()-1).getXPos()+playableGamesList.get(playableGamesList.size()-1).getWidth()/2+500, 300);
        Path path = new Path();
        path.getElements().add(m);
        path.getElements().add(l);
        p.setNode(playableGamesList.get(playableGamesList.size()-1));
        p.setDuration(Duration.millis(2000));
        p.setPath(path);
        p.play();
        reinitializePreviewPos();
    }
    private void scrollRight() {
        GamePreview temp = playableGamesList.get(playableGamesList.size()-1);
        for (int i = playableGamesList.size()-1; i > 0; i --) {
            playableGamesList.set(i, playableGamesList.get(i-1));
        }
        playableGamesList.set(0, temp);
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
        for (int i = 0; i < 3; i ++) {
            playableGamesList.get(i).setXPos((i+1)*200+(50*i));
        }
    }
    private void initializePreviewPos() {
        for (int i = 0; i < 3; i ++) {
            playableGamesList.get(i).setX((i+1)*200+50*i);
            playableGamesList.get(i).setXPos((i+1)*200+50*i);
            this.getChildren().add(playableGamesList.get(i));
        }
    }
}
