package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;

public interface MoveStrategy {
    public void move(Game game, Enemy enemy);
}
