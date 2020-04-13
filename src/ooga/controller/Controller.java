package ooga.controller;

import javafx.scene.Node;

import java.util.List;

public interface Controller {

  void addEntity(EntityWrapper newEntity);

  List<EntityWrapper> getEntityList();

  void removeEntity(EntityWrapper node);
}
