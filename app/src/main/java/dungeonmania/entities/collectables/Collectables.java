package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.Game;
import dungeonmania.util.Position;

public class Collectables extends Entity implements InventoryItem, Overlappable {
  public Collectables(Position position) {
    super(position);
  }

  public void onOverlap(Game game, Entity entity) {
    if (entity instanceof Player) {
      if (!((Player) entity).pickUp(this))
        return;
      game.destroyEntity(this);
    }
  }
}
