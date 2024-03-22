package dungeonmania.goals.goalStrategy;

import dungeonmania.Game;

public interface GoalStrategy {

    public boolean achieved(Game game);

    public String toString(Game game);

}
