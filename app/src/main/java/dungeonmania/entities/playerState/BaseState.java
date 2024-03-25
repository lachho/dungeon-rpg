package dungeonmania.entities.playerState;

public class BaseState extends PlayerState {
    public BaseState() {
        super(null);
    }

    @Override
    public String getState() {
        return "base";
    }

    // @Override
    // public void transitionBase() {
    //     // Do nothing
    // }

    // @Override
    // public void transitionInvincible() {
    //     Player player = getPlayer();
    //     player.changeState(new InvincibleState(player));
    // }

    // @Override
    // public void transitionInvisible() {
    //     Player player = getPlayer();
    //     player.changeState(new InvisibleState(player));
    // }
}
