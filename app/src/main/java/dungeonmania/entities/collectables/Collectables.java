package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Collectables extends Entity implements InventoryItem, Overlappable {
    public Collectables(Position position) {
        super(position);
    }

    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
        if (!((Player) entity).pickUp(this))
            return;
        map.destroyEntity(this);
        }
    }
}
