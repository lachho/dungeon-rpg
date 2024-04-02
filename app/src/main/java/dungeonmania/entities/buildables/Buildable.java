package dungeonmania.entities.buildables;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.inventory.Inventory;

public interface Buildable extends BattleItem {
    public abstract boolean checkBuildCriteria(Inventory inventory);

    public abstract Buildable build(EntityFactory factory, Inventory inventory);

    public abstract boolean remove(Inventory inventory);

    public abstract String getName();
}
