package dungeonmania.entities.buildables;

import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;

public interface Buildable extends InventoryItem {
    public abstract boolean checkBuildCriteria(Inventory inventory);

    public abstract Buildable build(EntityFactory factory, Inventory inventory);

    public abstract boolean remove(Inventory inventory);

    public abstract String getName();
}
