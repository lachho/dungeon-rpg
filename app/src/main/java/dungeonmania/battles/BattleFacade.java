package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.NameConverter;

public class BattleFacade {
    private List<BattleResponse> battleResponses = new ArrayList<>();
    private BattleStatistics playerStats;
    private BattleStatistics enemyStats;
    private Game game;
    private Enemy enemy;
    private Player player;
    private BuffDecorator buffs;

    public void battle(Game game, Player player, Enemy enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        buffs = new BuffDecorator();
        playerStats = new BattleStatistics(player.getBattleStatistics());
        enemyStats = enemy.getBattleStatistics();
        calculatePlayerBuff();

        if (!playerStats.isEnabled() || !enemyStats.isEnabled())
            return;

        logBattleResponse(battleResult());
    }

    public List<BattleResponse> getBattleResponses() {
        return battleResponses;
    }

    private void calculatePlayerBuff() {

        if (player.getState() != "Base") {
            player.applyBuff(playerStats);
        } else {
            for (BattleItem item : player.getInventoryEntities(BattleItem.class)) {
                buffs = new BuffDecorator(buffs, item);
            }
        }
        buffs.applyBuff(playerStats);
        calculateMercenaryBuff();
    }

    private void calculateMercenaryBuff() {
        List<Mercenary> mercs = game.getMap().getEntities(Mercenary.class);
        for (Mercenary merc : mercs) {
            if (merc.isAllied())
                merc.applyBuff(playerStats);
        }
    }

    private List<BattleRound> battleResult() {
        List<BattleRound> rounds = playerStats.battle(enemyStats);
        updateHealth();
        decreaseDurabilityOfItems();
        return rounds;
    }

    private void updateHealth() {
        player.setBattleStatisticsHealth(playerStats.getHealth());
        enemy.setBattleStatisticsHealth(enemyStats.getHealth());
    }

    private void decreaseDurabilityOfItems() {
        buffs.use(game);
    }

    private void logBattleResponse(List<BattleRound> rounds) {
        battleResponses
                .add(new BattleResponse(NameConverter.toSnakeCase(enemy),
                        rounds.stream().map(ResponseBuilder::getRoundResponse).collect(Collectors.toList()),
                        buffs.getItems().stream().map(Entity.class::cast).map(ResponseBuilder::getItemResponse)
                                .collect(Collectors.toList()),
                        player.getBattleStatisticsHealth(), enemy.getBattleStatisticsHealth()));
    }

}
