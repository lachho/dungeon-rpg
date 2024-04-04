package dungeonmania.entities.playerState;

public class BaseState extends PlayerState {
    public BaseState() {
        super(null);
    }

    @Override
    public String getState() {
        return "Base";
    }
}
