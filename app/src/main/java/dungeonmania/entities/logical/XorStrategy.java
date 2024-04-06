package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.util.Position;

public class XorStrategy implements LogicalStrategy {
  public boolean evaluate(Game game, Position position) {
    return game.getNumAdjacentActiveCurrents(position, false) == 1;
  }
}
