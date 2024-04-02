package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

public class Treasure extends Collectables implements TreasureCount, SpecialCraftable {
    public Treasure(Position position) {
        super(position);
    }
}
