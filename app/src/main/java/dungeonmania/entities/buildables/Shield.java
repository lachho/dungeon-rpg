package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class Shield extends Entity implements Buildable {
    private int durability;
    private double defence;

    public Shield() {
        super(null);
    }

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
        // return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, 1, 1));
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        return inventory.count(Wood.class) >= 2
        && (inventory.count(Treasure.class) >= 1 || inventory.count(Key.class) >= 1);
    }

    @Override
    public Buildable build(EntityFactory factory) {
        return factory.buildShield();
    }

    @Override
    public boolean remove(Inventory inventory) {
        if (!checkBuildCriteria(inventory)) {
            return false;
        }

        inventory.removeMultiple(Wood.class, 2);
        if (!inventory.removeFirst(Treasure.class)) inventory.removeFirst(Key.class);

        return true;
    }

    @Override
    public String getName() {
        return "shield";
    }

}
