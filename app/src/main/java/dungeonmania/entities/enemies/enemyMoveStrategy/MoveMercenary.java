package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Position;

public class MoveMercenary implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        Position nextPos = game.mapDijkstraPathFind(enemy.getPosition(), game.getPlayerPosition(), enemy);
        game.moveTo(enemy, nextPos);
    }
}
