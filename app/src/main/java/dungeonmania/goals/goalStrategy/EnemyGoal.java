package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;
import dungeonmania.entities.enemies.ZombieToastSpawner;

public class EnemyGoal implements GoalStrategy {
  private int numEnemiesToDestroy;

  public EnemyGoal(int numEnemiesToDestroy) {
    this.numEnemiesToDestroy = numEnemiesToDestroy;
  }

  @Override
  public boolean achieved(Game game) {
    int numSpawners = game.getMapEntities(ZombieToastSpawner.class).size();

    return numSpawners == 0 && game.getNumDefeatedEnemies() >= numEnemiesToDestroy;
  }

  @Override
  public String toString(Game game) {
    if (this.achieved(game))
      return "";

    return ":enemies";
  }

}
