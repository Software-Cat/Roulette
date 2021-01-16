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

/**
 * Bet associates an amount with an Outcome and a Player.
 */
public class Bet {

    /**
     * The amount of the bet.
     */
    public int amountBet;

    /**
     * The Outcome on which the bet is placed.
     */
    public Outcome outcome;

    /**
     * The player who created the bet.
     */
    public Player parent;

    public Bet(int amountBet, Outcome outcome) {
        this.amountBet = amountBet;
        this.outcome = outcome;
        this.parent = null;
    }

    /**
     * Create a new Bet of a specific amount on a specific outcome.
     *
     * @param amountBet the amount of the bet
     * @param outcome   the Outcome we are betting on
     */
    public Bet(int amountBet, Outcome outcome, Player parent) {
        this.amountBet = amountBet;
        this.outcome = outcome;
        this.parent = parent;
    }

    /**
     * Uses the Outcome‘s winAmount to compute the amount won, given the amount of this bet. Note that the
     * amount bet must also be added in. A 1:1 outcome (e.g. a bet on Red) pays the amount bet plus the amount won.
     *
     * @return amount won
     */
    public int winAmount() {
        return amountBet + outcome.winAmount(amountBet);
    }

    /**
     * Returns the amount bet as the amount lost. This is the cost of placing the bet.
     *
     * @return amount lost
     */
    public int loseAmount() {
        return amountBet;
    }

    /**
     * Returns a string representation of this bet. Note that this method will delegate the much of the work to the
     * toString() method of the Outcome.
     *
     * @return string representation of this bet with the form "amount on outcome"
     */
    @Override
    public String toString() {
        return amountBet + " on " + outcome;
    }
}
