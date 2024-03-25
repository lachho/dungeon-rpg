package dungeonmania.entities.enemies.enemyMoveStrategy;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveInvisibleAndZombieToast implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        GameMap map = game.getMap();
        // Move random
        Random randGen = new Random();
        List<Position> cardinallyAdjacentPositions = enemy.getCardinallyAdjacentPositions();
        Position nextPos;

        List<Position> validMoves = cardinallyAdjacentPositions.stream().filter(p -> map.canMoveTo(enemy, p))
                .collect(Collectors.toList());

        if (validMoves.size() == 0) {
            nextPos = enemy.getPosition();
        } else {
            nextPos = validMoves.get(randGen.nextInt(validMoves.size()));
        }

        game.moveTo(enemy, nextPos);
    }
}
