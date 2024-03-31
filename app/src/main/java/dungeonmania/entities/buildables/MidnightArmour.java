package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.collectables.Weapon;
import dungeonmania.entities.inventory.Inventory;

public class MidnightArmour extends Entity implements Buildable, Weapon {
    private double attack;
    private double defence;

    public MidnightArmour(double attack, double defence) {
        super(null);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyBuff'");
    }

    @Override
    public void use(Game game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'use'");
    }

    @Override
    public int getDurability() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDurability'");
    }

    @Override
    public boolean checkBuildCriteria(Inventory inventory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkBuildCriteria'");
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
