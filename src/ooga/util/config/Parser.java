package ooga.util.config;

import java.io.FileReader;
import java.io.IOException;
import ooga.model.controlschemes.controlSchemeExceptions.InvalidControlSchemeException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class Parser {
  protected String myFileName;

  public Parser() {
    myFileName = "";
  }

  public Object readJsonFile() {
    try {
      System.out.println(myFileName);
      FileReader reader = new FileReader(myFileName);
      JSONParser jsonParser = new JSONParser();
      return jsonParser.parse(reader);
    } catch (IOException | ParseException e) {
      throw new InvalidControlSchemeException(e);
    }
  }

  public void setMyFileName(String name) {
    myFileName = name;
  }

}
