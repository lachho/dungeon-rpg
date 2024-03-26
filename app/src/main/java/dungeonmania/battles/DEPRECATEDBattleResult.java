package dungeonmania.battles;

import java.util.ArrayList;
import java.util.List;

//Deprecated MARKED FOR REMOVAL
public class DEPRECATEDBattleResult {
    public static List<BattleRound> battle(BattleStatistics self, BattleStatistics target) {
        if (self.isInvincible() ^ target.isInvincible()) {
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
        return (self.isInvincible()) ? 0 : self.getHealth();
    }

    private static void updateHealth(BattleStatistics self, double damage) {
        self.setHealth(self.getHealth() - damage);
    }

    private static void updateInvincibleHealth(BattleStatistics self) {
        self.setHealth((self.isInvincible()) ? self.getHealth() : 0);
    }
}
