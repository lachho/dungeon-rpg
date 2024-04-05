package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.playerState.InvincibleState;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvincibilityPotion(Position position, int duration) {
        super(position, duration);
    }

    public void applyBuff(BattleStatistics origin) {
        origin.setInvincible(true);
    }

    @Override
    public PlayerState createState() {
        return new InvincibleState(this);
    }
}
