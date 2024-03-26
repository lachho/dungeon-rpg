package dungeonmania.entities.battleDecorator;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;

public interface BuffDecorator {
    public void applyBuff(BattleStatistics origin);

    public void use(Game game);

    List<BattleItem> getItems();
}
