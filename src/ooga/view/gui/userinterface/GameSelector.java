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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.util.List;
//TODO: REMOVE ALL MAGIC NUMBERS, REFACTOR MORE - SPECIFICALLY SCROLLLEFT AND RIGHT,
// FIGURE OUT HOW TO HANDLE ONLY 3 GAMES, FIX BUTTON NOT GETTING GAME NAME, FACTOR OUT FUNCTIONS INTO A FACTORY FOR ALL UI
// FIGURE OUT HOW TO STOP SPAMMING ARROWS, MOVE ALL STYLING TO CSS, MAKE ARROWS INTO STACKPANES AND ADD TEXT, REFACTOR SPAM?
public class GameSelector extends BorderPane {
    private final int GAMEDISPLAYLIMIT = 3; //Preferably ODD
    private final int GAPDISTANCE = 250;
    private final int ANIMATIONSTABILIZER = 50;
    private Polygon leftScrollArrow;
    private Polygon rightScrollArrow;
    private List<GamePreview> playableGamesList;
    private Button selectGameButton;
    private VBox menuFrame;
    private HBox gameCartridgeBox;
    private Text gameNameText;
    private Group gameSelectionAndArrowsGroup;
    private VBox menuBottom = new VBox();
    private VBox menuTop = new VBox();
    private double pastTime = -10000000;
    private StackPane gameNameDisplay = new StackPane();
    private GamePreview CENTER;

    public GameSelector(List<GamePreview> playableGames) {
        this.playableGamesList = playableGames;
        this.menuFrame = new VBox();
        this.gameCartridgeBox = new HBox();
        this.gameSelectionAndArrowsGroup = new Group();
        this.gameNameText = new Text();
        this.CENTER = playableGamesList.get(GAMEDISPLAYLIMIT/2);
        this.setId("gameSelector");
        initGameSwitchGroup();
        initSelectionUI();
        initCompleteView();
        initMenuTop();
        initMenuBottom();
    }
    private HBox createMenuAccent() {
        HBox darkgray = new HBox();
        for (int i = 0; i < 11; i++) {
            Rectangle r = new Rectangle(10, 17.5);
            r.setId("vent");
            darkgray.getChildren().add(r);
        }
        darkgray.setId("consoleAccent");
        return darkgray;
    }
    private HBox createMainSkin() {
        HBox lightgray = new HBox();
        lightgray.setId("mainConsoleArea");
        return lightgray;
    }
    private VBox createVentArt() {
        VBox rectangles = new VBox();
        Rectangle r1 = new Rectangle(150, 15);
        Rectangle r2 = new Rectangle(150, 15);
        r1.setId("lidVentArt1");
        r2.setId("lidVentArt2");
        rectangles.getChildren().addAll(r1, r2);
        return rectangles;
    }
    private VBox createLogoArt() {
        VBox textbox = new VBox();
        textbox.setId("logoBox");
        Text t1 = new Text("BOOGA");
        Text t2 = new Text("Bob's Object Oriented Game Arcade");
        t1.setId("logoAbbreviationText");
        t2.setId("logoText");
        textbox.getChildren().addAll(t1, t2);
        return textbox;
    }
    private void initMenuBottom() {
        HBox mainSkin = createMainSkin();
        VBox ventArt = createVentArt();
        HBox menuAccent = createMenuAccent();
        VBox logoArt = createLogoArt();
        Rectangle lidArt = new Rectangle(150, 63);
        lidArt.setId("lidArtBottom");
        ventArt.setId("ventBoxBottom");
        mainSkin.getChildren().add(lidArt);
        mainSkin.getChildren().add(selectGameButton);
        mainSkin.getChildren().add(logoArt);
        menuAccent.getChildren().add(ventArt);
        menuBottom.getChildren().add(mainSkin);
        menuBottom.getChildren().add(menuAccent);
        menuBottom.setId("menuSkin");
        this.setBottom(menuBottom);
    }
    private void initMenuTop() {
        HBox mainSkin = createMainSkin();
        VBox ventArt = createVentArt();
        HBox menuAccent = createMenuAccent();
        Rectangle lidArt = new Rectangle(150, 63);
        lidArt.setId("lidArtTop");
        ventArt.setId("ventBoxTop");
        mainSkin.getChildren().add(lidArt);
        mainSkin.getChildren().add(selectGameButton);
        menuAccent.getChildren().add(ventArt);
        menuTop.getChildren().add(menuAccent);
        menuTop.getChildren().add(mainSkin);
        menuTop.setId("menuSkin");
        this.setTop(menuTop);
    }
    private void initGameSwitchGroup() {
        initLeftArrow();
        initRightArrow();
        initializePreviewPos();
        gameCartridgeBox.getChildren().add(gameSelectionAndArrowsGroup);
        gameCartridgeBox.setId("gameCartridgeBox");
    }
    private void initSelectionUI() {
        createGameSelectButton();
        Rectangle gameNameBackground = new Rectangle(600, 100);
        gameNameDisplay.setId("gameNameDisplay");
        gameNameText.setId("gameNameText");
        gameNameBackground.setId("gameNameBackground");
        gameNameDisplay.getChildren().add(gameNameBackground);
        gameNameDisplay.getChildren().add(gameNameText);
    }
    private void initCompleteView() {
        menuFrame.getChildren().add(gameCartridgeBox);
        menuFrame.getChildren().add(gameNameDisplay);
        menuFrame.setId("menuFrame");
        this.setCenter(menuFrame);
    }
    public void scrollRight() {
        if (System.currentTimeMillis() - pastTime > 1500) {
            GamePreview temp = playableGamesList.get(0);
            for (int i = 0; i < playableGamesList.size() - 1; i++) {
                playableGamesList.set(i, playableGamesList.get(i + 1));
                double oldXPos = playableGamesList.get(i).getXPos();
                System.out.println(i);
                System.out.println(oldXPos);
                if (i < GAMEDISPLAYLIMIT) {
                    if (i == GAMEDISPLAYLIMIT-1) {
                        playFadeTransition(playableGamesList.get(i), true, 2000);
                        MoveTo m = new MoveTo(850, 325);
                        LineTo l = new LineTo(oldXPos - GAPDISTANCE, 325);
                        playPathTransition(playableGamesList.get(i), m, l);
                    } else {
                        MoveTo m = new MoveTo(oldXPos + ANIMATIONSTABILIZER, 325);
                        LineTo l = new LineTo(oldXPos - GAPDISTANCE, 325);
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
                        MoveTo m = new MoveTo(oldXPos + ANIMATIONSTABILIZER, 325);
                        LineTo l = new LineTo(850, 325);
                        playPathTransition(playableGamesList.get(i), m, l);
                    }
                    else {
                        MoveTo m = new MoveTo(oldXPos + ANIMATIONSTABILIZER, 325);
                        LineTo l = new LineTo(oldXPos + GAPDISTANCE + playableGamesList.get(i).getWidth(), 325);
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
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.DARKRED));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.RED));
        leftScrollArrow.setOnMouseClicked(e -> scrollLeft());
        leftScrollArrow.setId("leftArrow");
        gameSelectionAndArrowsGroup.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setOnMousePressed(e -> rightScrollArrow.setFill(Color.DARKRED));
        rightScrollArrow.setOnMouseReleased(e -> rightScrollArrow.setFill(Color.RED));
        rightScrollArrow.setOnMouseClicked(e -> scrollRight());
        rightScrollArrow.setId("rightArrow");
        gameSelectionAndArrowsGroup.getChildren().add(rightScrollArrow);
    }
    private void reinitializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setXPos(100 + playableGamesList.get(i).getWidth()/2 + 300*i);
            playableGamesList.get(i).setLayoutX((0));
            playableGamesList.get(i).setLayoutY(0);
            if (playableGamesList.get(i).getXPos() < 850) {
                playableGamesList.get(i).setVisible(true);
            }
            if (playableGamesList.get(i).equals(CENTER)) {
                addGlowEffects(playableGamesList.get(1));
                updateGameNameTextAndPreview(playableGamesList.get(1));
            }
            else {
                playableGamesList.get(i).setEffect(null);
                playableGamesList.get(i).setClickable(false);
            }
        }
    }
    private void initializePreviewPos() {
        for (int i = 0; i < playableGamesList.size(); i ++) {
            playableGamesList.get(i).setLayoutX((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            playableGamesList.get(i).setXPos((100+ playableGamesList.get(i).getWidth()/2)+300*i);
            gameSelectionAndArrowsGroup.getChildren().add(playableGamesList.get(i));
            if (playableGamesList.get(i).getXPos() > 900 || playableGamesList.get(i).getXPos() < 100) {
                playableGamesList.get(i).setVisible(false);
                playableGamesList.get(i).setClickable(false);
            }
            else {
                if (playableGamesList.get(i).equals(CENTER)) {
                    initializeSelectedGameAesthetics(playableGamesList.get(i));
                }
                playableGamesList.get(i).setVisible(true);
            }
        }
    }
    private void initializeSelectedGameAesthetics(GamePreview selectedGame) {
        addGlowEffects(selectedGame);
        selectedGame.setScaleX(2.1);
        selectedGame.setScaleY(2.1);
        updateGameNameTextAndPreview(selectedGame);
    }
    private void updateGameNameTextAndPreview(GamePreview selectedGame) {
        gameNameText.setText(selectedGame.getGameName());
        selectedGame.setClickable(true);
    }
    private void addGlowEffects(Node selectedGame) {
        DropShadow glowEffect = new DropShadow();
        glowEffect.setColor(Color.GOLD);
        glowEffect.setOffsetX(0);
        glowEffect.setOffsetY(0);
        glowEffect.setHeight(10);
        glowEffect.setWidth(10);
        selectedGame.setEffect(glowEffect);
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
        selectGameButton.setId("selectGameButton");
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
