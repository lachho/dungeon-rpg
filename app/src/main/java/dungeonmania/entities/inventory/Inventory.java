package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Game;
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
        return buildables.stream()
            .filter(buildable -> buildable.checkBuildCriteria(this))
            .map(Buildable::getName)
            .collect(Collectors.toList());

        // int wood = count(Wood.class);
        // int arrows = count(Arrow.class);
        // int treasure = count(Treasure.class);
        // int keys = count(Key.class);
        // List<String> result = new ArrayList<>();

        // if (wood >= 1 && arrows >= 3) {
        //     result.add("bow");
        // }
        // if (wood >= 2 && (treasure >= 1 || keys >= 1)) {
        //     result.add("shield");
        // }
        // return result;
    }

    public InventoryItem build(String type, EntityFactory factory) {
        return buildables.stream()
            .filter(item -> type.equals(item.getName()) && item.checkBuildCriteria(this))
            .findFirst()
            .map(item -> {
                item.remove(this);
                return item.build(factory);
            })
            .orElse(null);

        // List<Wood> wood = getEntities(Wood.class);
        // List<Arrow> arrows = getEntities(Arrow.class);
        // List<Treasure> treasure = getEntities(Treasure.class);
        // List<Key> keys = getEntities(Key.class);

        // if (wood.size() >= 1 && arrows.size() >= 3 && !forceShield) {
        //     if (remove) {
        //         items.remove(wood.get(0));
        //         items.remove(arrows.get(0));
        //         items.remove(arrows.get(1));
        //         items.remove(arrows.get(2));
        //     }
        //     return factory.buildBow();

        // } else if (wood.size() >= 2 && (treasure.size() >= 1 || keys.size() >= 1)) {
        //     if (remove) {
        //         items.remove(wood.get(0));
        //         items.remove(wood.get(1));
        //         if (treasure.size() >= 1) {
        //             items.remove(treasure.get(0));
        //         } else {
        //             items.remove(keys.get(0));
        //         }
        //     }
        //     return factory.buildShield();
        // }
        // return null;
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
        // return getFirst(Weapon.class)  null || getFirst(Bow.class) != null;
    }

    // FIXME- getWeapon()/useWeapon() is only used in ZombieToastSpawner - bad inheritance
    public BattleItem getWeapon() {
        return getFirst(Weapon.class);
        // BattleItem weapon = getFirst(Sword.class);
        // if (weapon == null)
        //     return getFirst(Bow.class);
        // return weapon;
    }

    public void useWeapon(Game game) {
        getWeapon().use(game);
    }

}
