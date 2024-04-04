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
    @DisplayName("Test collect sun_stone and check in inventory")
    public void collectSunstone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_collection", "c_SunStoneTest");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-2")
    @DisplayName("Use sun_stone to open door, check is retained after use")
    public void openDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_WalkThroughDoor", "c_SunStoneTest");

        // pick up sun_stone
        res = dmc.tick(Direction.RIGHT);
        Position pos = TestUtils.getEntities(res, "player").get(0).getPosition();
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // walk through door and check sun_stone remains
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertNotEquals(pos, TestUtils.getEntities(res, "player").get(0).getPosition());
    }

    @Test
    @Tag("20-3")
    @DisplayName("Test meeting treasure goal with sun_stone")
    public void treasureGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_treasure", "c_SunStoneTest");

        // move player to right
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect sun_stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // collect sun_stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("20-4")
    @DisplayName("Create sceptre with 2 sun_stones, 1 to be retained, 1 consumed")
    public void buildSceptreWith2Stones() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildSeptre2Stones", "c_SunStoneTest");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up SunStone x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-5")
    @DisplayName("Test building a shield with sun_stone")
    public void buildShieldWithSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        //TODO update to replace treasure with sun_stone, sun_stone to be retained
        //d_BuildablesTest_BuildShieldWithTreasure
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithSunStone", "c_SunStoneTest");
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up Wood x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "wood").size());

        // Pick up Sunstone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-6")
    @DisplayName("Test building a shield with treasure, even if sunstone exists")
    public void buildShieldWithTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SunStoneTest_BuildShieldWithTreasure", "c_SunStoneTest");
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
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Shield
        assertEquals(0, TestUtils.getInventory(res, "shield").size());
        res = assertDoesNotThrow(() -> dmc.build("shield"));
        assertEquals(1, TestUtils.getInventory(res, "shield").size());

        // Materials used in construction disappear from inventory, yet sunstone is not consumed
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-7")
    @DisplayName("Test Mindcontrol")
    public void battleWithMindControl() throws InvalidActionException {
        DungeonManiaController controller = new DungeonManiaController();
        String config = "c_SunStoneTest";
        DungeonResponse res = controller.newGame("d_SunStoneTest_mindControl", config);

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "mercenary"));
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Pick up Arrows
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Pick up key
        res = controller.tick(Direction.RIGHT);

        // Pick up sun_stone
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        res = controller.build("sceptre");
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> controller.interact(mercId));

        // kill zombie number 1
        int battlesHeld = 0;
        while (battlesHeld == 0) {
            res = controller.tick(Direction.RIGHT);
            battlesHeld = res.getBattles().size();
        }

        res = controller.tick(Direction.LEFT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // battle mercenary, no more sceptre bonus
        while (battlesHeld == 1) {
            res = controller.tick(Direction.RIGHT);
            battlesHeld = res.getBattles().size();
        }

        assertEquals(2, res.getBattles().size());
        List<BattleResponse> battles = res.getBattles();
        BattleResponse firstBattle = battles.get(0);

        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double allyAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("ally_attack", config));

        // check values with ally boost
        RoundResponse firstRound = firstBattle.getRounds().get(0);
        assertEquals((playerBaseAttack + allyAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        // check values for no ally boost
        BattleResponse lastBattle = battles.get(battles.size() - 1);
        firstRound = lastBattle.getRounds().get(0);
        assertEquals((playerBaseAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

    @Test
    @Tag("20-8")
    @DisplayName("Craft Midnight armour with zombies")
    public void battleWithMidnightArmour() throws InvalidActionException {
        DungeonManiaController controller = new DungeonManiaController();
        String config = "c_SunStoneTest";
        DungeonResponse res = controller.newGame("d_SunStoneTest_MidnightArmour", config);

        List<EntityResponse> entities = res.getEntities();
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "zombie_toast"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "mercenary"));

        // Pick up sword, treasure, sunstone
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        assertThrows(InvalidActionException.class, () -> controller.build("midnight_armour"));

        // kill zombie number 1
        int battlesHeld = 0;
        while (battlesHeld == 0) {
            res = controller.tick(Direction.RIGHT);
            battlesHeld = res.getBattles().size();
        }

        res = controller.build("midnight_armour");

        // battle mercenary, has attack and defence bonus
        while (battlesHeld == 1) {
            res = controller.tick(Direction.RIGHT);
            battlesHeld = res.getBattles().size();
        }

        assertEquals(2, res.getBattles().size());
        List<BattleResponse> battles = res.getBattles();
        BattleResponse lastBattle = battles.get(1);

        // check armour is held
        assertNotEquals(0, lastBattle.getBattleItems().size());
        assertTrue(lastBattle.getBattleItems().get(0).getType().startsWith("midnight_armour"));

        // This is the attack without armour
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double armourAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        // check values with armour boost
        RoundResponse firstRound = lastBattle.getRounds().get(0);
        assertEquals((playerBaseAttack + armourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);

        // Assumption: armour effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - armour effect
        double enemyAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("mercenary_attack", config));
        double armourEffect = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        double expectedDamage = (enemyAttack - armourEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
    }

}
