package dungeonmania.entities.playerState;

import dungeonmania.entities.collectables.potions.Potion;

public class BaseState extends PlayerState {
    public BaseState(Potion potion) {
        super(potion);
    }

    @Override
    public String getState() {
        return "base";
    }

    // @Override
    // public void transitionBase() {
    //     // Do nothing
    // }

    // @Override
    // public void transitionInvincible() {
    //     Player player = getPlayer();
    //     player.changeState(new InvincibleState(player));
    // }

    // @Override
    // public void transitionInvisible() {
    //     Player player = getPlayer();
    //     player.changeState(new InvisibleState(player));
    // }
}
