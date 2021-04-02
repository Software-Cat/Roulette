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

package io.github.softwarecat.player;

import io.github.softwarecat.*;

/**
 * Fibonacci uses the Fibonacci betting system. This player allocates their available budget into a sequence
 * of bets that have an accelerating potential gain.
 */
public class Fibonacci extends Player {

    /**
     * This is the player’s preferred Outcome.
     */
    protected final Outcome BLACK;

    /**
     * This is the most recent bet amount. Initially, this is 1.
     */
    protected int current = 1;

    /**
     * This is the bet amount previous to the most recent bet amount. Initially, this is zero.
     */
    protected int previous = 0;

    /**
     * Initialize the Fibonacci player.
     *
     * @param table the table to use
     */
    public Fibonacci(Table table) {
        super(table);

        BLACK = table.WHEEL.getOutcomes(Game.BET_NAMES.getString("black")).get(0);
    }

    @Override
    public boolean playing() {
        return (current <= stake) && (roundsToGo > 0);
    }

    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(new Bet(current, BLACK, this));
    }

    @Override
    public void newRound() {
        current = 1;
        previous = 0;
    }

    /**
     * Uses the superclass method to update the stake with an amount won. It resets recent and previous to their
     * initial values of 1 and 0.
     *
     * @param bet the bet which won
     */
    @Override
    public void win(Bet bet) {
        super.win(bet);

        current = 1;
        previous = 0;
    }

    /**
     * Uses the superclass method to update the stake with an amount lost. This will go “forwards” in the sequence. It
     * updates recent and previous as follows.<p>
     * next -> recent + previous<p>
     * previous -> recent<p>
     * recent -> next<p>
     *
     * @param bet the bet which lost
     */
    @Override
    public void lose(Bet bet) {
        super.lose(bet);

        int next = current + previous;
        previous = current;
        current = next;
    }
}
