package ooga.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import ooga.model.actions.Action;
import ooga.model.controlschemes.ControlScheme;
import ooga.util.config.XMLException;
import ooga.view.application.TestSandboxBlue;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GameParser {
  private static final String TXT_FILEPATH = "src/resources/";
  private static final String IMG_FILEPATH = "resources/";
  private static final String PACKAGE_PREFIX_NAME = "ooga.model.";
  private static final String ACTIONS_PREFIX = PACKAGE_PREFIX_NAME + "actions.";
  private static final String CONTROLS_PREFIX = PACKAGE_PREFIX_NAME + "controlschemes.";
  public static final String CORRUPTED_FILE = "Error with file input. Check game file or choose another game.";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public final String CLASS_NOT_FOUND = "Game not valid";

  private File myFile;
  private Document myDoc;


  public GameParser (String entityName){
    myFile = new File(TXT_FILEPATH + "properties/" + entityName + ".xml");
    setupDocument();
  }


  private void setupDocument() { //FIXME streamline into API?
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = null;
    try {
      builder = factory.newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLException(CORRUPTED_FILE);
    }
    try {
      myDoc = builder.parse(myFile);
    } catch (SAXException | IOException e) {
      throw new XMLException(CORRUPTED_FIELD);
    }
    myDoc.getDocumentElement().normalize();
  }

  public TestSandboxBlue parseGameConfig() {
    NodeList controls = myDoc.getElementsByTagName("Controls");
    Node controlNode = controls.item(0);

    TestSandboxBlue temp = new TestSandboxBlue();
    String controlType = "NoControls"; //FIXME magic number
    if(controlNode.getNodeType() == Node.ELEMENT_NODE){
      Element controlElement = (Element) controlNode;
      controlMap = readControlMap(controlElement);
      controlType = readControlScheme(controlElement);
    }
}
