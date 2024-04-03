package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Destroyable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.util.Position;

public class ZombieToastSpawner extends Entity implements Interactable, Destroyable {
    public static final int DEFAULT_SPAWN_INTERVAL = 0;

    public ZombieToastSpawner(Position position, int spawnInterval) {
        super(position);
    }

    public void spawn(Game game) {
        game.spawnZombie(game, this);
    }

    public void onDestroy(Game game) {
        game.unsubscribe(getId());
    }

    @Override
    public void interact(Player player, Game game) {
        player.useWeapon(game);
    }

    @Override
    public boolean isInteractable(Player player) {
        return Position.isAdjacent(player.getPosition(), getPosition()) && player.hasWeapon();
    }
}
