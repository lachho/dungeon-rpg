package dungeonmania.battles;

public class BattleStatistics {
    public static final double DEFAULT_DAMAGE_MAGNIFIER = 1.0;
    public static final double DEFAULT_PLAYER_DAMAGE_REDUCER = 10.0;
    public static final double DEFAULT_ENEMY_DAMAGE_REDUCER = 5.0;

    private double health;
    private double attack;
    private double defence;
    private double magnifier;
    private double reducer;
    private boolean invincible;
    private boolean enabled;

    public BattleStatistics(double health, double attack, double defence, double attackMagnifier,
            double damageReducer) {
        this.health = health;
        this.attack = attack;
        this.defence = defence;
        this.magnifier = attackMagnifier;
        this.reducer = damageReducer;
        this.invincible = false;
        this.enabled = true;
    }

    public BattleStatistics(double health, double attack, double defence, double attackMagnifier, double damageReducer,
            boolean isInvincible, boolean isEnabled) {
        this(health, attack, defence, attackMagnifier, damageReducer);
        this.invincible = isInvincible;
        this.enabled = isEnabled;
    }

    public BattleStatistics(BattleStatistics player) {
        this(player.getHealth(), player.getAttack(), player.getDefence(), player.getMagnifier(), player.getReducer());
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void addHealth(double health) {
        this.health += health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public void addAttack(double attack) {
        this.attack += attack;
    }

    public double getDefence() {
        return defence;
    }

    public void addDefence(double defence) {
        this.defence += defence;
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public double getMagnifier() {
        return magnifier;
    }

    public void setMagnifier(double magnifier) {
        this.magnifier = magnifier;
    }

    public double getReducer() {
        return reducer;
    }

    public void setReducer(double reducer) {
        this.reducer = reducer;
    }

    public boolean isInvincible() {
        return this.invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
