package dungeonmania.entities;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.inventory.InventoryItem;

/**
 * Item has buff in battles
 */
public interface BattleItem extends InventoryItem {
    public void applyBuff(BattleStatistics origin);
    public void use(Game game);
    public int getDurability();
}
