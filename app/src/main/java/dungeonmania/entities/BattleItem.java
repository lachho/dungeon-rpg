package dungeonmania.entities;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

/**
 * Item has buff in battles
 */
public interface BattleItem {
    public void applyBuff(BattleStatistics origin);
    public void use(Game game);
    public int getDurability();
}
