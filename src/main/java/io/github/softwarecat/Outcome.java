package io.github.softwarecat;

import java.util.Objects;

/**
 * Outcome contains a single outcome on which a bet can be placed.
 * In Roulette, each spin of the wheel has a number of Outcomes with bets that will be paid off.
 * For example, the “1” bin has the following winning Outcomes: “1”, “Red”, “Odd”, “Low”, “Column 1”,
 * “Dozen 1-12”, “Split 1-2”, “Split 1-4”, “Street 1-2-3”, “Corner 1-2-4-5”, “Five Bet”, “Line 1-2-3-4-5-6”,
 * “00-0-1-2-3”, “Dozen 1”, “Low” and “Column 1”. All of these bets will payoff if the wheel spins a “1”.
 */
public class Outcome {

    /**
     * Holds the name of the Outcome. Examples include "1", "Red".
     */
    private final String name;

    /**
     * Holds the payout odds for this Outcome. Most odds are stated as 1:1 or 17:1, we only keep the
     * numerator (17) and assume the denominator is 1.
     */
    private final int odds;

    /**
     * Sets the instance name and odds from the parameter name and odds.
     *
     * @param name The name of this outcome
     * @param odds The payout odds of this outcome
     */
    public Outcome(String name, int odds) {
        this.name = name;
        this.odds = odds;
    }

    public String getName() {
        return name;
    }

    public int getOdds() {
        return odds;
    }

    /**
     * Multiply this Outcome‘s odds by the given amount. The product is returned.
     *
     * @param amount amount being bet
     * @return amount won based on the outcome’s odds and the amount bet
     */
    public int winAmount(int amount) {
        return odds * amount;
    }

    /**
     * Compare the name attributes of this and other.
     *
     * @param other Another Outcome to compare against
     * @return true if this name matches the other name
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Outcome outcome = (Outcome) other;
        return Objects.equals(name, outcome.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Easy-to-read representation of this outcome.
     *
     * @return String of the form ‘name (odds:1)’.
     */
    @Override
    public String toString() {
        return String.format("%s (%s:1)", name, odds);
    }
}
