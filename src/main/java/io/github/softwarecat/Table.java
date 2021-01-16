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

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Table contains all the Bet s created by the Player. A table also has a betting limit, and the sum of all of a player’s
 * bets must be less than or equal to this limit. We assume a single Player in the simulation.
 */
public class Table {

    public final Wheel WHEEL;

    /**
     * This is a list of the Bets currently active. These will result in either wins or losses to the Player.
     */
    private final LinkedList<Bet> bets = new LinkedList<>();

    /**
     * This is the table limit. The sum of the bets from a Player must be less than or equal to this limit.
     */
    private final int LIMIT;

    /**
     * This is the table minimum. Each individual bet from a Player must be greater than this limit.
     */
    private final int MINIMUM;

    /**
     * Instantiates a new Table.
     * Creates an empty list of bets.
     */
    public Table(Wheel wheel) {
        this(wheel, Game.TABLE_LIMIT, Game.TABLE_MINIMUM);
    }

    public Table(Wheel wheel, int limit, int minimum) {
        this.WHEEL = wheel;
        this.LIMIT = limit;
        this.MINIMUM = minimum;
    }

    /**
     * Validates the table-limit rules:
     * The sum of all bets is less than or equal to the table limit.
     * All bet amounts are greater than or equal to the table minimum.
     *
     * @throws InvalidBetException if the bets don’t pass the table limit rules
     */
    public void validate() throws InvalidBetException {
        // Minimum check
        for (Bet bet : bets) {
            if (bet.amountBet < MINIMUM) {
                throw new InvalidBetException();
            }
        }

        // Maximum check
        int sum = 0;
        for (Bet bet : bets) {
            sum += bet.amountBet;
        }
        if (sum > LIMIT) {
            throw new InvalidBetException();
        }
    }

    /**
     * Adds this bet to the list of working bets.
     *
     * @param bet a Bet instance to be added to the table
     * @throws InvalidBetException if the bets don’t pass the table limit rules
     */
    public void placeBet(Bet bet) throws InvalidBetException {
        if (bet.parent != null) {
            if (bet.parent.stake >= bet.amountBet) {
                bet.parent.stake -= bet.amountBet;
            } else {
                throw new InvalidBetException();
            }
        }

        bets.add(bet);

        validate();
    }

    /**
     * Returns a ListIterator over the available list of Bet instances.
     *
     * @return iterator over all bets
     */
    public ListIterator<Bet> iterator() {
        return bets.listIterator();
    }

    /**
     * @return String representation of all current bets.
     */
    @Override
    public String toString() {
        return bets.toString();
    }
}
