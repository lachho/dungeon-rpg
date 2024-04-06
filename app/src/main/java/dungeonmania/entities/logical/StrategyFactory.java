package dungeonmania.entities.logical;

public class StrategyFactory {
  public static LogicalStrategy getStrategy(String strategy) {
    if (strategy.equals("abd")) {
      return new AndStrategy();
    } else if (strategy.equals("or")) {
      return new OrStrategy();
    } else if (strategy.equals("xor")) {
      return new XorStrategy();
    } else if (strategy.equals("co_and")) {
      return new CoAndStrategy();
    } else {
      return null;
    }
  }
}
