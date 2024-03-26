package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.NameConverter;

public class BattleFacade {
    private List<BattleResponse> battleResponses = new ArrayList<>();

    public void battle(Game game, Player player, Enemy enemy) {
        List<BattleItem> battleItems = new ArrayList<>();
        BattleStatistics playerBuff = new BattleStatistics(0, 0, 0, 1, 1);
        playerBuff = calculatePlayerBuff(battleItems, playerBuff, player, game);

        List<BattleRound> battleRounds = battleTwoStatistics(player, enemy, playerBuff);
        if (battleRounds == null)
            return;

        decreaseDurabilityOfItems(game, battleItems);

        logBattleResponse(player, enemy, battleRounds, battleItems);
    }

    public List<BattleResponse> getBattleResponses() {
        return battleResponses;
    }

    private BattleStatistics calculatePlayerBuff(List<BattleItem> battleItems, BattleStatistics playerBuff,
            Player player, Game game) {
        Potion effectivePotion = player.getEffectivePotion();
        if (effectivePotion != null) {
            playerBuff = player.applyBuff(playerBuff);
        } else {
            // FIXME demeter violation
            for (BattleItem item : player.getInventory().getEntities(BattleItem.class)) {
                if (item instanceof Potion)
                    continue;

                playerBuff = item.applyBuff(playerBuff);
                battleItems.add(item);
            }
        }

        return calculateMercenaryBuff(playerBuff, game);
    }

    private BattleStatistics calculateMercenaryBuff(BattleStatistics playerBuff, Game game) {
        List<Mercenary> mercs = game.getMap().getEntities(Mercenary.class);
        for (Mercenary merc : mercs) {
            if (!merc.isAllied())
                continue;
            playerBuff = BattleStatistics.applyBuff(playerBuff, merc.getBattleStatistics());
        }

        return playerBuff;
    }

    private List<BattleRound> battleTwoStatistics(Player player, Enemy enemy, BattleStatistics playerBuff) {
        BattleStatistics playerBaseStatistics = player.getBattleStatistics();
        BattleStatistics enemyBaseStatistics = enemy.getBattleStatistics();
        BattleStatistics playerBattleStatistics = BattleStatistics.applyBuff(playerBaseStatistics, playerBuff);
        BattleStatistics enemyBattleStatistics = enemyBaseStatistics;
        if (!playerBattleStatistics.isEnabled() || !enemyBaseStatistics.isEnabled())
            return null;

        List<BattleRound> battleRounds = BattleStatistics.battle(playerBattleStatistics, enemyBattleStatistics);

        updateHealth(player, enemy, playerBattleStatistics, enemyBattleStatistics);
        return battleRounds;
    }

    private void updateHealth(Player player, Enemy enemy, BattleStatistics playerBattleStatistics,
            BattleStatistics enemyBattleStatistics) {
        player.setBattleStatisticsHealth(playerBattleStatistics.getHealth());
        enemy.setBattleStatisticsHealth(enemyBattleStatistics.getHealth());
    }

    private void decreaseDurabilityOfItems(Game game, List<BattleItem> battleItems) {
        for (BattleItem item : battleItems) {
            if (item instanceof InventoryItem)
                item.use(game);
        }
    }

    private void logBattleResponse(Player player, Enemy enemy, List<BattleRound> rounds, List<BattleItem> battleItems) {
        battleResponses
                .add(new BattleResponse(NameConverter.toSnakeCase(enemy),
                        rounds.stream().map(ResponseBuilder::getRoundResponse).collect(Collectors.toList()),
                        battleItems.stream().map(Entity.class::cast).map(ResponseBuilder::getItemResponse)
                                .collect(Collectors.toList()),
                        player.getBattleStatisticsHealth(), enemy.getBattleStatisticsHealth()));
    }
}
