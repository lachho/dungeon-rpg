package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.inventory.Inventory;

public class Sceptre extends Entity implements Buildable {
    private int durability;

    public Sceptre(int durability) {
        super(null);
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyBuff'");
    }

    @Override
    public void use(Game game) {
        durability--;
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
        return "sceptre";
    }

}
