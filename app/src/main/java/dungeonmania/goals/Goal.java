package dungeonmania.goals;

import dungeonmania.Game;
import dungeonmania.goals.goalStrategy.AndGoal;
import dungeonmania.goals.goalStrategy.BoulderGoal;
import dungeonmania.goals.goalStrategy.ExitGoal;
import dungeonmania.goals.goalStrategy.GoalStrategy;
import dungeonmania.goals.goalStrategy.NoGoal;
import dungeonmania.goals.goalStrategy.OrGoal;
import dungeonmania.goals.goalStrategy.TreasureGoal;

public class Goal {
    private GoalStrategy strategy;

    public Goal(String type) {
        this(type, 0);
    }

    public Goal(String type, int target) {
        switch (type) {
        case "exit":
            strategy = new ExitGoal();
            break;
        case "boulders":
            strategy = new BoulderGoal();
            break;
        case "treasure":
            strategy = new TreasureGoal(target);
            break;
        default:
            strategy = new NoGoal();
        }
    }

    public Goal(String type, Goal goal1, Goal goal2) {
        switch (type) {
        case "AND":
            strategy = new AndGoal(goal1.strategy, goal2.strategy);
            break;
        case "OR":
            strategy = new OrGoal(goal1.strategy, goal2.strategy);
            break;
        default:
            strategy = new NoGoal();
        }
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;

        return strategy.achieved(game);
    }

    public String toString(Game game) {
        return strategy.toString(game);
    }

}
