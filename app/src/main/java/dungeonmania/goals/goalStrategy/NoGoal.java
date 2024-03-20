package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;

public class NoGoal implements GoalStrategy {

    @Override
    public boolean achieved(Game game) {return false;}

    @Override
    public String toString(Game game) {return "";}
}
