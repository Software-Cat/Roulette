/*
 * MIT License
 *
 * Copyright © 2021 Bowen Wu. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.softwarecat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * PlayerRandom is a Player who places bets in Roulette. This player makes random bets around the layout.
 */
public class PlayerRandom extends Player {

    /**
     * A Random Number Generator which will return the next random number.
     * When writing unit tests, we will want to patch this with a mock object to return a known sequence of bets.
     */
    protected final Random RNG;

    /**
     * All possible outcomes.
     */
    protected final List<Outcome> ALL_OUTCOMES;

    /**
     * The Base bet.
     */
    protected int baseBet = Game.TABLE_MINIMUM;

    /**
     * This uses the super() construct to invoke the superclass constructor using the Table.
     * <p>
     * It will also use the wheel associated with the table to get the set of bins. The set of bins is then used to create the
     * pool of outcomes for creating bets.
     *
     * @param table the Table which will accept the bests
     * @param rng   the random number generator
     */
    public PlayerRandom(Table table, Random rng) {
        super(table);
        RNG = rng;
        ALL_OUTCOMES = new ArrayList<>(table.WHEEL.ALL_OUTCOMES.values());
    }

    /**
     * This uses the super() construct to invoke the superclass constructor using the Table.
     * <p>
     * This will create a random.Random random number generator.
     * <p>
     * It will also use the wheel associated with the table to get the set of bins. The set of bins is then used to create the
     * pool of outcomes for creating bets.
     *
     * @param table the Table which will accept the bests
     */
    public PlayerRandom(Table table) {
        this(table, new Random());
    }

    @Override
    public boolean playing() {
        return (baseBet <= stake) && (roundsToGo > 0);
    }

    /**
     * Updates the Table with a randomly placed bet.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    @Override
    public void placeBets() throws InvalidBetException {
        Outcome outcomeToBet = ALL_OUTCOMES.get(RNG.nextInt(ALL_OUTCOMES.size()));
        table.placeBet(new Bet(baseBet, outcomeToBet, this));
    }
}
