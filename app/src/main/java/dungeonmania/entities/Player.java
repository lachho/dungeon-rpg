package dungeonmania.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.TreasureCount;
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
    private int nextPotionTick = 0;

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

    public BattleItem getWeapon() {
        return inventory.getWeapon();
    }

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

    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy) {
            if (entity instanceof Mercenary) {
                if (((Mercenary) entity).isAllied())
                    return;
            }
            map.getGame().battle(this, (Enemy) entity);
        }
    }

    public Entity getEntity(String itemUsedId) {
        return inventory.getEntity(itemUsedId);
    }

    public boolean pickUp(Entity item) {
        if (item instanceof TreasureCount)
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

    public void nextPotion(int currentTick) {
        if (queue.isEmpty()) {
            changeState(new BaseState());
            return;
        }
        Potion potion = queue.remove();
        changeState(potion.createState());

        nextPotionTick = currentTick + potion.getDuration();
    }

    public void changeState(PlayerState playerState) {
        state = playerState;
    }

    public void addPotion(Potion potion, int tick) {
        queue.add(potion);
        if (getState().equals("Base")) {
            nextPotion(tick);
        }
    }

    public void onTick(int tick) {
        if (getState().equals("Base") || tick == nextPotionTick) {
            nextPotion(tick);
        }
    }

    public int mindControlDuration() {
        return inventory.mindControlDuration();
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

    public <T extends InventoryItem> boolean containsEntityOfType(Class<T> itemType) {
        return countEntityOfType(itemType) >= 1;
    }

    public void applyBuff(BattleStatistics origin) {
        if (!getState().equals("Base"))
            state.applyBuff(origin);
    }

    public String getState() {
        return state.getState();
    }

    public boolean hasKey(int lockNumber) {
        Key key = inventory.getFirst(Key.class);
        return (key != null && key.getnumber() == lockNumber);
    }

}
