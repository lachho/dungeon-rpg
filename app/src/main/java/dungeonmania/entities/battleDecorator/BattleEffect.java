package dungeonmania.entities.battleDecorator;

import dungeonmania.battles.BattleStatistics;

public interface BattleEffect {
    public void applyBuff(BattleStatistics origin);
}
