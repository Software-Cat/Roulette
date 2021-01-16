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

public class PlayerRandom extends Player {

    protected final Random RNG;

    protected final List<Outcome> ALL_OUTCOMES;

    protected int baseBet = Game.TABLE_MINIMUM;

    public PlayerRandom(Table table, Random rng) {
        super(table);
        RNG = rng;
        ALL_OUTCOMES = new ArrayList<>(table.WHEEL.ALL_OUTCOMES.values());
    }

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this WHEEL to extract Outcome objects.
     *
     * @param table the table to use
     */
    public PlayerRandom(Table table) {
        this(table, new Random());
    }

    @Override
    public boolean playing() {
        return (baseBet <= stake) && (roundsToGo > 0);
    }

    @Override
    public void placeBets() throws InvalidBetException {
        Outcome outcomeToBet = ALL_OUTCOMES.get(RNG.nextInt(ALL_OUTCOMES.size()));
        table.placeBet(new Bet(baseBet, outcomeToBet));
    }
}
