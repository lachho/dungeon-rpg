package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.Buildable;
import dungeonmania.entities.buildables.Shield;
import dungeonmania.entities.collectables.Weapon;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();
    private List<Buildable> buildables = new ArrayList<>();

    //TODO Update to include new buildables
    public Inventory() {
        buildables.add(new Bow());
        buildables.add(new Shield());
        // Add more criteria here as needed in stage 3
    }

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public <T extends InventoryItem> boolean removeFirst(Class<T> itemType) {
        T first = getFirst(itemType);
        if (first != null) {
            remove(first);
            return true;
        } else {
            return false;
        }
    }

    public <T extends InventoryItem> boolean removeMultiple(Class<T> itemType, int count) {
        for (int i = 0; i < count; i++) {
            if (!removeFirst(itemType)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getBuildables() {
        return buildables.stream().filter(buildable -> buildable.checkBuildCriteria(this)).map(Buildable::getName)
                .collect(Collectors.toList());
    }

    public InventoryItem build(String type, EntityFactory factory) {
        return buildables.stream().filter(item -> type.equals(item.getName()) && item.checkBuildCriteria(this))
                .findFirst().map(item -> {
                    item.remove(this);
                    return item.build(factory);
                }).orElse(null);
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Weapon.class) != null;
    }

    public BattleItem getWeapon() {
        return getFirst(Weapon.class);
    }

}
