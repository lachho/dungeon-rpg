package dungeonmania.entities.collectables.potions;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Collectables;
import dungeonmania.entities.collectables.Usable;
import dungeonmania.entities.playerState.PlayerState;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Potion extends Collectables implements Usable {
    private int duration;

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void applyBuff(BattleStatistics origin) {
        return;
    }

    public abstract PlayerState createState();

    public void use(Player player, GameMap map) {
        player.remove(this);
        player.addPotion(this, map.getTick());
    }
}
