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

import java.util.Set;

/**
 * PlayerSevenReds is a PlayerMartingale player who places bets in Roulette. This player waits until the WHEEL has spun red
 * seven times in a row before betting black.
 */
public class PlayerSevenReds extends PlayerMartingale {

    /**
     * The Outcome Red.
     */
    protected final Outcome RED;

    /**
     * The number of reds yet to go. This starts at 7 , is reset to 7 on each non-red outcome, and decrements by 1 on
     * each red outcome.
     */
    protected int redCount = 0;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this WHEEL to extract Outcome objects.
     *
     * @param table the table to use
     */
    public PlayerSevenReds(Table table) {
        super(table);
        RED = table.WHEEL.getOutcomes(Game.BET_NAMES.getString("red")).get(0);
    }

    /**
     * If redCount is zero, this places a bet on black, using the bet multiplier.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    @Override
    public void placeBets() throws InvalidBetException {
        if (redCount >= 7) {
            super.placeBets();
        }
    }

    /**
     * This is notification from the Game of all the winning outcomes. If this vector includes red, redCount is
     * decremented. Otherwise, redCount is reset to 7.
     *
     * @param outcomes the Outcome set from a Bin
     */
    @Override
    public void notifyWinners(Set<Outcome> outcomes) {
        if (outcomes.contains(RED)) {
            redCount++;
        } else {
            redCount = 0;
        }
    }

    @Override
    public void newRound() {
        super.newRound();
        redCount = 0;
    }
}
