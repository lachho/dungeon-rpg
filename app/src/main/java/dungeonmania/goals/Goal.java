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
    private String type;
    private int target;
    private Goal goal1;
    private Goal goal2;

    public Goal(String type) {
        this(type, 0);
        // this.type = type;
    }

    public Goal(String type, int target) {
        // this.type = type;
        // this.target = target;
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
        // this.type = type;
        // this.goal1 = goal1;
        // this.goal2 = goal2;
    }

    /**
     * @return true if the goal has been achieved, false otherwise
     */
    public boolean achieved(Game game) {
        if (game.getPlayer() == null)
            return false;

        return strategy.achieved(game);
        // switch (type) {
        // case "exit":
        //     Player character = game.getPlayer();
        //     Position pos = character.getPosition();
        //     List<Exit> es = game.getMap().getEntities(Exit.class);
        //     if (es == null || es.size() == 0)
        //         return false;
        //     return es.stream().map(Entity::getPosition).anyMatch(pos::equals);
        // case "boulders":
        //     return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
        // case "treasure":
        //     return game.getCollectedTreasureCount() >= target;
        // case "AND":
        //     return goal1.achieved(game) && goal2.achieved(game);
        // case "OR":
        //     return goal1.achieved(game) || goal2.achieved(game);
        // default:
        //     break;
        // }
        // return false;
    }

    public String toString(Game game) {
        if (achieved(game))
            return "";

        return strategy.toString(game);
        // switch (type) {
        // case "exit":
        //     return ":exit";
        // case "boulders":
        //     return ":boulders";
        // case "treasure":
        //     return ":treasure";
        // case "AND":
        //     return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
        // case "OR":
        //     if (achieved(game))
        //         return "";
        //     else
        //         return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
        // default:
        //     return "";
        // }
    }

}
