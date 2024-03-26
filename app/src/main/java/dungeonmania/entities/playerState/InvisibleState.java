package dungeonmania.entities.playerState;

import dungeonmania.entities.collectables.potions.Potion;

public class InvisibleState extends PlayerState {
    public InvisibleState(Potion potion) {
        super(potion);
    }

    @Override
    public String getState() {
        return "Invisible";
    }


//     @Override
//     public void transitionBase() {
//         Player player = getPlayer();
//         player.changeState(new BaseState(player));
//     }

//     @Override
//     public void transitionInvincible() {
//         Player player = getPlayer();
//         player.changeState(new InvincibleState(player));
//     }

//     @Override
//     public void transitionInvisible() {
//         Player player = getPlayer();
//         player.changeState(new InvisibleState(player));
//     }
}
