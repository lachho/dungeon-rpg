package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;

import dungeonmania.util.Position;

public class Wire extends Entity implements Current, LogicalEntity {
  private boolean hasCurrent = false;
  private int activatedTickNumber;

  public Wire(Position position) {
    super(position);
  }

  public void onTick(int tickCount) {
    // TODO do something;
  }

  public boolean hasCurrent() {
    return hasCurrent;
  }

  public void setCurrent(boolean hasCurrent) {
    this.hasCurrent = hasCurrent;
  }

  public int getActivatedTickNumber() {
    return activatedTickNumber;
  }

  public void setActivatedTickNumber(int activatedTickNumber) {
    this.activatedTickNumber = activatedTickNumber;
  }

  public void toggle() {
    setCurrent(!hasCurrent());
  }

  public void turnOff() {
    this.hasCurrent = false;
  }
}
