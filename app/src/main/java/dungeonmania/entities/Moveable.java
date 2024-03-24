package dungeonmania.entities;

import dungeonmania.map.GameMap;

public interface Moveable {
  public void onMovedAway(GameMap map, Entity entity);
}
