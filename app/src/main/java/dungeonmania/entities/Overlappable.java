package dungeonmania.entities;

import dungeonmania.Game;

public interface Overlappable {
  public void onOverlap(Game game, Entity entity);
}
