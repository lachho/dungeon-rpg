package dungeonmania.entities.battleDecorator;

import dungeonmania.battles.Battleable;
import dungeonmania.entities.BattleItem;

public class SwordDecorator extends BuffDecorator {

    public SwordDecorator(Battleable player, BattleItem item) {
        super(player, item);
    }
    
}
