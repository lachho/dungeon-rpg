package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.SpecialCraftable;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Shield extends Entity implements Buildable, BattleItem {
    private int durability;
    private double defence;

    public Shield(int durability, double defence) {
        super(null);
        this.durability = durability;
        this.defence = defence;
    }

    @Override
    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        origin.addDefence(defence);
    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        return inventory.count(Wood.class) >= 2
                && (inventory.count(SpecialCraftable.class) >= 1 || inventory.count(SunStone.class) >= 1);
    }

    @Override
    public Buildable build(EntityFactory factory, Inventory inventory) {
        remove(inventory);
        return factory.buildShield();
    }

    @Override
    public boolean remove(Inventory inventory) {
        if (!checkBuildCriteria(inventory)) {
            return false;
        }

        inventory.removeMultiple(Wood.class, 2);
        inventory.removeFirst(SpecialCraftable.class);
        // if (!inventory.removeFirst(Treasure.class))
        //     inventory.removeFirst(Key.class);

        return true;
    }

    @Override
    public String getName() {
        return "shield";
    }

}
