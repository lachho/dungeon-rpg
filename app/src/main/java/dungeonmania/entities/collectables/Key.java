package dungeonmania.entities.collectables;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class Key extends Collectables implements SpecialCraftable {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    public int getnumber() {
        return number;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Player && !((Player) entity).containsEntityOfType(Key.class)) {
            super.onOverlap(game, entity);
        }
    }
}
