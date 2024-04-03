package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveInvincible implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        Position enemyPosition = enemy.getPosition();
        Position plrDiff = Position.calculatePositionBetween(game.getPlayerPosition(), enemyPosition);

        Position moveX = calculateNextXPosition(plrDiff, enemyPosition);
        Position moveY = calculateNextYPosition(plrDiff, enemyPosition);

        if (game.canMoveTo(enemy, moveX))
            enemyPosition = moveX;
        else if (game.canMoveTo(enemy, moveY))
            enemyPosition = moveY;

        game.moveTo(enemy, enemyPosition);
    }

    private Position calculateNextXPosition(Position plrDiff, Position enemyPosition) {
        return (plrDiff.getX() >= 0) ? Position.translateBy(enemyPosition, Direction.RIGHT)
                : Position.translateBy(enemyPosition, Direction.LEFT);
    }

    private Position calculateNextYPosition(Position plrDiff, Position enemyPosition) {
        return (plrDiff.getY() >= 0) ? Position.translateBy(enemyPosition, Direction.UP)
                : Position.translateBy(enemyPosition, Direction.DOWN);
    }
}
