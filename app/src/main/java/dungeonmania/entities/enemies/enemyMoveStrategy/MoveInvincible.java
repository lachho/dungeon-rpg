package dungeonmania.entities.enemies.enemyMoveStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveInvincible implements MoveStrategy {

    @Override
    public void move(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        Position diff = Position.calculatePositionBetween(game.getPlayer().getPosition(), enemy.getPosition());

        Position nextPos = decideMoveDirection(map, enemy, diff);
        map.moveTo(enemy, nextPos);
    }

    private Position decideMoveDirection(GameMap map, Enemy enemy, Position diff) {
        Position current = enemy.getPosition();
        Direction horizontalDirection = tryHorizontalDirection(diff);
        Position horizontalMove = Position.translateBy(current, horizontalDirection);
        if (map.canMoveTo(enemy, horizontalMove)) {
            return horizontalMove;
        }

        Direction verticalDirection = tryVerticalDirection(diff);
        Position verticalMove = Position.translateBy(current, verticalDirection);
        if (map.canMoveTo(enemy, verticalMove)) {
            return verticalMove;
        }

        return current;
    }

    private Direction tryHorizontalDirection(Position diff) {
        if (Math.abs(diff.getX()) >= Math.abs(diff.getY())) {
            return diff.getX() >= 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return diff.getY() >= 0 ? Direction.UP : Direction.DOWN;
        }
    }

    private Direction tryVerticalDirection(Position diff) {
        if (Math.abs(diff.getX()) < Math.abs(diff.getY())) {
            return diff.getX() >= 0 ? Direction.RIGHT : Direction.LEFT;
        } else {
            return diff.getY() >= 0 ? Direction.UP : Direction.DOWN;
        }
    }

    //     Position moveX = (diff.getX() >= 0) ? Position.translateBy(enemy.getPosition(), Direction.RIGHT)
    //             : Position.translateBy(enemy.getPosition(), Direction.LEFT);
    //     Position moveY = (diff.getY() >= 0) ? Position.translateBy(enemy.getPosition(), Direction.UP)
    //             : Position.translateBy(enemy.getPosition(), Direction.DOWN);
    //     Position offset = enemy.getPosition();
    //     if (diff.getY() == 0 && map.canMoveTo(enemy, moveX))
    //         offset = moveX;
    //     else if (diff.getX() == 0 && map.canMoveTo(enemy, moveY))
    //         offset = moveY;
    //     else if (Math.abs(diff.getX()) >= Math.abs(diff.getY())) {
    //         if (map.canMoveTo(enemy, moveX))
    //             offset = moveX;
    //         else if (map.canMoveTo(enemy, moveY))
    //             offset = moveY;
    //         else
    //             offset = enemy.getPosition();
    //     } else {
    //         if (map.canMoveTo(enemy, moveY))
    //             offset = moveY;
    //         else if (map.canMoveTo(enemy, moveX))
    //             offset = moveX;
    //         else
    //             offset = enemy.getPosition();
    //     }
    //     map.moveTo(enemy, offset);
}
