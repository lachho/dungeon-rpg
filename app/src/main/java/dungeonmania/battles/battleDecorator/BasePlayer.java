package dungeonmania.battles.battleDecorator;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;

public class BasePlayer implements BuffDecorator {
    public BasePlayer() { }

    @Override
    public void applyBuff(BattleStatistics origin) {
        return;
    }

    @Override
    public void use(Game game) {
        return;
    }

    @Override
    public List<BattleItem> getItems() {
        return new ArrayList<BattleItem>();
    }


}
