package ooga.view.application.games;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameDataManager  {
  public static void save(Serializable data, String fName) {
    try (ObjectOutputStream gameStream
        = new ObjectOutputStream(Files.newOutputStream(Paths.get(fName)))) {
      gameStream.writeObject(data);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public static Object load(String fName) {
    try(ObjectInputStream gameStream
        = new ObjectInputStream(Files.newInputStream(Paths.get(fName)))) {
      return gameStream.readObject();
    } catch(IOException | ClassNotFoundException e) {
      System.out.println("problem with data");
      return null;
    }
  }

}
