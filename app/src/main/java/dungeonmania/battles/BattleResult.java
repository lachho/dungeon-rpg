package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;

public class BattleResult {
    public static List<BattleRound> battle(BattleStatistics self, BattleStatistics target) {
        if (self.invincible ^ target.invincible) {
            return battleOneInvincible(self, target);
        }

        return battleNeitherOrBothInvincible(self, target);
    }

    private static List<BattleRound> battleOneInvincible(BattleStatistics self, BattleStatistics target) {
        List<BattleRound> rounds = new ArrayList<>();

        double damageOnSelf = calculateInvincibleDamage(self);
        double damageOnTarget = calculateInvincibleDamage(target);

        updateInvincibleHealth(self);
        updateInvincibleHealth(target);

        rounds.add(new BattleRound(-damageOnSelf, -damageOnTarget));
        return rounds;
    }

    private static List<BattleRound> battleNeitherOrBothInvincible(BattleStatistics self, BattleStatistics target) {
        List<BattleRound> rounds = new ArrayList<>();

        while (self.getHealth() > 0 && target.getHealth() > 0) {
            double damageOnSelf = calculateDamage(target, self);
            double damageOnTarget = calculateDamage(self, target);

            updateHealth(self, damageOnSelf);
            updateHealth(target, damageOnTarget);

            rounds.add(new BattleRound(-damageOnSelf, -damageOnTarget));
        }

        return rounds;
    }

    private static double calculateDamage(BattleStatistics target, BattleStatistics self) {
        return target.getMagnifier() * (target.getAttack() - self.getDefence()) / self.getReducer();
    }

    private static double calculateInvincibleDamage(BattleStatistics self) {
        return (self.invincible) ? 0 : self.getHealth();
    }

    private static void updateHealth(BattleStatistics self, double damage) {
        self.setHealth(self.getHealth() - damage);
    }

    private static void updateInvincibleHealth(BattleStatistics self) {
        self.setHealth((self.invincible) ? self.getHealth() : 0);
    }

    public static BattleStatistics applyBuff(BattleStatistics origin, BattleStatistics buff) {
        return new BattleStatistics(origin.health + buff.health, origin.attack + buff.attack,
                origin.defence + buff.defence, origin.magnifier, origin.reducer, buff.isInvincible(), buff.isEnabled());
    }


}
