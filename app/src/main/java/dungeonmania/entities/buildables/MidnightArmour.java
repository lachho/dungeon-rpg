package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Weapon;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.inventory.Inventory;

public class MidnightArmour extends Entity implements Buildable, Weapon {
    private double attack;
    private double defence;
    private int durability = 1;

    public MidnightArmour(double attack, double defence) {
        super(null);
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        origin.addAttack(attack);
        origin.addDefence(defence);
    }

    @Override
    public void use(Game game) {
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        return inventory.count(Sword.class) >= 1 && (inventory.count(SunStone.class) >= 1);
    }

    @Override
    public Buildable build(EntityFactory factory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    @Override
    public boolean remove(Inventory inventory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

}
