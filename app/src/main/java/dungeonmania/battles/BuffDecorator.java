package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.BattleItem;

public class BuffDecorator implements BattleItem {
    private BuffDecorator decorator;
    private BattleItem item;

    public BuffDecorator() {
        decorator = null;
        item = null;
    }

    public BuffDecorator(BuffDecorator decorator, BattleItem item) {
        this.decorator = decorator;
        this.item = item;
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        if (decorator != null) {
            decorator.applyBuff(origin);
            item.applyBuff(origin);
        }
    }

    @Override
    public void use(Game game) {
        if (decorator != null) {
            decorator.use(game);
            item.use(game);
        }
    }

    public List<BattleItem> getItems() {
        if (item != null) {
            List<BattleItem> items = new ArrayList<>(decorator.getItems());
            items.add(item);
            return items;
        }
        return new ArrayList<>();
    }
}
