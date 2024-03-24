package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveAlly implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        Position playerPosition = game.getPlayerPosition();
        Mercenary mercenary = (Mercenary) enemy;

        Position nextPos = getNextPosition(mercenary, game, map, playerPosition);

        if (!mercenary.isAdjacentToPlayer() && Position.isAdjacent(playerPosition, nextPos))
            mercenary.setAdjacentToPlayer(true);

        map.moveTo(enemy, nextPos);
    }

    private Position getNextPosition(Mercenary mercenary, Game game, GameMap map, Position playerPosition) {
        return mercenary.isAdjacentToPlayer() ? game.getPlayerPreviousPosition()
                : map.dijkstraPathFind(mercenary.getPosition(), playerPosition, mercenary);
    }

}
