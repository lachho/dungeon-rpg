package dungeonmania.entities.battleDecorator;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.BattleItem;

public abstract class BuffDecorator implements Battleable {
    private Battleable player;
    private BattleItem item;

    public BuffDecorator(Battleable player, BattleItem item) {
        this.player = player;
        this.item = item;
    }

    @Override
    public void applyBuff(BattleStatistics origin) {
        item.applyBuff(origin);
    }

    @Override
    public void use(Game game) {
        player.use(game);
        item.use(game);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return player.getBattleStatistics();
    }

    @Override
    public double getBattleStatisticsHealth() {
        return player.getBattleStatisticsHealth();
    }

    @Override
    public void setBattleStatisticsHealth(double health) {
        player.setBattleStatisticsHealth(health);
    }

}
