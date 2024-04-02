package dungeonmania.entities;

import dungeonmania.Game;

public interface Moveable {
  public void onMovedAway(Game game, Entity entity);
}
