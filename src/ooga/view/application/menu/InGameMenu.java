package ooga.view.application.menu;

import javafx.scene.layout.VBox;

public class InGameMenu extends VBox {
    private MenuButtons buttons;
    public InGameMenu(String currentGame) {
        buttons = new MenuButtons(currentGame);
        getChildren().add(buttons.getVBox());
    }
    public MenuButtons getButtons() {
        return buttons;
    }

}
