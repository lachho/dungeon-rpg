package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveAlly implements MoveStrategy {

    @Override
    public void move(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        Mercenary mercenary = (Mercenary) enemy;
        Position nextPos = mercenary.isAdjacentToPlayer() ? player.getPreviousDistinctPosition()
        : map.dijkstraPathFind(mercenary.getPosition(), player.getPosition(), mercenary);
        if (!mercenary.isAdjacentToPlayer() && Position.isAdjacent(player.getPosition(), nextPos))
            mercenary.setAdjacentToPlayer(true);
        map.moveTo(enemy, nextPos);
    }

}
