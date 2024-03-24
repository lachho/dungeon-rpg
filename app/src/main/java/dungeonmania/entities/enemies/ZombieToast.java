package dungeonmania.entities.enemies;

// import java.util.Random;
// import java.util.stream.Collectors;
// import java.util.List;
// import dungeonmania.entities.collectables.potions.InvisibilityPotion;
// import dungeonmania.entities.enemies.enemyMoveStrategy.MoveAlly;
// import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvisible;
// import dungeonmania.util.Direction;
// import dungeonmania.map.GameMap;
// import dungeonmania.entities.enemies.enemyMoveStrategy.MoveMercenary;
import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvincible;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveStrategy;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveZombieToast;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;
    // private Random randGen = new Random();
    private MoveStrategy moveStrategy;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public void move(Game game) {
        if (game.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            moveStrategy = new MoveInvincible();
        } else {
            moveStrategy = new MoveZombieToast();
        }

        moveStrategy.move(game, this);
        // Position nextPos;
        // GameMap map = game.getMap();
        // if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
        //     Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), getPosition());

        //     Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(getPosition(), Direction.RIGHT)
        //             : Position.translateBy(getPosition(), Direction.LEFT);
        //     Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(getPosition(), Direction.UP)
        //             : Position.translateBy(getPosition(), Direction.DOWN);
        //     Position offset = getPosition();
        //     if (plrDiff.getY() == 0 && map.canMoveTo(this, moveX))
        //         offset = moveX;
        //     else if (plrDiff.getX() == 0 && map.canMoveTo(this, moveY))
        //         offset = moveY;
        //     else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
        //         if (map.canMoveTo(this, moveX))
        //             offset = moveX;
        //         else if (map.canMoveTo(this, moveY))
        //             offset = moveY;
        //         else
        //             offset = getPosition();
        //     } else {
        //         if (map.canMoveTo(this, moveY))
        //             offset = moveY;
        //         else if (map.canMoveTo(this, moveX))
        //             offset = moveX;
        //         else
        //             offset = getPosition();
        //     }
        //     nextPos = offset;
        // } else {
        //     List<Position> pos = getPosition().getCardinallyAdjacentPositions();
        //     pos = pos.stream().filter(p -> map.canMoveTo(this, p)).collect(Collectors.toList());
        //     if (pos.size() == 0) {
        //         nextPos = getPosition();
        //     } else {
        //         nextPos = pos.get(randGen.nextInt(pos.size()));
        //     }
        // }
        // game.getMap().moveTo(this, nextPos);
    }

}
