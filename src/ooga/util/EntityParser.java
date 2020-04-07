package ooga.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import ooga.model.actions.Action;
import ooga.model.actions.ActionFactory;
import ooga.model.actions.CollisionKey;
import ooga.model.actions.NoAction;
import ooga.model.controlschemes.ControlScheme;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import ooga.util.config.XMLException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EntityParser {
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public final String CLASS_NOT_FOUND = "Control Scheme not valid";


  private File myFile;
  private Document myDoc;


  public EntityParser (String entityName){
    myFile = new File(TXT_FILEPATH + "properties/" + entityName + ".xml");
    setupDocument();
  }

  private void setupDocument() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(CORRUPTED_FIELD);
    }
    try {
      myDoc = builder.parse(myFile);
    } catch (SAXException | IOException e) {
      throw new XMLException(CORRUPTED_FIELD);
    }
    myDoc.getDocumentElement().normalize();
  }

  public ControlScheme parseControls() {
    NodeList controls = myDoc.getElementsByTagName("Controls");
    Node controlNode = controls.item(0);

    Map<String, Action> controlMap = new HashMap<String, Action>();
    String controlType = "NoControls"; //FIXME magic number
    if(controlNode.getNodeType() == Node.ELEMENT_NODE){
      Element controlElement = (Element) controlNode;
      controlMap = readControlMap(controlElement);
      controlType = readControlScheme(controlElement);
    }

    Class controlClass = null;
    try{
      controlClass = Class.forName(CONTROLS_PREFIX + controlType);
    } catch (ClassNotFoundException e) {
      throw new InvalidControlSchemeException(CLASS_NOT_FOUND);
    }

    ControlScheme myScheme = null;

    try{
      myScheme = (ControlScheme) (controlClass.getConstructor(Map.class)
          .newInstance(controlMap));
    } catch (InstantiationException e) {
      throw new InvalidControlSchemeException(CLASS_NOT_FOUND);
    } catch (InvocationTargetException e) {
      throw new InvalidControlSchemeException(CLASS_NOT_FOUND);
    } catch (NoSuchMethodException e) {
      throw new InvalidControlSchemeException(CLASS_NOT_FOUND);
    } catch (IllegalAccessException e) {
      throw new InvalidControlSchemeException(CLASS_NOT_FOUND);
    }

    return myScheme;
  }

  public Map<CollisionKey, Action> parseCollisions() {
    NodeList controls = myDoc.getElementsByTagName("Collisions");
    Node controlNode = controls.item(0);

    Map<CollisionKey, Action> collisionMap = new HashMap<CollisionKey, Action>();
    String controlType = ""; //FIXME magic number

    if(controlNode.getNodeType() == Node.ELEMENT_NODE){
      Element controlElement = (Element) controlNode;
      collisionMap = readCollisionMap(controlElement);
    }
    return collisionMap;
  }

  private Map<CollisionKey, Action> readCollisionMap(Element controlElement) {
    NodeList collisions = controlElement.getElementsByTagName("Collision");
    Map<CollisionKey, Action> collisionMap = new HashMap<CollisionKey, Action>();

    //FIXME refactor into one method to reduce code reuse (parseCollision+parseControls)
    for(int i = 0; i < collisions.getLength(); i++) {
      Node collision = collisions.item(i);
      if(collision.getNodeType() == Node.ELEMENT_NODE) {
        Element crlElement = (Element) collision;
        String key = crlElement.getAttribute("id");

        ActionFactory actionFactory = new ActionFactory();
        String actionName = crlElement.getAttribute("action");
        String paramName = crlElement.getAttribute("param");

        Action testAction = actionFactory.makeAction(actionName, paramName);

        String orientation = crlElement.getAttribute("orientation");
        collisionMap.put(new CollisionKey(key, orientation), testAction);

      }
    }
    return collisionMap;
  }

  private Map<String, Action> readControlMap(Element controlElement) {
    NodeList controls = controlElement.getElementsByTagName("Control");
    Map<String, Action> controlMap = new HashMap<String, Action>();
    for(int i = 0; i < controls.getLength(); i++){
      Node control = controls.item(i);
      if(control.getNodeType() == Node.ELEMENT_NODE){
        Element crlElement = (Element)control;
        String key = crlElement.getAttribute("id");

        ActionFactory actionFactory = new ActionFactory();
        String actionName = crlElement.getAttribute("action");
        String paramName = crlElement.getAttribute("param");
        Action testAction = actionFactory.makeAction(actionName, paramName);

        controlMap.put(key, testAction);
      }
    }
    return controlMap;
  }

  private String readControlScheme(Element controlElement) {
    NodeList controlSchema = controlElement.getElementsByTagName("Scheme");
    Node scheme = controlSchema.item(0);
    if(scheme.getNodeType() == Node.ELEMENT_NODE){
      return ((Element)scheme).getAttribute("name");
    }
    return "NoControls";
  }

  public ImageView generateImage(){
    NodeList info = myDoc.getElementsByTagName("Info");
    Node infoNode = info.item(0);
    ImageView output = null; //FIXME set default sprite

    if(infoNode.getNodeType() == Node.ELEMENT_NODE){
      Element infoElement = (Element)infoNode;
      output = loadImage(infoElement);
      output.setX(Double.parseDouble(getElementValue(infoElement, "XPos")));
      output.setY(Double.parseDouble(getElementValue(infoElement, "YPos")));
      output.setFitHeight(Double.parseDouble(getElementValue(infoElement, "Height")));
      output.setFitWidth(Double.parseDouble(getElementValue(infoElement, "Width")));
    }
    return output;
  }

  private ImageView loadImage(Element infoElement) {
    Image entityImage = new Image(this.getClass().getClassLoader()
        .getResourceAsStream(IMG_FILEPATH + getElementValue(infoElement, "Image") + ".png"));
    return new ImageView(entityImage);
  }

  private String getElementValue(Element element, String node){
    return element.getElementsByTagName(node).item(0).getTextContent();
  }
}
