package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;

public class OrGoal implements GoalStrategy {
    private GoalStrategy goal1;
    private GoalStrategy goal2;

    public OrGoal(GoalStrategy goal1, GoalStrategy goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean achieved(Game game) {
        return goal1.achieved(game) || goal2.achieved(game);
    }

    public String toString(Game game) {
        if (achieved(game))
            return "";

        return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }

}
