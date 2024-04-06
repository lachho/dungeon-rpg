package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;

import dungeonmania.util.Position;

public class Wire extends Entity implements Toggleable {
  private boolean hasCurrent = false;
  private int activatedTickNumber;

  public Wire(Position position) {
    super(position);
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
