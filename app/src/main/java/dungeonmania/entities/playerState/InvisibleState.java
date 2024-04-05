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
}
