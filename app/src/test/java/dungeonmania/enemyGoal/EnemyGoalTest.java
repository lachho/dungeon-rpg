package dungeonmania.enemyGoal;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
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
    DungeonResponse res = dmc.newGame("d_enemyGoalTest_basicDestroyAllSpawnersMinimumEntites",
        "c_enemyGoalTest_basicDestroyAllSpawnersMinimumEntites");
    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());
    String spawnerId = TestUtils.getEntities(res, "zombie_toast_spawner").get(0).getId();

    // move player to right
    res = dmc.tick(Direction.LEFT);

    // player has picked up weapon
    assertEquals(1, TestUtils.getInventory(res, "sword").size());

    // cardinally adjacent: false, has sword: true
    assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
    assertEquals(1, TestUtils.getEntities(res, "zombie_toast_spawner").size());

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

    dmc.tick(Direction.LEFT);
    res = dmc.tick(Direction.LEFT);

    // assert goal met
    assertEquals("", TestUtils.getGoals(res));
  }

  @Test
  @Tag("2a-2")
  @DisplayName("Set enemy goal, destroy all but 1 spawners and minimum number of enemies")
  // Shouldn't be achieved
  public void testDestroyMostSpawnersMinimumEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d_basicGoalsTest_exit", "c_basicGoalsTest_exit");
  }

  @Test
  @Tag("2a-3")
  // Shouldn't be achieved
  @DisplayName("Set enemy goal, destory all spawners and minimum number of enemies minus one")
  public void testDestroyAllSpawnersMostEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d_basicGoalsTest_exit", "c_basicGoalsTest_exit");
  }

  @Test
  @Tag("2a-4")
  @DisplayName("Set enemy goal, no spawners and minimum number of enemies")
  // Should be achieved
  public void testNoSpawnersMinimumEnemies() {
    DungeonManiaController dmc;
    dmc = new DungeonManiaController();
    DungeonResponse res = dmc.newGame("d_basicGoalsTest_exit", "c_basicGoalsTest_exit");
  }

}
