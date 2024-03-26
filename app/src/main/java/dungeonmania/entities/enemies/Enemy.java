package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Destroyable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Overlappable;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable, Destroyable, Overlappable {
    private BattleStatistics battleStatistics;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    public double getBattleStatisticsHealth() {
        return battleStatistics.getHealth();
    }

    public void setBattleStatisticsHealth(double health) {
        battleStatistics.setHealth(health);
    }

    public void onOverlap(Game game, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            game.battle(player, this);
        }
    }

    public void onDestroy(Game game) {
        game.unsubscribe(getId());
    }

    public abstract void move(Game game);
}
