package dungeonmania.entities.logical;

import dungeonmania.Game;
import dungeonmania.entities.collectables.Bomb;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

// TODO i think this needs to implement Tickable, but it needs to take in Game
// --> update fn definitino of onTick()
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
    // if evaluate == true, explode
  }

  // Logic bomb doesn't explode on put down
  @Override
  public void onPutDown(GameMap map, Position p) {
    setPosition(p);
    map.addEntity(this);
    setState(State.PLACED);
  }

  // public void onTick(Game game, int tickCount) {
  //   // bomb has exploded
  //   // if (evaluate(game)) {
  //   //   // bfsLogicalEntities()
  //   //   // game.getalllogiclaentiites
  //   //   // res = game.getallllogicalentities - bfsLogicalEntities

  //   //   // for r in res
  //   //   // r.toggle();

  //   //   // do bfs from all switches to get all the reachable wires and entities
  //   //   // Compare this with all the currently active wires on the map

  //   //   // bomb has exploded
  //   //   // turn off all entiites and turn them on again 💀
  //   //   // this wil break coand switches ugh

  //   //   // do bfs again
  //   // }

  // }
}
