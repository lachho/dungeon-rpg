package dungeonmania.entities.logical;

import dungeonmania.util.Position;

import dungeonmania.Game;

public interface LogicalStrategy {
  public boolean evaluate(Game game, Position position);
}
