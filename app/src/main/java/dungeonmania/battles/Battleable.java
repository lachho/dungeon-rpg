package dungeonmania.battles;

import dungeonmania.Game;

/**
 * Entities implement this interface can do battles
 */
public interface Battleable {
    public BattleStatistics getBattleStatistics();

    public double getBattleStatisticsHealth();

    public void setBattleStatisticsHealth(double health);

    public void applyBuff(BattleStatistics origin);

    public void use(Game game);
}
