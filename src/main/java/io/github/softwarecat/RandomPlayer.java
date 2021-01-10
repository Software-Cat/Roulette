/*
 * MIT License
 *
 * Copyright © 2021 Bowen Wu
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

public class RandomPlayer extends Player {

    protected static final int BET_AMOUNT = Game.TABLE_MINIMUM;

    protected final Random RNG;

    protected final List<Outcome> ALL_OUTCOMES;

    public RandomPlayer(Table table, Random rng) {
        super(table);
        RNG = rng;
        ALL_OUTCOMES = new ArrayList<>(table.wheel.allOutcomes.values());
    }

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public RandomPlayer(Table table) {
        this(table, new Random());
    }

    @Override
    public boolean playing() {
        return (BET_AMOUNT <= stake) && (roundsToGo > 0);
    }

    @Override
    public void placeBets() throws InvalidBetException {
        Outcome outcomeToBet = ALL_OUTCOMES.get(RNG.nextInt(ALL_OUTCOMES.size()));
        table.placeBet(new Bet(BET_AMOUNT, outcomeToBet));
    }
}
