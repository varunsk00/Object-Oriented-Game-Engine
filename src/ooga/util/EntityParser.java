package ooga.util;

import java.io.File;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ooga.model.controlschemes.ControlScheme;
import org.w3c.dom.Document;

public class EntityParser {
  private static final String TXT_FILEPATH = "data/templates/";
  private File myFile;
  private Document myDoc;
  private Stage myStage;


  public EntityParser(String entityName){

  }

  public static ControlScheme parseControls(){return null;} //fixme implement parsing

  public static ImageView generateImage(){return null;}
}
