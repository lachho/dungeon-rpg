package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalBomb extends Bomb implements LogicalEntity {
  private LogicalStrategy logicalStrategy;

  public LogicalBomb(Position position, int radius, String strategy) {
    super(position, radius);
    this.logicalStrategy = StrategyFactory.getStrategy(strategy);
  }

  public LogicalStrategy getLogicalStrategy() {
    return logicalStrategy;
  }

  public void evaluate(Game game) {
    if (logicalStrategy.evaluate(game, getPosition())) {
      explode(game.getMap());
    }
  }

  // Logic bomb doesn't explode on put down
  @Override
  public void onPutDown(GameMap map, Position p) {
    setPosition(p);
    map.addEntity(this);
    setState(State.PLACED);
  }

}
