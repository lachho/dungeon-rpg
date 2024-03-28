package dungeonmania.entities.enemies.enemyMoveStrategy;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Boulder;
import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveSpider implements MoveStrategy {
    @Override
    public void move(Game game, Enemy enemy) {
        Spider spider = (Spider) enemy;
        GameMap map = game.getMap();

        Position nextPos = spider.getNextPosition();
        List<Entity> entities = game.getEntities(nextPos);

        if (isBoulderAtPosition(game, nextPos, entities)) {
            spider.setForward(!spider.isForward());
            spider.updateNextPosition();
            spider.updateNextPosition();
        }

        nextPos = spider.getNextPosition();
        entities = game.getEntities(nextPos);

        if (isValidMovePosition(game, nextPos, entities, spider)) {
            // FIXME - demeter
            map.moveTo(enemy, nextPos);
            spider.updateNextPosition();
        }
    }

    private boolean isBoulderAtPosition(Game game, Position position, List<Entity> entities) {
        return entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder);
    }

    private boolean isValidMovePosition(Game game, Position position, List<Entity> entities, Spider spider) {
        return entities == null || entities.size() == 0
                || entities.stream().allMatch(e -> e.canMoveOnto(game.getMap(), spider));
    }
}
