package ooga.view.application.menu;

import javafx.scene.layout.VBox;
import ooga.view.application.games.Game;

public class InGameMenu extends VBox {
    private MenuButtons buttons;
    public InGameMenu(Game currentGame) {
        buttons = new MenuButtons(currentGame);
        getChildren().add(buttons.getVBox());
    }
    public MenuButtons getButtons() {
        return buttons;
    }

}
