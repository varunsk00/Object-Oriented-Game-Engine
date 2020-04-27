package ooga.view.gui.userinterface;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.util.ResourceBundle;

public class ConsoleSkin extends BorderPane {
    private final String RESOURCES_PACKAGE = "resources.guiText";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private final String CONSOLE_NAME = myResources.getString("consoleName");
    private final String CONSOLE_ABBREV = myResources.getString("consoleAbbrev");
    private final int VENT_WIDTH = 150;
    private final int VENT_HEIGHT = 15;
    private final int LID_WIDTH = 150;
    private final int LID_HEIGHT = 63;
    private final int GAMENAME_DISPLAY_WIDTH = 600;
    private final int GAMENAME_DISPLAY_HEIGHT = 100;
    private final int MINIVENT_WIDTH = 10;
    private final double MINIVENT_HEIGHT = 17.5;
    private final double ZER0 = 0.0;
    private final double ONEHUNDRED = 100.0;
    private final double FIFTY = 50.0;
    private VBox menuTop;
    private VBox menuBottom;
    private Button selectGameButton;
    private Text gameNameText;
    private StackPane gameNameDisplay;
    private VBox menuFrame;
    private Polygon leftScrollArrow;
    private Polygon rightScrollArrow;
    private HBox gameCartridgeBox;
    private Group gameSelectionAndArrowsGroup;

    public ConsoleSkin() {
        this.menuTop = new VBox();
        this.menuBottom = new VBox();
        this.gameNameText = new Text();
        this.gameNameDisplay = new StackPane();
        this.menuFrame = new VBox();
        this.gameCartridgeBox = new HBox();
        this.gameSelectionAndArrowsGroup = new Group();
        this.setId("gameSelector");
        initCompleteView();
    }
    public Button getSelectGameButton() {
        return this.selectGameButton;
    }
    public Polygon getLeftScrollArrow() {
        return this.leftScrollArrow;
    }
    public Polygon getRightScrollArrow() {
        return this.rightScrollArrow;
    }
    public void updateGameNameTextAndPreview(GamePreview selectedGame) {
        gameNameText.setText(selectedGame.getGameName());
        selectedGame.setClickable(true);
    }
    private void initCompleteView() {
        initSelectionUI();
        initGameSwitchGroup();
        menuFrame.getChildren().add(gameCartridgeBox);
        menuFrame.getChildren().add(gameNameDisplay);
        menuFrame.setId("menuFrame");
        initMenuTop();
        initMenuBottom();
        this.setCenter(menuFrame);

    }
    private void initGameSwitchGroup() {
        initRightArrow();
        initLeftArrow();
        gameCartridgeBox.getChildren().add(gameSelectionAndArrowsGroup);
        gameCartridgeBox.setId("gameCartridgeBox");
    }
    private HBox createMenuAccent() {
        HBox menuAccent = new HBox();
        for (int i = 0; i < 11; i++) {
            Rectangle miniVent = new Rectangle(MINIVENT_WIDTH, MINIVENT_HEIGHT);
            miniVent.setId("vent");
            menuAccent.getChildren().add(miniVent);
        }
        menuAccent.setId("consoleAccent");
        return menuAccent;
    }
    private HBox createMainSkin() {
        HBox mainSkin = new HBox();
        mainSkin.setId("mainConsoleArea");
        return mainSkin;
    }
    private VBox createVentArt() {
        VBox ventArt = new VBox();
        Rectangle vent1 = new Rectangle(VENT_WIDTH, VENT_HEIGHT);
        Rectangle vent2 = new Rectangle(VENT_WIDTH, VENT_HEIGHT);
        vent1.setId("lidVentArt1");
        vent2.setId("lidVentArt2");
        ventArt.getChildren().addAll(vent1, vent2);
        return ventArt;
    }
    private VBox createLogoArt() {
        VBox logoBox = new VBox();
        logoBox.setId("logoBox");
        Text logoAbbreviated = new Text(CONSOLE_ABBREV);
        Text logo = new Text(CONSOLE_NAME);
        logoAbbreviated.setId("logoAbbreviationText");
        logo.setId("logoText");
        logoBox.getChildren().addAll(logoAbbreviated, logo);
        return logoBox;
    }
    private void initMenuBottom() {
        HBox mainSkin = createMainSkin();
        VBox ventArt = createVentArt();
        HBox menuAccent = createMenuAccent();
        VBox logoArt = createLogoArt();
        Rectangle lidArt = new Rectangle(LID_WIDTH, LID_HEIGHT);
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
        Rectangle lidArt = new Rectangle(LID_WIDTH, LID_HEIGHT);
        lidArt.setId("lidArtTop");
        ventArt.setId("ventBoxTop");
        mainSkin.getChildren().add(lidArt);
        menuAccent.getChildren().add(ventArt);
        menuTop.getChildren().add(menuAccent);
        menuTop.getChildren().add(mainSkin);
        menuTop.setId("menuSkin");
        this.setTop(menuTop);
    }
    private void initSelectionUI() {
        createGameSelectButton();
        Rectangle gameNameBackground = new Rectangle(GAMENAME_DISPLAY_WIDTH, GAMENAME_DISPLAY_HEIGHT);
        gameNameDisplay.setId("gameNameDisplay");
        gameNameText.setId("gameNameText");
        gameNameBackground.setId("gameNameBackground");
        gameNameDisplay.getChildren().add(gameNameBackground);
        gameNameDisplay.getChildren().add(gameNameText);
    }
    private void createGameSelectButton() {
        selectGameButton = makeButton("Select");
        selectGameButton.setId("selectGameButton");
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(FIFTY, ZER0,  FIFTY, ONEHUNDRED,ZER0, FIFTY);
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.DARKRED));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.RED));
        leftScrollArrow.setId("leftArrow");
        gameSelectionAndArrowsGroup.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(FIFTY, ZER0,  FIFTY, ONEHUNDRED,ONEHUNDRED, FIFTY);
        rightScrollArrow.setOnMousePressed(e -> rightScrollArrow.setFill(Color.DARKRED));
        rightScrollArrow.setOnMouseReleased(e -> rightScrollArrow.setFill(Color.RED));
        rightScrollArrow.setId("rightArrow");
        gameSelectionAndArrowsGroup.getChildren().add(rightScrollArrow);
    }
    public void updateGameSelectionGroup(GamePreview selectedGame) {
        gameSelectionAndArrowsGroup.getChildren().add(selectedGame);
    }
    private Button makeButton(String property) {
        Button result = new Button();
        String label = property;
        result.setText(label);
        return result;
    }
}
