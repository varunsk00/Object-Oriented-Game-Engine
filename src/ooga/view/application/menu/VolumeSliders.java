package ooga.view.application.menu;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class VolumeSliders extends VBox {
    private final String RESOURCES_PACKAGE = "resources.guiText";
    private ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE);
    private static final int MIN_MUSIC = 0;
    private static final int DEFAULT_MUSIC = 2;
    private static final int MAX_MUSIC = 4;
    private Slider[] volumeSliders;

    public VolumeSliders(int numSliders){
        this.volumeSliders = new Slider[numSliders];
        renderSliders();
    }

    public Slider[] getSliders(){
        return volumeSliders;
    }

    private void renderSliders() {
        HBox allLabels = new HBox();
        addLabel(myResources.getString("SongSlider"), allLabels);
        addLabel(myResources.getString("EffectsSlider"), allLabels);
        HBox allSliders = new HBox();
        for(int index = 0; index < volumeSliders.length; index++){
            volumeSliders[index] = addAndReturnSlider(MIN_MUSIC, MAX_MUSIC, DEFAULT_MUSIC, allSliders);
        }
        getChildren().add(allLabels);
        getChildren().add(allSliders);
    }

    private void addLabel(String key, HBox text) {
        Label tempLabel = new Label(key);
        tempLabel.setMaxWidth(Double.MAX_VALUE);
        tempLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(tempLabel, Priority.ALWAYS);
        text.getChildren().add(tempLabel);
    }

    private Slider addAndReturnSlider(int min, int max, int def, HBox sliders) {
        Slider tempSlider = new Slider(min, max, def);
        HBox.setHgrow(tempSlider, Priority.ALWAYS);
        setSliderTicks(tempSlider);
        sliders.getChildren().add(tempSlider);
        return tempSlider;
    }

    private void setSliderTicks(Slider slider) {
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }
}
