package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalBomb extends Bomb {
  private LogicalStrategy logicalStrategy;

  public LogicalBomb(Position position, int radius, String strategy) {
    super(position, radius);
    this.logicalStrategy = StrategyFactory.getStrategy(strategy);
  }

  public LogicalStrategy getLogicalStrategy() {
    return logicalStrategy;
  }

  public boolean evaluate(Game game) {
    if (logicalStrategy.evaluate(game, getPosition())) {
      // i think this works, even though it breaks the observer pattern
      explode(game.getMap());
      return true;
    }

    return false;
  }

  // Logic bomb doesn't explode on put down
  @Override
  public void onPutDown(GameMap map, Position p) {
    setPosition(p);
    map.addEntity(this);
    setState(State.PLACED);
  }

}
