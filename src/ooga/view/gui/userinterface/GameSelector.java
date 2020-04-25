package ooga.view.gui.userinterface;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.List;

public class GameSelector {
    private final int GAME_DISPLAYLIMIT = 3; //Preferably ODD
    private final int GAP_DISTANCE = 250;
    private final int ANIMATION_HEIGHT = 325;
    private final int LONG_DURATION = 2000;
    private final int SHORT_DURATION = 500;
    private final int DEFAULT_LAYOUT = 0;

    private final double SPAM_BUFFER = 1500.0;

    private final double DEFAULT_SCALE = 2.1;

    private double gameWidth;
    private double animationStabilizer;
    private double timeElapsed = -10000000.0;

    private ConsoleSkin consoleSkin;
    private SelectionAnimator animator;

    private List<GamePreview> playableGamesList;
    private List<GamePreview> visibleGamesList;

    private GamePreview CENTER;
    private GamePreview RIGHT;
    private GamePreview LEFT;
    private GamePreview INCOMING_RIGHT;
    private GamePreview INCOMING_LEFT;

    public GameSelector(List<GamePreview> playableGames) {
        this.playableGamesList = playableGames;
        this.gameWidth = playableGamesList.get(0).getWidth();
        this.animationStabilizer = gameWidth/2;
        this.consoleSkin = new ConsoleSkin();
        this.animator = new SelectionAnimator();
        calibrateGamePositions();
        addActionsToConsoleSkin();
        initializePreviewPos();
    }
    public BorderPane getSelectionScreenVisuals() {
        return this.consoleSkin;
    }
    private void addActionsToConsoleSkin() {
        consoleSkin.getLeftScrollArrow().setOnMouseClicked(e -> scrollLeft());
        consoleSkin.getRightScrollArrow().setOnMouseClicked(e -> scrollRight());
        consoleSkin.getSelectGameButton().setOnAction(e -> CENTER.chooseGame());
    }
    private void calibrateGamePositions() {
        this.CENTER = playableGamesList.get(GAME_DISPLAYLIMIT /2);
        this.RIGHT = playableGamesList.get(GAME_DISPLAYLIMIT -1);
        this.LEFT = playableGamesList.get(0);
        this.INCOMING_RIGHT = playableGamesList.get(GAME_DISPLAYLIMIT);
        this.INCOMING_LEFT = playableGamesList.get(playableGamesList.size()-1);
        this.visibleGamesList = playableGamesList.subList(0, GAME_DISPLAYLIMIT);
    }
    public void scrollRight() {
        if (System.currentTimeMillis() - timeElapsed > SPAM_BUFFER) {
            calibrateGamePositions();
            shuffleGamePreviewListRight();
            animateScrollRight();
            timeElapsed = System.currentTimeMillis();
        }
    }
    private void shuffleGamePreviewListRight() {
        GamePreview temp = playableGamesList.get(0);
        for (int i = 0; i < playableGamesList.size() - 1; i++) {
            playableGamesList.set(i, playableGamesList.get(i + 1));
        }
        playableGamesList.set(playableGamesList.size() - 1, temp);
    }
    private void animateScrollRight() {
        for (int i = 0; i < visibleGamesList.size()-1; i++) {
            double oldXPos = visibleGamesList.get(i).getXPos();
            System.out.println(oldXPos);
            MoveTo pathBeginning = new MoveTo(oldXPos + animationStabilizer, ANIMATION_HEIGHT);
            LineTo pathEnd = new LineTo(oldXPos - GAP_DISTANCE, ANIMATION_HEIGHT);
            animator.playPathTransition(visibleGamesList.get(i), pathBeginning, pathEnd);
        }
        animator.playFadeTransition(INCOMING_RIGHT, true, LONG_DURATION);
        MoveTo pathBeginning = new MoveTo(RIGHT.getXPos()+gameWidth, ANIMATION_HEIGHT);
        LineTo pathEnd = new LineTo(INCOMING_RIGHT.getXPos() - GAP_DISTANCE, ANIMATION_HEIGHT);
        animator.playPathTransition(INCOMING_RIGHT, pathBeginning, pathEnd);
        animator.playFadeTransition(LEFT, false, SHORT_DURATION);
        MoveTo pathBeginning2 = new MoveTo(LEFT.getXPos() + animationStabilizer, ANIMATION_HEIGHT);
        LineTo pathEnd2 = new LineTo(LEFT.getXPos(), ANIMATION_HEIGHT);
        animator.playPathTransition(LEFT, pathBeginning2, pathEnd2);
        calibrateGamePositions();
        animator.playGrowOrShrinkTransition(LEFT, CENTER);
        reinitializePreviewPos();
    }
    public void scrollLeft() {
        if (System.currentTimeMillis() - timeElapsed > SPAM_BUFFER) {
            calibrateGamePositions();
            shuffleGamePreviewListLeft();
            animateScrollLeft();
            timeElapsed = System.currentTimeMillis();
        }
    }
    private void shuffleGamePreviewListLeft() {
        GamePreview temp = playableGamesList.get(playableGamesList.size()-1);
        for (int i = playableGamesList.size()-1; i > 0; i --) {
            playableGamesList.set(i, playableGamesList.get(i - 1));
        }
        playableGamesList.set(0, temp);
    }
    private void animateScrollLeft() {
        for (int i = visibleGamesList.size(); i > 0; i--) {
            double oldXPos = playableGamesList.get(i).getXPos();
            if (playableGamesList.get(i).equals(RIGHT)) {
                animator.playFadeTransition(RIGHT, false, SHORT_DURATION);
                MoveTo pathBeginning = new MoveTo(oldXPos + animationStabilizer, ANIMATION_HEIGHT);
                LineTo pathEnd = new LineTo(RIGHT.getXPos()+gameWidth, ANIMATION_HEIGHT);
                animator.playPathTransition(RIGHT, pathBeginning, pathEnd);
            } else {
                MoveTo pathBeginning = new MoveTo(oldXPos + animationStabilizer, ANIMATION_HEIGHT);
                LineTo pathEnd = new LineTo(oldXPos + GAP_DISTANCE + gameWidth, ANIMATION_HEIGHT);
                animator.playPathTransition(playableGamesList.get(i), pathBeginning, pathEnd);
            }
        }
        animator.playFadeTransition(INCOMING_LEFT,true,LONG_DURATION);
        MoveTo pathBeginning = new MoveTo(LEFT.getXPos(), ANIMATION_HEIGHT);
        LineTo pathEnd = new LineTo(LEFT.getXPos() + animationStabilizer, ANIMATION_HEIGHT);
        animator.playPathTransition(INCOMING_LEFT, pathBeginning, pathEnd);
        calibrateGamePositions();
        reinitializePreviewPos();
        animator.playGrowOrShrinkTransition(RIGHT, CENTER);
    }

    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos(gameWidth + animationStabilizer + (GAP_DISTANCE+ animationStabilizer)*i);
            playableGamesList.get(i).setLayoutX((DEFAULT_LAYOUT));
            playableGamesList.get(i).setLayoutY(DEFAULT_LAYOUT);
            if (i < GAME_DISPLAYLIMIT) {
                playableGamesList.get(i).setVisible(true);
            }
            if (playableGamesList.get(i).equals(CENTER)) {
                animator.addGlowEffects(CENTER);
                consoleSkin.updateGameNameTextAndPreview(CENTER);
            }
            else {
                playableGamesList.get(i).setEffect(null);
                playableGamesList.get(i).setClickable(false);
            }
        }
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setLayoutX((gameWidth + animationStabilizer)+(GAP_DISTANCE+ animationStabilizer)*i);
            playableGamesList.get(i).setXPos((gameWidth + animationStabilizer)+(GAP_DISTANCE+ animationStabilizer)*i);
            consoleSkin.updateGameSelectionGroup(playableGamesList.get(i));
            if (i > GAME_DISPLAYLIMIT-1) {
                playableGamesList.get(i).setVisible(false);
                playableGamesList.get(i).setClickable(false);
            }
            else {
                if (playableGamesList.get(i).equals(CENTER)) {
                    initializeSelectedGameAesthetics(CENTER);
                }
                playableGamesList.get(i).setVisible(true);
            }
        }
    }
    private void initializeSelectedGameAesthetics(GamePreview selectedGame) {
        animator.addGlowEffects(selectedGame);
        selectedGame.setScaleX(DEFAULT_SCALE);
        selectedGame.setScaleY(DEFAULT_SCALE);
        consoleSkin.updateGameNameTextAndPreview(selectedGame);
    }
}
