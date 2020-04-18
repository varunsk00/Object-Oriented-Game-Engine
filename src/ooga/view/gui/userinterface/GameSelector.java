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
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
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
// FIGURE OUT HOW TO STOP SPAMMING ARROWS, MOVE ALL STYLING TO CSS, MAKE ARROWS INTO STACKPANES AND ADD TEXT, REFACTOR SPAM?
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
    private VBox menuBottom = new VBox();
    private VBox menuTop = new VBox();
    private double pastTime = -10000000;
    StackPane p = new StackPane();

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
        initMenuTop();
        initMenuBottom();
    }
    private void initMenuBottom() {
        HBox darkgray = new HBox();
        HBox lightgray = new HBox();
        for (int i = 0; i < 11; i++) {
            Rectangle r = new Rectangle(10, 17.5, Color.BLACK);
            darkgray.getChildren().add(r);
        }
        VBox textbox = new VBox();
        textbox.setSpacing(5);
        Text t1 = new Text("BOOGA");
        Text t2 = new Text("Bob's Object Oriented Game Arcade");
        t1.setFill(Color.RED);
        t1.setId("logo");
        t2.setFill(Color.RED);
        t2.setId("logo");
        t1.setFont(Font.font(35));
        t2.setFont(Font.font(20));
        textbox.getChildren().addAll(t1, t2);
        lightgray.getChildren().add(selectGameButton);
        lightgray.getChildren().add(textbox);
        selectGameButton.setId("start");
        textbox.setTranslateY(15);
        selectGameButton.setTranslateY(15);
        lightgray.setPadding(new Insets(0, 0, 0, 20));
        lightgray.setSpacing(10);
        darkgray.setSpacing(5);
        darkgray.setPadding(new Insets(17.5, 0, 0, 100));
        VBox rectangles = new VBox();
        Rectangle r1 = new Rectangle(150, 15, Color.DARKGRAY);
        Rectangle r2 = new Rectangle(150, 15, Color.DARKGRAY);
        r1.setStroke(Color.BLACK);
        r2.setStroke(Color.BLACK);
        r2.setTranslateY(-2.5);
        r1.setStrokeWidth(2.5);
        r2.setStrokeWidth(2.5);
        rectangles.getChildren().addAll(r1, r2);
        darkgray.getChildren().add(rectangles);
        rectangles.setTranslateX(700);
        rectangles.setTranslateY(-18);
        Rectangle r3 = new Rectangle(150, 63, Color.BLACK);
        r3.setStroke(Color.BLACK);
        r3.setStrokeWidth(2.5);
        lightgray.getChildren().add(r3);
        r3.setTranslateX(586);
        r3.setTranslateY(2);
        darkgray.setStyle("-fx-background-color: #a9a9a9");
        darkgray.setMinHeight(35);
        lightgray.setMinHeight(65);
        darkgray.setMinWidth(1280);
        lightgray.setMinWidth(1280);
        lightgray.setStyle("-fx-background-color: #d3d3d3");
        menuBottom.getChildren().add(lightgray);
        menuBottom.getChildren().add(darkgray);
        menuBottom.setTranslateX(-175);
        menuBottom.setTranslateY(-100);
//        darkgray.setLayoutX(0);
//        lightgray.setLayoutY(690);
//        darkgray.setLayoutY(720);
//        lightgray.setLayoutX(0);
        this.setBottom(menuBottom);
    }
    private void initMenuTop() {
        HBox darkgray = new HBox();
        HBox lightgray = new HBox();
        for (int i = 0; i < 11; i++) {
            Rectangle r = new Rectangle(10, 17.5, Color.BLACK);
            darkgray.getChildren().add(r);
        }
        darkgray.setSpacing(5);
        darkgray.setPadding(new Insets(0, 0, 0, 100));
        VBox rectangles = new VBox();
        Rectangle r1 = new Rectangle(150, 15, Color.DARKGRAY);
        Rectangle r2 = new Rectangle(150, 15, Color.DARKGRAY);
        r1.setStroke(Color.BLACK);
        r2.setStroke(Color.BLACK);
        r1.setStrokeWidth(2.5);
        r2.setStrokeWidth(2.5);
        r2.setTranslateY(-1.5);
        rectangles.getChildren().addAll(r1, r2);
        darkgray.getChildren().add(rectangles);
        rectangles.setTranslateX(700);
        Rectangle r3 = new Rectangle(150, 63, Color.BLACK);
        r3.setStroke(Color.BLACK);
        r3.setStrokeWidth(2.5);
        lightgray.getChildren().add(r3);
        r3.setTranslateX(965);
        r3.setTranslateY(-2);
        darkgray.setStyle("-fx-background-color: #a9a9a9");
        darkgray.setMinHeight(35);
        lightgray.setMinHeight(65);
        darkgray.setMinWidth(1280);
        lightgray.setMinWidth(1280);
        lightgray.setStyle("-fx-background-color: #d3d3d3");
        menuTop.getChildren().add(darkgray);
        menuTop.getChildren().add(lightgray);
        menuTop.setAlignment(Pos.CENTER);
        menuTop.setTranslateX(-175);
        menuTop.setTranslateY(-100);
//        darkgray.setLayoutX(0);
//        lightgray.setLayoutY(0);
//        darkgray.setLayoutY(100);
//        lightgray.setLayoutX(0);
        this.setTop(menuTop);
    }

    private void initGameSwitchGroup() {
        initLeftArrow();
        initRightArrow();
        initializePreviewPos();
        gameSelectionBox.getChildren().add(gameSwitchGroup);
        gameSelectionBox.setTranslateX(13);
        //gameSelectionBox.setMinHeight(300);
        //gameSelectionBox.setTranslateX(-75);
        //gameSelectionBox.setAlignment(Pos.CENTER);
        gameSelectionBox.setId("gameselectionbox");
    }
    private void initSelectionUI() {
        createGameSelectButton();
        //selectButtonAndNameBox.getChildren().add(selectGameButton);
        //selectButtonAndNameBox.setPadding(new Insets(0, 50, 0, 415));
        //selectButtonAndNameBox.setSpacing(15);
        selectButtonAndNameBox.setTranslateY(-50);
        selectButtonAndNameBox.setTranslateX(200);
        selectButtonAndNameBox.setId("buttonandnamebox");
        selectButtonAndNameBox.getChildren().add(p);
    }
    private void initCompleteView() {
        menuFrame.getChildren().add(gameSelectionBox);
        menuFrame.getChildren().add(selectButtonAndNameBox);
        menuFrame.setSpacing(40);
        menuFrame.setTranslateY(-100);
        menuFrame.setTranslateX(-175);
        menuFrame.setAlignment(Pos.CENTER_RIGHT);
        menuFrame.setMinHeight(520);
        menuFrame.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setLayoutX(175);
        this.setLayoutY(100);
        this.setCenter(menuFrame);
    }
    public void scrollRight() {
        if (System.currentTimeMillis() - pastTime > 1500) {
            GamePreview temp = playableGamesList.get(0);
            for (int i = 0; i < playableGamesList.size() - 1; i++) {
                playableGamesList.set(i, playableGamesList.get(i + 1));
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
            playableGamesList.set(playableGamesList.size() - 1, temp);
            playFadeTransition(playableGamesList.get(playableGamesList.size() - 1), false, 500);
            MoveTo m = new MoveTo(playableGamesList.get(playableGamesList.size() - 1).getXPos() + 50, 325);
            LineTo l = new LineTo(150, 325);
            playPathTransition(playableGamesList.get(playableGamesList.size() - 1), m, l);
            reinitializePreviewPos();
            playGrowOrShrinkTransition(false);
            pastTime = System.currentTimeMillis();
        }
    }
    public void scrollLeft() {
        if (System.currentTimeMillis() - pastTime > 1500) {
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
            pastTime = System.currentTimeMillis();
        }
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,0.0, 50.0);
        leftScrollArrow.setFill(Color.RED);
        leftScrollArrow.setStroke(Color.DARKRED);
        leftScrollArrow.setTranslateX(60);
        leftScrollArrow.setTranslateY(275);
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.DARKRED));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.RED));
        leftScrollArrow.setOnMouseClicked(e -> scrollLeft());
        gameSwitchGroup.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setFill(Color.RED);
        rightScrollArrow.setStroke(Color.DARKRED);
        rightScrollArrow.setTranslateX(850);
        rightScrollArrow.setTranslateY(275);
        rightScrollArrow.setOnMousePressed(e -> rightScrollArrow.setFill(Color.DARKRED));
        rightScrollArrow.setOnMouseReleased(e -> rightScrollArrow.setFill(Color.RED));
        rightScrollArrow.setOnMouseClicked(e -> scrollRight());
        gameSwitchGroup.getChildren().add(rightScrollArrow);
    }
    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos(100 + playableGamesList.get(i).getWidth()/2 + 300*i);
            playableGamesList.get(i).setLayoutX((0));
            playableGamesList.get(i).setLayoutY(0);
            if (playableGamesList.get(i).getXPos() < 850) {
                playableGamesList.get(i).setVisible(true);
            }
            if (i == 1) {
                DropShadow d = new DropShadow();
                d.setColor(Color.GOLD);
                d.setOffsetX(0);
                d.setOffsetY(0);
                d.setHeight(10);
                d.setWidth(10);
                playableGamesList.get(i).setEffect(d);
                playableGamesList.get(i).setClickable(true);
            }
            else {
                playableGamesList.get(i).setEffect(null);
                playableGamesList.get(i).setClickable(false);
            }
        }
        gameName.setText(playableGamesList.get(1).getGameName());
        gameName.setFill(playableGamesList.get(1).getColor());
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setLayoutX((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            playableGamesList.get(i).setXPos((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            gameSwitchGroup.getChildren().add(playableGamesList.get(i));
            if (playableGamesList.get(i).getXPos() > 900 || playableGamesList.get(i).getXPos() < 100) {
                playableGamesList.get(i).setVisible(false);
                playableGamesList.get(i).setClickable(false);
            }
            else {
                if (playableGamesList.get(i).getXPos() == 450) {
                    DropShadow d = new DropShadow();
                    d.setColor(Color.GOLD);
                    d.setOffsetX(0);
                    d.setOffsetY(0);
                    d.setHeight(10);
                    d.setWidth(10);
                    playableGamesList.get(i).setEffect(d);
                    playableGamesList.get(i).setScaleX(2.1);
                    playableGamesList.get(i).setScaleY(2.1);
                    gameName.setText(playableGamesList.get(i).getGameName());
                    gameName.setFill(playableGamesList.get(1).getColor());
                    playableGamesList.get(i).setClickable(true);
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
        gameName.setId("gamename");
       // gameNameBackground = new HBox();

        Rectangle r = new Rectangle(600, 100);
        r.setFill(Color.BLACK);
        r.setArcHeight(20);
        r.setArcWidth(30);
//        gameNameBackground.getChildren().add(r);
//        gameNameBackground.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
//        gameNameBackground.setPadding(new Insets(25, 25, 25, 25));
//        gameNameBackground.setPrefWidth(600);
//        gameNameBackground.setMaxSize(600, 100);
//        gameNameBackground.setTranslateX(-290);
//        gameNameBackground.setAlignment(Pos.CENTER);
//        gameNameBackground.getChildren().add(gameName);
        p.getChildren().add(r);
        p.setPadding(new Insets(25, 25, 25, 25));
        p.setPrefWidth(600);
        p.setMaxSize(600, 100);
        p.setTranslateX(-290);
        p.setAlignment(Pos.CENTER);
        p.getChildren().add(gameName);
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
