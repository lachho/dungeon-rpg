package dungeonmania.entities.logical;

import dungeonmania.util.Position;

import dungeonmania.Game;

public class CoAndStrategy implements LogicalStrategy {
  public boolean evaluate(Game game, Position position) {
    int numAdjacentActiveCurrentsSameTick = game.getNumAdjacentActiveCurrents(position, true);
    int numAdjacentCurrents = game.getNumAdjacentCurrents(position);

    return numAdjacentActiveCurrentsSameTick >= 2 && (numAdjacentCurrents == numAdjacentActiveCurrentsSameTick);
  }
}
