package dungeonmania.entities.playerState;

import dungeonmania.entities.collectables.potions.Potion;

public abstract class PlayerState {
    // private Player player;
    private Potion potion;
    // private boolean isInvincible = false;
    // private boolean isInvisible = false;

    PlayerState(Potion potion) {
        // this.player = player;
        this.potion = potion;
        // this.isInvincible = isInvincible;
        // this.isInvisible = isInvisible;
    }

    // public boolean isInvincible() {
    //     return isInvincible;
    // };

    // public boolean isInvisible() {
    //     return isInvisible;
    // };

    // public Player getPlayer() {
    //     return player;
    // }

    public Potion getPotion() {
        return potion;
    }

    public abstract String getState();

    // public abstract void transitionInvisible();

    // public abstract void transitionInvincible();

    // public abstract void transitionBase();
}
