package dungeonmania.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.entities.inventory.Inventory;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.playerState.BaseState;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity implements Battleable, Overlappable {
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 5.0;
    private BattleStatistics battleStatistics;
    private Inventory inventory;
    private Queue<Potion> queue = new LinkedList<>();
    // private Potion inEffective = null;
    private int nextTrigger = 0;

    private int collectedTreasureCount = 0;

    private PlayerState state;

    public Player(Position position, double health, double attack) {
        super(position);
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_PLAYER_DAMAGE_REDUCER);
        inventory = new Inventory();
        state = new BaseState();
    }

    public int getCollectedTreasureCount() {
        return collectedTreasureCount;
    }

    public boolean hasWeapon() {
        return inventory.hasWeapon();
    }

    public void useWeapon(Game game) {
        inventory.useWeapon(game);
    }

    // unused
    // public BattleItem getWeapon() {
    //     return inventory.getWeapon();
    // }

    public List<String> getBuildables() {
        return inventory.getBuildables();
    }

    public boolean build(String entity, EntityFactory factory) {
        InventoryItem item = inventory.build(entity, factory);
        if (item == null)
            return false;
        return inventory.add(item);
    }

    public void move(GameMap map, Direction direction) {
        this.setFacing(direction);
        map.moveTo(this, Position.translateBy(this.getPosition(), direction));
    }

    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            game.battle(this, (Enemy) entity);
        }
    }

    public boolean pickUp(Entity item) {
        if (item instanceof Treasure)
            collectedTreasureCount++;
        return inventory.add((InventoryItem) item);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public <T extends InventoryItem> void use(Class<T> itemType) {
        T item = inventory.getFirst(itemType);
        if (item != null)
            inventory.remove(item);
    }

    public void use(Bomb bomb, GameMap map) {
        inventory.remove(bomb);
        bomb.onPutDown(map, getPosition());
    }

    public void triggerNext(int currentTick) {
        if (queue.isEmpty()) {
            // inEffective = null;
            changeState(new BaseState());
            return;
        }
        Potion potion = queue.remove();
        changeState(potion.createState());

        // if (queue.isEmpty()) {
        //     inEffective = null;
        //     state.transitionBase();
        //     return;
        // }
        // inEffective = queue.remove();
        // if (inEffective instanceof InvincibilityPotion) {
        //     state.transitionInvincible();
        // } else {
        //     state.transitionInvisible();
        // }
        nextTrigger = currentTick + potion.getDuration();
    }

    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    public void use(Potion potion, int tick) {
        inventory.remove(potion);
        queue.add(potion);
        if (getState().equals("Base")) {
            triggerNext(tick);
        }
    }

    public void onTick(int tick) {
        if (getState().equals("Base") || tick == nextTrigger) {
            triggerNext(tick);
        }
    }

    public void remove(InventoryItem item) {
        inventory.remove(item);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public double getBattleStatisticsHealth() {
        return battleStatistics.getHealth();
    }

    public void setBattleStatisticsHealth(double health) {
        battleStatistics.setHealth(health);
    }

    public <T extends InventoryItem> int countEntityOfType(Class<T> itemType) {
        return inventory.count(itemType);
    }

    public void applyBuff(BattleStatistics origin) {

        if (!getState().equals("Base"))
            state.applyBuff(origin);

        // if (state.isInvincible()) {
        //     return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, true, true));
        // } else if (state.isInvisible()) {
        //     return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, false, false));
        // }
    }

    @Override
    public void use(Game game) {
        return;
    }

    public String getState() {
        return state.getState();
    }

    public Entity getInventoryEntity(String itemUsedId) {
        return inventory.getEntity(itemUsedId);
    }

    public <T> List<T> getInventoryEntities(Class<T> clz) {
        return inventory.getEntities(clz);
    }
}
