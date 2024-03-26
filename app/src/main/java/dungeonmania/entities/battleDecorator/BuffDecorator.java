package dungeonmania.entities.battleDecorator;

import dungeonmania.battles.Battleable;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public abstract class BuffDecorator implements Battleable {
    private Battleable player;

    public BuffDecorator(Battleable player) {
        this.player = player;
    }

}
