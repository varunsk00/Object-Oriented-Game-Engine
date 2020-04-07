package ooga.view.gui;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;


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
    public void scrollLeft() {
        GamePreview temp = playableGamesList.get(0);
        for (int i = 0; i < playableGamesList.size()-1; i ++) {
            playableGamesList.set(i, playableGamesList.get(i+1));
            double oldXPos = playableGamesList.get(i).getXPos();
            if (oldXPos > 100 && oldXPos <= 1050) {
                if (oldXPos > 900 && oldXPos <= 1050) {
                    playFadeTransition(playableGamesList.get(i), true, 2000);
                    MoveTo m = new MoveTo(850, 325);
                    LineTo l = new LineTo(oldXPos - 250, 325);
                    playPathTransition(playableGamesList.get(i), m, l);

                } else {
                    MoveTo m = new MoveTo(oldXPos + 50, 325);
                    LineTo l = new LineTo(oldXPos - 250, 325);
                    playPathTransition(playableGamesList.get(i), m, l);
                }
            }
        }
        playableGamesList.set(playableGamesList.size()-1, temp);
        playFadeTransition(playableGamesList.get(playableGamesList.size()-1), false, 500);
        MoveTo m = new MoveTo(playableGamesList.get(playableGamesList.size()-1).getXPos() + 50, 325);
        LineTo l = new LineTo(100, 325);
        playPathTransition(playableGamesList.get(playableGamesList.size()-1), m, l);
        reinitializePreviewPos();
    }
    public void scrollRight() {
        GamePreview temp = playableGamesList.get(playableGamesList.size()-1);
        for (int i = playableGamesList.size()-1; i > 0; i --) {
            playableGamesList.set(i, playableGamesList.get(i - 1));
            double oldXPos = playableGamesList.get(i).getXPos();
            if (oldXPos > 100 && oldXPos <= 1050) {
                if (oldXPos == 750) {
                    playFadeTransition(playableGamesList.get(i),false, 500);
                    MoveTo m = new MoveTo(oldXPos + 50, 325);
                    LineTo l = new LineTo(850, 325);
                    playPathTransition(playableGamesList.get(i), m, l);
                }
                else {
                    MoveTo m = new MoveTo(oldXPos + 50, 325);
                    LineTo l = new LineTo(oldXPos + 350, 325);
                    playPathTransition(playableGamesList.get(i), m, l);
                }
            }
        }
        playableGamesList.set(0, temp);
        playFadeTransition(playableGamesList.get(0),true,2000);
        MoveTo m = new MoveTo(150, 325);
        LineTo l = new LineTo(200, 325);
        playPathTransition(playableGamesList.get(0), m, l);
        reinitializePreviewPos();
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,0.0, 50.0);
        leftScrollArrow.setFill(Color.WHITE);
        leftScrollArrow.setStroke(Color.RED);
        leftScrollArrow.setTranslateX(50);
        leftScrollArrow.setTranslateY(275);
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.LIGHTGRAY));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.WHITE));
        this.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setFill(Color.WHITE);
        rightScrollArrow.setStroke(Color.RED);
        rightScrollArrow.setTranslateX(850);
        rightScrollArrow.setTranslateY(275);
        rightScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.LIGHTGRAY));
        rightScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.WHITE));
        this.getChildren().add(rightScrollArrow);
    }
    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos(100 + playableGamesList.get(i).getWidth()/2 + 300*i);
        }
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setX((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            playableGamesList.get(i).setXPos((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            this.getChildren().add(playableGamesList.get(i));

        }
    }
    private void playFadeTransition(Node node, Boolean isReversed, int duration) {
        FadeTransition f = new FadeTransition();
        if (isReversed) {
            f.setFromValue(0);
            f.setToValue(1);
        }
        else {
            f.setFromValue(1);
            f.setToValue(0);
        }
        f.setNode(node);
        f.setDuration(Duration.millis(duration));
        f.play();
    }
    private void playPathTransition(Node node, MoveTo m, LineTo l) {
        PathTransition p = new PathTransition();
        Path path = new Path();
        path.getElements().add(m);
        path.getElements().add(l);
        p.setNode(node);
        p.setDuration(Duration.millis(1000));
        p.setPath(path);
        p.play();
    }


}
