package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.Door;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends Door implements LogicalEntity, Toggleable {
  private boolean open = false;
  private LogicalStrategy logicalStrategy;

  public SwitchDoor(Position position, String strategy) {
    super(position);
    this.logicalStrategy = StrategyFactory.getStrategy(strategy);
  }

  public LogicalStrategy getLogicalStrategy() {
    return logicalStrategy;
  }

  @Override
  public boolean canMoveOnto(GameMap map, Entity entity) {
    // player can only move onto the door if it is open
    if (entity instanceof Player) {
      return isOpen();
    }

    return false;
  }

  @Override
  // TODO - figure out what this does
  public void onOverlap(Game game, Entity entity) {
    if (!(entity instanceof Player))
      return;
  }

  public boolean isOpen() {
    return open;
  }

  private void openDoor(boolean open) {
    this.open = open;
  }

  public boolean evaluate(Game game) {
    if (logicalStrategy.evaluate(game, getPosition())) {
      openDoor(true);
      return true;
    }

    return false;
  }

  public void turnOff() {
    openDoor(false);
  }

}
