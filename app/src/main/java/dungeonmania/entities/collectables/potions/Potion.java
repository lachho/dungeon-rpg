package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
// import dungeonmania.Game;
// import dungeonmania.battles.BattleStatistics;
// import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.collectables.Collectables;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.util.Position;

public abstract class Potion extends Collectables {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    // TODO can we remove this?
    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    // @Override
    // public void use(Game game) {
    //     return;
    // }
    public int getDuration() {
        return duration;
    }

    // @Override
    public void applyBuff(BattleStatistics origin) {
        return;
    }

    // @Override
    // public int getDurability() {
    //     return 1;
    // }

    public abstract PlayerState createState();
}
