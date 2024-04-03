package dungeonmania.entities.collectables;

import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;

public interface Usable {
    public void use(Player player, GameMap map);
}
