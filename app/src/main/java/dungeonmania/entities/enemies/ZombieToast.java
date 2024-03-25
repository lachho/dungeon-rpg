package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvincible;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveStrategy;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvisibleAndZombieToast;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;
    private MoveStrategy moveStrategy;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
    }

    @Override
    public void move(Game game) {
        if (game.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            moveStrategy = new MoveInvincible();
        } else {
            moveStrategy = new MoveInvisibleAndZombieToast();
        }

        moveStrategy.move(game, this);
    }

}
