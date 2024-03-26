package dungeonmania.entities.battleDecorator;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;

public interface BattleEffect {
    public void applyBuff(BattleStatistics origin);
    public void use(Game game);
}
