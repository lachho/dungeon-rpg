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
        // 0. init
        double initialPlayerHealth = player.getBattleStatistics().getHealth();
        double initialEnemyHealth = enemy.getBattleStatistics().getHealth();
        String enemyString = NameConverter.toSnakeCase(enemy);

        // 1. apply buff provided by the game and player's inventory
        // getting buffing amount
        List<BattleItem> battleItems = new ArrayList<>();
        BattleStatistics playerStats = new BattleStatistics(player.getBattleStatistics());

        if (player.getState() != "Base") {
            player.applyBuff(playerStats);
        } else {
            for (BattleItem item : player.getInventory().getEntities(BattleItem.class)) {
                // if (!(item instanceof Potion)) {
                item.applyBuff(playerStats);
                battleItems.add(item);
                // }
            }
        }

        List<Mercenary> mercs = game.getMap().getEntities(Mercenary.class);
        for (Mercenary merc : mercs) {
            if (merc.isAllied()) merc.applyBuff(playerStats);
            // playerStats = BattleStatistics.applyBuff(playerStats, merc .getBattleStatistics());
        }

        // 2. Battle the two stats
        // BattleStatistics playerBattleStatistics = BattleStatistics.applyBuff(player.getBattleStatistics(), playerStats);
        BattleStatistics enemyStats = enemy.getBattleStatistics();
        if (!playerStats.isEnabled() || !enemyStats.isEnabled())
            return;
        List<BattleRound> rounds = playerStats.battle(enemyStats);

        // 3. update health to the actual statistics
        player.getBattleStatistics().setHealth(playerStats.getHealth());
        enemy.getBattleStatistics().setHealth(enemyStats.getHealth());

        // 4. call to decrease durability of items
        for (BattleItem item : battleItems) {
            if (item instanceof InventoryItem)
                item.use(game);
        }

        // 5. Log the battle - solidate it to be a battle response
        battleResponses.add(new BattleResponse(
                enemyString,
                rounds.stream()
                    .map(ResponseBuilder::getRoundResponse)
                    .collect(Collectors.toList()),
                battleItems.stream()
                        .map(Entity.class::cast)
                        .map(ResponseBuilder::getItemResponse)
                        .collect(Collectors.toList()),
                initialPlayerHealth,
                initialEnemyHealth));
    }

    public List<BattleResponse> getBattleResponses() {
        return battleResponses;
    }
}
