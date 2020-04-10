package ooga.controller;

import java.util.List;

public interface Controller {

  void addEntity(EntityWrapper newEntity);

  List<EntityWrapper> getEntityList();
}
