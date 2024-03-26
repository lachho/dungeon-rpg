package dungeonmania.entities.battleDecorator;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;

public class DecoratedPlayer implements BuffDecorator{
    private BuffDecorator decorator;
    private BattleItem item;

    public DecoratedPlayer(BuffDecorator decorator, BattleItem item) {
        this.decorator = decorator;
        this.item = item;
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        decorator.applyBuff(origin);
        item.applyBuff(origin);
    }

    @Override
    public void use(Game game) {
        decorator.use(game);
        item.use(game);
    }

    public List<BattleItem> getItems() {
        List<BattleItem> items = new ArrayList<>(decorator.getItems()); 
        items.add(item);
        return items;
    }
}
