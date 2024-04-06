package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logical.LogicalBomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Entity implements Moveable, Overlappable {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().filter(b -> !(b instanceof LogicalBomb)).forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    // FIXME - demeter
    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            game.getMap().bfsLogicalEntities(getPosition(), true, game.getTick(), false);
            game.getMap().evaluateAllLogicalEntities();
            bombs.stream().filter(b -> !(b instanceof LogicalBomb)).forEach(b -> b.notify(game.getMap()));
        }
    }

    public void onMovedAway(Game game, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            game.getMap().bfsLogicalEntities(getPosition(), false, game.getTick(), false);
            game.getMap().evaluateAllLogicalEntities();
        }
    }

    public boolean isActivated() {
        return activated;
    }
}
