package dungeonmania.entities.enemies;

// import java.util.List;
// import java.util.Random;
// import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
// import dungeonmania.entities.collectables.potions.InvincibilityPotion;
// import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveAlly;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvincible;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvisibleAndZombieToast;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveMercenary;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveStrategy;
// import dungeonmania.map.GameMap;
// import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean isAdjacentToPlayer = false;
    private MoveStrategy moveStrategy;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }

    public boolean isAdjacentToPlayer() {
        return isAdjacentToPlayer;
    }

    public void setAdjacentToPlayer(boolean isAdjacentToPlayer) {
        this.isAdjacentToPlayer = isAdjacentToPlayer;
    }

    public boolean isAllied() {
        return allied;
    }

    @Override
    public void onOverlap(Game game, Entity entity) {
        if (allied)
            return;
        super.onOverlap(game, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    @Override
    public void move(Game game) {

        if (allied) {
            moveStrategy = new MoveAlly();
        } else if (game.getPlayer().getState().equals("Invisible")) {
            moveStrategy = new MoveInvisibleAndZombieToast();
        } else if (game.getPlayer().getState().equals("Invincible")) {
            moveStrategy = new MoveInvincible();
        } else {
            moveStrategy = new MoveMercenary();
        }
        moveStrategy.move(game, this);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }
}
