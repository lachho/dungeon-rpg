package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Collectables extends Entity implements InventoryItem {
  public Collectables(Position position) {
    super(position);
  }

  @Override
  public void onOverlap(GameMap map, Entity entity) {
    if (entity instanceof Player) {
      if (!((Player) entity).pickUp(this))
        return;
      map.destroyEntity(this);
    }
  }

  // FIXME - Collectables must implement the abstract methods in Entity
  // This will need refactoring in part c - inheritance design
  public void onDestroy(GameMap gameMap) {
  }

  public void onMovedAway(GameMap map, Entity entity) {
  }

}
