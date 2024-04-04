package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.playerState.InvisibleState;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.util.Position;

public class InvisibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvisibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    public void applyBuff(BattleStatistics origin) {
        origin.setEnabled(false);
    }

    @Override
    public PlayerState createState() {
        return new InvisibleState(this);
    }

}
