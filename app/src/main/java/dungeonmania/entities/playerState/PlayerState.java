package dungeonmania.entities.playerState;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.potions.Potion;

public abstract class PlayerState {
    private Potion potion;

    PlayerState(Potion potion) {
        this.potion = potion;
    }

    public Potion getPotion() {
        return potion;
    }

    public abstract String getState();

    public void applyBuff(BattleStatistics origin) {
        potion.applyBuff(origin);
    }
}
