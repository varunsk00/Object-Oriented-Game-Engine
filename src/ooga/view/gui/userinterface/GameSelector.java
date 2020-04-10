package ooga.view.gui.userinterface;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooga.view.gui.userinterface.GamePreview;

import javax.imageio.ImageIO;
import java.util.List;
//TODO: REMOVE ALL MAGIC NUMBERS, REFACTOR MORE - SPECIFICALLY SCROLLLEFT AND RIGHT,
// FIGURE OUT HOW TO HANDLE ONLY 3 GAMES, FIX BUTTON NOT GETTING GAME NAME, FACTOR OUT FUNCTIONS INTO A FACTORY FOR ALL UI
// FIGURE OUT HOW TO STOP SPAMMING ARROWS, MOVE ALL STYLING TO CSS, MAKE ARROWS INTO STACKPANES AND ADD TEXT
public class GameSelector extends BorderPane {
    Polygon leftScrollArrow;
    Polygon rightScrollArrow;
    List<GamePreview> playableGamesList;
    Button selectGameButton;
    private VBox menuFrame;
    private HBox gameSelectionBox;
    private VBox selectButtonAndNameBox;
    private HBox gameNameBackground;
    private Text gameName;
    private Group gameSwitchGroup;

    public GameSelector(List<GamePreview> playableGames) {
        this.playableGamesList = playableGames;
        this.menuFrame = new VBox();
        this.gameSelectionBox = new HBox();
        this.selectButtonAndNameBox = new VBox();
        this.gameSwitchGroup = new Group();
        this.gameName = new Text();
        initGameSwitchGroup();
        initSelectionUI();
        initCompleteView();
    }

    private void initGameSwitchGroup() {
        initLeftArrow();
        initRightArrow();
        initializePreviewPos();
        gameSelectionBox.getChildren().add(gameSwitchGroup);
        gameSelectionBox.setMinHeight(300);
        gameSelectionBox.setAlignment(Pos.CENTER);
    }
    private void initSelectionUI() {
        createGameSelectButton();
        selectButtonAndNameBox.getChildren().add(selectGameButton);
        selectButtonAndNameBox.setPadding(new Insets(0, 50, 0, 415));
        selectButtonAndNameBox.setSpacing(15);
        selectButtonAndNameBox.getChildren().add(gameNameBackground);
    }
    private void initCompleteView() {
        menuFrame.getChildren().add(gameSelectionBox);
        menuFrame.getChildren().add(selectButtonAndNameBox);
        menuFrame.setAlignment(Pos.CENTER);
        menuFrame.setSpacing(40);
        this.setLayoutX(175);
        this.setLayoutY(100);
        this.setCenter(menuFrame);
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
        LineTo l = new LineTo(150, 325);
        playPathTransition(playableGamesList.get(playableGamesList.size()-1), m, l);
        reinitializePreviewPos();
        playGrowOrShrinkTransition(false);
    }
    public void scrollRight() {
        GamePreview temp = playableGamesList.get(playableGamesList.size()-1);
        for (int i = playableGamesList.size()-1; i > 0; i --) {
            playableGamesList.set(i, playableGamesList.get(i - 1));
            double oldXPos = playableGamesList.get(i).getXPos();
            if (oldXPos > 100 && oldXPos < 850) {
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
        playGrowOrShrinkTransition(true);
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,0.0, 50.0);
        leftScrollArrow.setFill(Color.WHITE);
        leftScrollArrow.setStroke(Color.RED);
        leftScrollArrow.setTranslateX(60);
        leftScrollArrow.setTranslateY(300);
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.LIGHTGRAY));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.WHITE));
        leftScrollArrow.setOnMouseClicked(e -> scrollLeft());
        gameSwitchGroup.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setFill(Color.WHITE);
        rightScrollArrow.setStroke(Color.RED);
        rightScrollArrow.setTranslateX(850);
        rightScrollArrow.setTranslateY(275);
        rightScrollArrow.setOnMousePressed(e -> rightScrollArrow.setFill(Color.LIGHTGRAY));
        rightScrollArrow.setOnMouseReleased(e -> rightScrollArrow.setFill(Color.WHITE));
        rightScrollArrow.setOnMouseClicked(e -> scrollRight());
        gameSwitchGroup.getChildren().add(rightScrollArrow);
    }
    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos(100 + playableGamesList.get(i).getWidth()/2 + 300*i);
            if (playableGamesList.get(i).getXPos() < 850) {
                playableGamesList.get(i).setVisible(true);
            }
        }
        gameName.setText(playableGamesList.get(1).getGameName());
        gameName.setFill(playableGamesList.get(1).getColor());
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setX((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            playableGamesList.get(i).setXPos((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            gameSwitchGroup.getChildren().add(playableGamesList.get(i));
            if (playableGamesList.get(i).getXPos() > 900 || playableGamesList.get(i).getXPos() < 100) {
                playableGamesList.get(i).setVisible(false);
            }
            else {
                if (playableGamesList.get(i).getXPos() == 450) {
                   playableGamesList.get(i).setScaleX(2.1);
                   playableGamesList.get(i).setScaleY(2.1);
                   gameName.setText(playableGamesList.get(i).getGameName());
                   gameName.setFill(playableGamesList.get(1).getColor());
                }
                playableGamesList.get(i).setVisible(true);
            }
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
    /***
     * @param direction - true == left, false == right
     */
    private void playGrowOrShrinkTransition(boolean direction) {
        growCenterGame();
        Transition growOrShrinkTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }
            @Override
            protected void interpolate(double frac) {
                if (direction) {

                    playableGamesList.get(2).setScaleX(playableGamesList.get(2).getScaleX() - .022);
                    playableGamesList.get(2).setScaleY(playableGamesList.get(2).getScaleY() - .022);
                } else {

                    playableGamesList.get(0).setScaleX(playableGamesList.get(0).getScaleX() - .022);
                    playableGamesList.get(0).setScaleY(playableGamesList.get(0).getScaleY() - .022);
                }
            }
        };
        growOrShrinkTransition.play();
    }
    private void growCenterGame() {
        Transition growCenterGameTransition = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }
            @Override
            protected void interpolate(double frac) {
                playableGamesList.get(1).setScaleX(playableGamesList.get(1).getScaleX() + .022);
                playableGamesList.get(1).setScaleY(playableGamesList.get(1).getScaleY() + .022);
            }
        };
        growCenterGameTransition.play();
    }
    public void createGameSelectButton() {
        selectGameButton = makeButton("Select", e -> playableGamesList.get(1).chooseGame());
        gameName.setFont(Font.font("ariel", FontWeight.BOLD, FontPosture.REGULAR, 50));
        gameNameBackground = new HBox();
        gameNameBackground.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        gameNameBackground.setPadding(new Insets(25, 25, 25, 25));
        gameNameBackground.setMaxSize(200, 100);
        gameNameBackground.setTranslateX(-70);
        gameNameBackground.setAlignment(Pos.CENTER);
        gameNameBackground.getChildren().add(gameName);
    }

    private Button makeButton(String property, EventHandler<ActionEvent> handler) {
        // represent all supported image suffixes
        final String IMAGEFILE_SUFFIXES = String
                .format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));
        Button result = new Button();
     //   String label = resources.getString(property);
        String label = property;
      //  if (label.matches(IMAGEFILE_SUFFIXES)) {
       //     result.setGraphic(new ImageView(
       //             new Image(getClass().getResourceAsStream(DEFAULT_RESOURCE_FOLDER + label))));
       // } else {
            result.setText(label);
      //  }
        result.setOnAction(handler);
        return result;
    }


}

