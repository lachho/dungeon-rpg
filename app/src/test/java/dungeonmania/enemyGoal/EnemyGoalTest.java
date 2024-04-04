package dungeonmania.enemyGoal;

import dungeonmania.DungeonManiaController;
import dungeonmania.mvp.TestUtils;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyGoalTest {
  @Test
  @Tag("2a-1")
  @DisplayName("Set enemy goal, destroy all spawners and minimum number of enemies")
  // Should be achieved
  public void testBasicDestroyAllSpawnersMinimumEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    assertEquals(1, TestUtils.getEntities(res, "sword").size());

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    // destroying the spawner doesn't count as destroying an enemy
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-2")
  @DisplayName("Set enemy goal, destroy all but 1 spawners and minimum number of enemies")
  // Shouldn't be achieved
  public void testDestroyMostSpawnersMinimumEnemies() {
    // Two zombie spawners and one spider
    // We destroy one spawner and one spider
    // Goal is not met
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_basicDestroyMostSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");
    assertEquals(2, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed a spawner
    assertEquals(1, TestUtils.countType(res, "zombie_toast_spawner"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    // assert goal not met - a spawner still exists
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
  }

  @Test
  @Tag("2a-3")
  // Shouldn't be achieved
  @DisplayName("Set enemy goal, destory all spawners and minimum number of enemies minus one")
  public void testDestroyAllSpawnersMostEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersNotMinimumEntites");

    // Map has goal set to destroying two enemies, but only one exists on the map
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    // assert not goal met - numEnemiesDefeated < targetNumEnemiesDefeated
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
  }

  @Test
  @Tag("2a-4")
  @DisplayName("Set enemy goal, destroy minimum entities, and then destroy the spawner")
  // Should be achieved
  public void testDestroyEnemyThenSpawner() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());
    // kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    dmc.tick(Direction.LEFT);
    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.UP);
    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  // Tests 1 - 4 but also with an exit goal

  @Test
  @Tag("2a-5")
  @DisplayName("Set enemy and exit goal, destroy all spawners and minimum number of enemies")
  // Should be achieved
  public void testExitDestroyAllSpawnersMinimumEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_exitDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
    assertTrue(TestUtils.getGoals(res).contains(":exit"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    assertTrue(TestUtils.getGoals(res).contains(":exit"));
    assertFalse(TestUtils.getGoals(res).contains(":enemy"));

    // move to exit
    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.LEFT);

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-6")
  @DisplayName("Set enemy and exit goal, destroy all but 1 spawners and minimum number of enemies")
  // Shouldn't be achieved
  public void testExitDestroyMostSpawnersMinimumEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_exitDestroyMostSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");
    assertEquals(2, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // destroy a spawner
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
    assertEquals(1, TestUtils.countType(res, "zombie_toast_spawner"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    // assert goal not met - a spawner still exists
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    // move to exit
    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.LEFT);

    // move off exit, assert goal not met
    res = dmc.tick(Direction.LEFT);

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
    assertTrue(TestUtils.getGoals(res).contains(":exit"));

  }

  @Test
  @Tag("2a-7")
  @DisplayName("Set enemy and exit goal, destroy minimum entities, and then destroy the spawner")
  // Should be achieved
  public void testBExitDestroyEnemyThenSpawner() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_exitDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
    assertTrue(TestUtils.getGoals(res).contains(":exit"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());
    // kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    dmc.tick(Direction.LEFT);
    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.UP);
    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    // assert goal met
    assertTrue(TestUtils.getGoals(res).contains(":exit"));
    assertFalse(TestUtils.getGoals(res).contains(":enemy"));

    // move to exit
    dmc.tick(Direction.LEFT);
    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.DOWN);
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-8")
  @DisplayName("Test achieving a basic boulders OR enemy goal")
  public void testEnemyOrBoulderGoal() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_boulderOr", "c_basicGoalsTest_oneSwitch");

    // move player to right
    res = dmc.tick(Direction.RIGHT);

    // assert goal not met
    assertTrue(TestUtils.getGoals(res).contains(":boulders"));
    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    // move boulder onto switch
    res = dmc.tick(Direction.RIGHT);

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-9")
  @DisplayName("Set enemy AND treasure goal")
  // Should be achieved
  public void testEnemyAndTreasureGoal() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_treasureAnd",
        "c__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));
    assertTrue(TestUtils.getGoals(res).contains(":treasure"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    dmc.tick(Direction.DOWN);
    dmc.tick(Direction.DOWN);
    res = dmc.tick(Direction.RIGHT);
    assertEquals(1, TestUtils.getEntities(res, "spider").size());

    // Kill the spider
    res = dmc.tick(Direction.DOWN);
    assertEquals(0, TestUtils.getEntities(res, "spider").size());

    // assert goal met
    assertTrue(TestUtils.getGoals(res).contains(":treasure"));
    assertFalse(TestUtils.getGoals(res).contains(":enemy"));

    // move to treasure
    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.LEFT);
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-10")
  @DisplayName("Set enemy goal, destroy all spawners and minimum number of enemies = 0")
  // Should be achieved
  public void testBasicDestroyAllSpawnersNoEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d__enemyGoalTest_basicDestroyAllSpawnersMinimumEntites",
        "c__enemyGoalTest_basicDestroyAllSpawnersNoEntites");

    assertTrue(TestUtils.getGoals(res).contains(":enemy"));

    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    assertEquals(1, TestUtils.getEntities(res, "sword").size());

    // move player to left
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: true, has sword: true
    res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

    // we've destroyed the spawner
    assertEquals(0, TestUtils.countType(res, "zombie_toast_spawner"));

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  // complex test?

}
