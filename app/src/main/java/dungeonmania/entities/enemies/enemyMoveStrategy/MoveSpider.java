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
        Position nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());
        List<Entity> entities = game.getMap().getEntities(nextPos);

        if (entities != null && entities.size() > 0 && entities.stream().anyMatch(e -> e instanceof Boulder)) {
            spider.setForward(!spider.isForward());
            spider.updateNextPosition();
            spider.updateNextPosition();
        }
        nextPos = spider.getMovementTrajectory().get(spider.getNextPositionElement());

        entities = game.getMap().getEntities(nextPos);
        if (entities == null || entities.size() == 0
                || entities.stream().allMatch(e -> e.canMoveOnto(game.getMap(), spider))) {
            map.moveTo(enemy, nextPos);
            spider.updateNextPosition();
        }
    }
}
