package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class LightBulb extends Entity implements LogicalEntity, Toggleable {
  private LogicalStrategy logicalStrategy;
  private boolean isOn = false;

  public LightBulb(Position position, String strategy) {
    super(position);
    this.logicalStrategy = StrategyFactory.getStrategy(strategy);
  }

  public LogicalStrategy getLogicalStrategy() {
    return logicalStrategy;
  }

  public boolean isOn() {
    return isOn;
  }

  private void turnOn(boolean isOn) {
    this.isOn = isOn;
  }

  public void evaluate(Game game) {
    if (logicalStrategy.evaluate(game, getPosition())) {
      turnOn(true);
    } else {
      turnOff();
    }

  }

  public void turnOff() {
    turnOn(false);
  }

}
