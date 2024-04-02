package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class SunStoneTest {
    @Test
    @Tag("20-1")
    @DisplayName("Test collect sunstone and check in inventory")
    public void collectSunstone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_collection", "c_SunStoneTest_collection");
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
    }

    @Test
    @Tag("20-2")
    @DisplayName("Use sunstone to open door, check is retained after use")
    public void openDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO replicate old dungeon and config and replace key with door d_DoorsKeysTest_useKeyWalkThroughOpenDoor
        DungeonResponse res = dmc.newGame("d_SunStoneTest_WalkThroughOpenDoor", "c_SunStoneTest_WalkThroughOpenDoor");

        // pick up sunstone
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("20-3")
    @DisplayName("Test meeting treasure goal with sunstone")
    public void treasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO update old dungeon to collect some sunstones instead of treasures d_basicGoalsTest_treasure
        DungeonResponse res = dmc.newGame("d_SunStoneTest_treasure", "c_SunStoneTest_treasure");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        //TODO collect sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sunstone").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        //TODO collect sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(3, TestUtils.getInventory(res, "sunstone").size());

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("20-4")
    @DisplayName("Create sceptre with 2 sunstones, 1 to be retained, 1 consumed")
    public void buildSceptreWith2Stones() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO update to collect 1 wood, 2 sunstones d_BuildablesTest_BuildBow
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildSeptre2Stones", "c_SunStoneTest_BuildSeptre2Stones");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sunstone").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up SunStone x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sunstone").size());

        // Build Bow
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
    }

    @Test
    @Tag("20-5")
    @DisplayName("Test building a shield with sunstone")
    public void buildShieldWithSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO update to replace treasure with sunstone, sunstone to be retained
        //d_BuildablesTest_BuildShieldWithTreasure
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithSunStone",
                "c_SunStoneTest_BuildShieldWithSunStone");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sunstone").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
    }

    @Test
    @Tag("20-6")
    @DisplayName("Test building a shield with treasure")
    public void buildShieldWithTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO update also collect a sunstone, but treasure to be consumed in , sunstone to be retained
        //d_BuildablesTest_BuildShieldWithTreasure
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithTreasure",
                "c_SunStoneTest_BuildShieldWithTreasure");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure and Sunstone
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
    }

    @Test
    @Tag("20-7")
    @DisplayName("Test Mindcontrol")
    public void battleWithMindControl() throws InvalidActionException {
        DungeonManiaController controller = new DungeonManiaController();
        //TODO update such that we build a sceptre, not a shield in d_battleTest_shieldDurabilityTest
        String config = "c_SunStoneTest_mindControl";
        DungeonResponse res = controller.newGame("d_SunStoneTest_mindControl", config);

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(3, TestUtils.countEntityOfType(entities, "zombie_toast"));

        //TODO Pick up Arrows
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);

        //TODO Pick up sunstone
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        res = controller.build("sceptre");

        // battle zombie number 1
        res = controller.tick(Direction.RIGHT);

        // move such that mind control expires
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // battle zombie number 2, no more ally attack bonus
        res = controller.tick(Direction.RIGHT);

        assertTrue(res.getBattles().size() != 0);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse firstBattle = battles.get(0);

        // This is the attack without ally
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double allyAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("ally_attack", config));

        // check values with ally boost
        RoundResponse firstRound = firstBattle.getRounds().get(0);
        assertEquals((playerBaseAttack + allyAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        assertNotEquals(0, firstBattle.getBattleItems().size());
        assertTrue(firstBattle.getBattleItems().get(0).getType().startsWith("sceptre"));

        // check values for no ally boost
        BattleResponse lastBattle = battles.get(battles.size() - 1);
        // the sceptre is not used, hence no ally bonuses
        assertEquals(0, lastBattle.getBattleItems().size());
        firstRound = lastBattle.getRounds().get(0);
        assertEquals((playerBaseAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

    @Test
    @Tag("20-8")
    @DisplayName("Craft Midnight armour with zombies")
    public void battleWithMidnightArmour() throws InvalidActionException {
        DungeonManiaController controller = new DungeonManiaController();
        //TODO update such that we build a midnight armour, not a shield in d_battleTest_shieldDurabilityTest
        // ensure there is 1 zombie and then 1 spider to battle
        String config = "c_SunStoneTest_MidnightArmour";
        DungeonResponse res = controller.newGame("d_SunStoneTest_MidnightArmour", config);

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "spider"));

        //TODO Pick up sword
        res = controller.tick(Direction.RIGHT);

        // Pick up treasure
        res = controller.tick(Direction.RIGHT);

        //TODO Pick up sjunstone
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sunstone").size());
        assertEquals(2, TestUtils.getInventory(res, "sword").size());

        assertThrows(InvalidActionException.class, () -> controller.build("midnightarmour"));

        // kill zombie number 1
        res = controller.tick(Direction.RIGHT);

        res = controller.build("midnightarmour");

        // battle spider, has attack and defence bonus
        res = controller.tick(Direction.RIGHT);

        //TODO idk what this line does
        assertTrue(res.getBattles().size() != 0);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse lastBattle = battles.get(1);

        // This is the attack without ally
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double allyAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("ally_attack", config));

        // check values with ally boost
        RoundResponse firstRound = lastBattle.getRounds().get(0);
        assertEquals((playerBaseAttack + allyAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        // check armour is held
        assertNotEquals(0, lastBattle.getBattleItems().size());
        assertTrue(lastBattle.getBattleItems().get(0).getType().startsWith("midnightarmour"));

        // Assumption: Shield effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - shield effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("spider_attack", config));
        int shieldEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("shield_defence", config));
        int expectedDamage = (enemyAttack - shieldEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
    }

}
