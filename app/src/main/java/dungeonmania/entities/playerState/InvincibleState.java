package dungeonmania.entities.playerState;

import dungeonmania.entities.collectables.potions.Potion;

public class InvincibleState extends PlayerState {
    public InvincibleState(Potion potion) {
        super(potion);
    }

    @Override
    public String getState() {
        return "Invincible";
    }
}
