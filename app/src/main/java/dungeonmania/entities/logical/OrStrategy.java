package dungeonmania.entities.logical;

import dungeonmania.util.Position;

import dungeonmania.Game;

public class OrStrategy implements LogicalStrategy {
  public boolean evaluate(Game game, Position position) {
    return game.getNumAdjacentActiveCurrents(position, false) >= 1;
  }
}
