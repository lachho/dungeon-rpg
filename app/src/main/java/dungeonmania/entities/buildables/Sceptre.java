package dungeonmania.entities.buildables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.SpecialCraftable;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Sceptre extends Entity implements Buildable {
    private int duration;

    public Sceptre(int duration) {
        super(null);
        this.duration = duration;
    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        return (inventory.count(Wood.class) >= 1 || inventory.count(Arrow.class) >= 2)
                && ((inventory.count(SpecialCraftable.class) >= 1 && inventory.count(SunStone.class) >= 1)
                        || inventory.count(SunStone.class) >= 2);
    }

    @Override
    public Buildable build(EntityFactory factory, Inventory inventory) {
        remove(inventory);
        return factory.buildSceptre();
    }

    @Override
    public boolean remove(Inventory inventory) {
        if (!checkBuildCriteria(inventory)) {
            return false;
        }

        if (!inventory.removeFirst(Wood.class))
            inventory.removeMultiple(Arrow.class, 2);
        inventory.removeFirst(SpecialCraftable.class);
        inventory.removeFirst(SunStone.class);

        return true;
    }

    @Override
    public String getName() {
        return "sceptre";
    }

    public int getDuration() {
        return duration;
    }

}
