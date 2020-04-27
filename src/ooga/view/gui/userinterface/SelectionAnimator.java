package ooga.view.gui.userinterface;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class SelectionAnimator {
    private final double ZERO_PERCENT = 0.0;
    private final double ONEHUNDRED_PERCENT = 100.0;
    private final double SCALE_FACTOR = 0.022;
    private final int GLOW_HEIGHT = 10;
    private final int MEDIUM_DURATION = 1000;
    private final int DEFAULT_OFFSET = 0;

    public SelectionAnimator() {
            //empty constructor
    }
    public void addGlowEffects(Node selectedGame) {
        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.GOLD);
        glowEffect.setOffsetX(DEFAULT_OFFSET);
        glowEffect.setOffsetY(DEFAULT_OFFSET);
        glowEffect.setHeight(GLOW_HEIGHT);
        glowEffect.setWidth(GLOW_HEIGHT);
        selectedGame.setEffect(glowEffect);
    }
    public void playFadeTransition(Node node, Boolean isReversed, int duration) {
        FadeTransition fadeTransition = new FadeTransition();
        if (isReversed) {
            fadeTransition.setFromValue(ZERO_PERCENT);
            fadeTransition.setToValue(ONEHUNDRED_PERCENT);
        }
        else {
            fadeTransition.setFromValue(ONEHUNDRED_PERCENT);
            fadeTransition.setToValue(ZERO_PERCENT);
        }
        fadeTransition.setNode(node);
        fadeTransition.setDuration(Duration.millis(duration));
        fadeTransition.play();
    }
    public void playPathTransition(Node node, MoveTo pathBeginning, LineTo pathEnd) {
        PathTransition p = new PathTransition();
        Path path = new Path();
        path.getElements().add(pathBeginning);
        path.getElements().add(pathEnd);
        p.setNode(node);
        p.setDuration(Duration.millis(MEDIUM_DURATION));
        p.setPath(path);
        p.play();
    }
    public void playGrowOrShrinkTransition(Node leftOrRight, Node center) {
        growCenterGame(center);
        Transition growOrShrinkTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(MEDIUM_DURATION));
            }
            @Override
            protected void interpolate(double frac) {
                    leftOrRight.setScaleX(leftOrRight.getScaleX() - SCALE_FACTOR);
                    leftOrRight.setScaleY(leftOrRight.getScaleY() - SCALE_FACTOR);

            }
        };
        growOrShrinkTransition.play();
    }
    private void growCenterGame(Node center) {
        Transition growCenterGameTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(MEDIUM_DURATION));
            }
            @Override
            protected void interpolate(double frac) {
                center.setScaleX(center.getScaleX() + SCALE_FACTOR);
                center.setScaleY(center.getScaleY() + SCALE_FACTOR);
            }
        };
        growCenterGameTransition.play();
    }
}
