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

import javax.imageio.ImageIO;

public class ConsoleSkin extends BorderPane {
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
            Rectangle miniVent = new Rectangle(10, 17.5);
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
        Rectangle vent1 = new Rectangle(150, 15);
        Rectangle vent2 = new Rectangle(150, 15);
        vent1.setId("lidVentArt1");
        vent2.setId("lidVentArt2");
        ventArt.getChildren().addAll(vent1, vent2);
        return ventArt;
    }
    private VBox createLogoArt() {
        VBox logoBox = new VBox();
        logoBox.setId("logoBox");
        Text logoAbbreviated = new Text("BOOGA");
        Text logo = new Text("Bob's Object Oriented Game Arcade");
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
        menuAccent.getChildren().add(ventArt);
        menuTop.getChildren().add(menuAccent);
        menuTop.getChildren().add(mainSkin);
        menuTop.setId("menuSkin");
        this.setTop(menuTop);
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
    private void createGameSelectButton() {
        selectGameButton = makeButton("Select");
        selectGameButton.setId("selectGameButton");
    }
    private void initLeftArrow() {
        leftScrollArrow = new Polygon();
        leftScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,0.0, 50.0);
        leftScrollArrow.setOnMousePressed(e -> leftScrollArrow.setFill(Color.DARKRED));
        leftScrollArrow.setOnMouseReleased(e -> leftScrollArrow.setFill(Color.RED));
        leftScrollArrow.setId("leftArrow");
        gameSelectionAndArrowsGroup.getChildren().add(leftScrollArrow);
    }
    private void initRightArrow() {
        rightScrollArrow = new Polygon();
        rightScrollArrow.getPoints().addAll(50.0, 0.0,  50.0, 100.0,100.0, 50.0);
        rightScrollArrow.setOnMousePressed(e -> rightScrollArrow.setFill(Color.DARKRED));
        rightScrollArrow.setOnMouseReleased(e -> rightScrollArrow.setFill(Color.RED));
        rightScrollArrow.setId("rightArrow");
        gameSelectionAndArrowsGroup.getChildren().add(rightScrollArrow);
    }
    public void updateGameSelectionGroup(GamePreview selectedGame) {
        gameSelectionAndArrowsGroup.getChildren().add(selectedGame);
    }
    private Button makeButton(String property) {
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
//        result.setOnAction(handler);
        return result;
    }
}
