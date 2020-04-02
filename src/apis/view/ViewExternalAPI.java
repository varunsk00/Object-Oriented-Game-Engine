package apis.view;

public interface ViewExternalAPI {
  void updateEntityPosition(int id, double newx, double newy);
  void removeEntity(int id);
  void addEntity();
}
