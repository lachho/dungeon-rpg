package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveAlly;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvincible;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveInvisibleAndZombieToast;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveMercenary;
import dungeonmania.entities.enemies.enemyMoveStrategy.MoveStrategy;
import dungeonmania.map.GameMap;
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

    private int endMindControl = 0;

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
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    private boolean withinRadius(Position player) {
        int x = Math.abs(getPosition().getX() - player.getX());
        int y = Math.abs(getPosition().getY() - player.getY());

        return x <= bribeRadius && y <= bribeRadius;
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return withinRadius(player.getPosition()) && player.countEntityOfType(Treasure.class) >= bribeAmount;
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
        if (player.containsEntityOfType(Sceptre.class)) {
            endMindControl = game.getTick() + player.mindControlDuration();
        } else {
            bribe(player);
        }
        if (!isAdjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            isAdjacentToPlayer = true;
    }

    public void onTick(int tick) {
        if (tick == endMindControl && allied) {
            allied = false;
        }
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
        return !allied && (canBeBribed(player)) || player.containsEntityOfType(Sceptre.class);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return super.getBattleStatistics();
    }

    public void applyBuff(BattleStatistics origin) {
        origin.addAttack(allyAttack);
        origin.addDefence(allyDefence);
    }
}
