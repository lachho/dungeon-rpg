package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;

public class AndGoal implements GoalStrategy {
    private GoalStrategy goal1;
    private GoalStrategy goal2;

    public AndGoal(GoalStrategy goal1, GoalStrategy goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean achieved(Game game) {
        return goal1.achieved(game) && goal2.achieved(game);
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";

        return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }
}
