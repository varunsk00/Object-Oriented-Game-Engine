package ooga.util.config;

import java.io.File;
import java.io.IOException;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLReader {
  public static final String ERROR_MESSAGE = "XML file does not represent %s";
  public static final String CORRUPTED_FIELD = "XML file has corrupted/missing fields";
  public static final String INCORRECT_DATATYPE = "XML file has incorrect data type";

  private final String TYPE_ATTRIBUTE;
  private final DocumentBuilder DOCUMENT_BUILDER;

  private String playerName;
  private String gameType;
  private int numLevels;
  private int numLives;
  private String gameConfig;



  public XMLReader(String type) {
    DOCUMENT_BUILDER = getDocumentBuilder();
    TYPE_ATTRIBUTE = type;
  }

  public Object getMainView(String fname) {
    File dataFile = new File(fname);
    Element root = getRootElement(dataFile);
    if(! isValidFile(root, "1")) { //this.DATA_TYPE);) {
      throw new XMLException(ERROR_MESSAGE, 1); //this.DATA_TYPE);
    }
    readBasic(root);
    return null;
  }

  private void readBasic(Element root) {
    try {

    }
    catch (NumberFormatException e) {
      throw new XMLException(INCORRECT_DATATYPE, 1); //this.DATA_TYPE);
    }
  }

  private Element getRootElement (File xmlFile) {
    try {
      DOCUMENT_BUILDER.reset();
      Document xmlDocument = DOCUMENT_BUILDER.parse(xmlFile);
      return xmlDocument.getDocumentElement();
    }
    catch (SAXException | IOException e) {
      throw new XMLException(e);
    }
  }

  private String getTextValue (Element e, String tagName) {
    NodeList nodeList = e.getElementsByTagName(tagName);
    if (nodeList != null && nodeList.getLength() > 0 && !nodeList.item(0).getTextContent().equals("")) {
      return nodeList.item(0).getTextContent();
    }
    else {
      throw new XMLException(CORRUPTED_FIELD + ": " + tagName, 1); // this.DATA_TYPE);
    }
  }

  private String getAttribute (Element e, String attributeName) {
    return e.getAttribute(attributeName);
  }

  private boolean isValidFile (Element root, String type) {
    return getAttribute(root, TYPE_ATTRIBUTE).equals(type);
  }

  // boilerplate code needed to make a documentBuilder
  private DocumentBuilder getDocumentBuilder()  {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }
    catch (ParserConfigurationException e) {
      throw new XMLException(e);
    }
  }

}
