package ooga.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import ooga.model.actions.Action;
import ooga.model.actions.NoAction;
import ooga.model.controlschemes.ControlScheme;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EntityParser {
  private static final String TXT_FILEPATH = "/src/resources/";
  private File myFile;
  private Document myDoc;
  private Stage myStage;


  public EntityParser (String entityName){
    myFile = new File(TXT_FILEPATH + entityName);
    setupDocument();
  }

  private void setupDocument() {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      //FIXME add error handling
    }
    try {
      myDoc = builder.parse(myFile);
    } catch (SAXException | IOException e) {
      //FIXME add error handling
    }
    myDoc.getDocumentElement().normalize();
  }

  public ControlScheme parseControls(){
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
      controlClass = Class.forName(controlType);
    } catch (ClassNotFoundException e) {
      //FIXME add error handling
    }

    ControlScheme myScheme = null;

    try{
      myScheme = (ControlScheme) (controlClass.getConstructor(Map.class)
          .newInstance(controlMap));
    } catch (InstantiationException e) {
      //FIXME add error handling
    } catch (InvocationTargetException e) {
      //FIXME add error handling
    } catch (NoSuchMethodException e) {
      //FIXME add error handling
    } catch (IllegalAccessException e) {
      //FIXME add error handling
    }

    return myScheme;
  }

  private Map<String, Action> readControlMap(Element controlElement) {
    NodeList controls = controlElement.getElementsByTagName("Control");
    Map<String, Action> controlMap = new HashMap<String, Action>();
    for(int i = 0; i < controls.getLength(); i++){
      Node control = controls.item(i);
      if(control.getNodeType() == Node.ELEMENT_NODE){
        Element crlElement = (Element)control;
        String key = crlElement.getAttribute("id");

        Class controlAction = null;
        try{
          String actionName = crlElement.getAttribute("move");
          controlAction = Class.forName(actionName);
        } catch (ClassNotFoundException e) {
          //FIXME add error handling
        }

        Action action = new NoAction();
        try{
          action = (Action) (controlAction.getConstructor(Action.class)
              .newInstance(crlElement.getAttribute("param")));
        } catch (InstantiationException e) {
          //FIXME add error handling
        } catch (InvocationTargetException e) {
          //FIXME add error handling
        } catch (NoSuchMethodException e) {
          //FIXME add error handling
        } catch (IllegalAccessException e) {
          //FIXME add error handling
        }
        controlMap.put(key, action);

      }
    }
    return controlMap;
  }

  private String readControlScheme(Element controlElement) {
    NodeList controlSchema = controlElement.getElementsByTagName("Scheme");
    Node scheme = controlSchema.item(0);
    if(scheme.getNodeType() == Node.ELEMENT_NODE){
      return(((Element)scheme).getAttribute("name"));
    }
    return "NoControls";
  }

  public static ImageView generateImage(){return null;}
}
