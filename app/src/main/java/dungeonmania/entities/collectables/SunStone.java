package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

public class SunStone extends Collectables implements DoorUnlockable, TreasureCount {
    public SunStone(Position position) {
        super(position);
    }
}
