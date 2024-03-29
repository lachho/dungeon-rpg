package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveMercenary implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        // FIXME - demeter
        Position nextPos = map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
        game.moveTo(enemy, nextPos);
    }
}
