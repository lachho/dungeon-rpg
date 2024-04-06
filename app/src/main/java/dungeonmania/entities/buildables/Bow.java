package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Weapon;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Bow extends Entity implements Buildable, Weapon {
    private int durability;

    public Bow(int durability) {
        super(null);
        this.durability = durability;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.removePlayerInventoryItem(this);
        }
    }

    @Override
    public void applyBuff(BattleStatistics origin) {

    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        return inventory.count(Wood.class) >= 1 && inventory.count(Arrow.class) >= 3;
    }

    @Override
    public Buildable build(EntityFactory factory, Inventory inventory) {
        remove(inventory);
        return factory.buildBow();
    }

    @Override
    public boolean remove(Inventory inventory) {
        if (!checkBuildCriteria(inventory)) {
            return false;
        }

        inventory.removeFirst(Wood.class);
        inventory.removeMultiple(Arrow.class, 3);

        return true;
    }

    @Override
    public String getName() {
        return "bow";
    }
}
