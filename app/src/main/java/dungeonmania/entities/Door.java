package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.Game;

import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.util.Position;

public class Door extends Entity implements Overlappable {
    private boolean open = false;
    private int number;

    public Door(Position position, int number) {
        super(position.asLayer(Entity.DOOR_LAYER));
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (open || entity instanceof Spider) {
            return true;
        }

        if (entity instanceof Player) {
            Player player = (Player) entity;
            return player.hasKey(number) || player.containsEntityOfType(SunStone.class);
        }

        return false;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (!(entity instanceof Player))
            return;

        Player player = (Player) entity;

        if (player.hasKey(number)) {
            player.use(Key.class);
            open();
        } else if (player.containsEntityOfType(SunStone.class)) {
            open();
        }
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

}
