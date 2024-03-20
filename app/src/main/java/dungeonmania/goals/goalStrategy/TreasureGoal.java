package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;

public class TreasureGoal implements GoalStrategy {
    private int target;

    public TreasureGoal(int target) {
        this.target = target;
    }

    @Override
    public boolean achieved(Game game) {
        return game.getCollectedTreasureCount() >= target;
    }

    @Override
    public String toString(Game game) {
        if (this.achieved(game))
            return "";

        return ":treasure";
    }

}
