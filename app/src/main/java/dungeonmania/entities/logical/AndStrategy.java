package dungeonmania.entities.logical;

import dungeonmania.util.Position;

import dungeonmania.Game;

public class AndStrategy implements LogicalStrategy {
  public boolean evaluate(Game game, Position position) {
    int numAdjacentCurrents = game.getNumAdjacentCurrents(position);
    int numAdjacentActiveCurrents = game.getNumAdjacentActiveCurrents(position, false);

    return (numAdjacentCurrents >= 2) && (numAdjacentCurrents == numAdjacentActiveCurrents);
  }
}
