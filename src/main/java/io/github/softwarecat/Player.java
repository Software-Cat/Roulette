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

import java.util.Set;

/**
 * Player places bets in Roulette. This an abstract class, with no actual body for the placeBets() method. However,
 * this class does implement the basic win() method used by all subclasses.
 */
public abstract class Player {

    /**
     * The player’s current stake. Initialized to the player’s starting budget.
     */
    protected int stake;

    /**
     * The number of rounds left to play. Initialized by the overall simulation control to the maximum number of rounds
     * to play.
     */
    protected int roundsToGo;

    /**
     * The Table used to place individual Bets. The Table contains the current Wheel from which the player can
     * get Outcomes used to build Bets.
     */
    protected Table table;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public Player(Table table) {
        this.table = table;
    }

    /**
     * Returns true while the player is still active.
     *
     * @return true while the player is still active
     */
    public abstract boolean playing();

    /**
     * Updates the Table with the various Bets.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    public abstract void placeBets() throws InvalidBetException;

    /**
     * The game will notify a player of each spin using this method. This will be invoked even if the player places no
     * bets.
     *
     * @param outcomes the set of Outcome instances that are part of the current win
     */
    public void notifyWinners(Set<Outcome> outcomes) {

    }

    /**
     * Informs the Player that a new round has started and it should reset all its attributes.
     */
    public void newRound() {

    }

    /**
     * Notification from the Game that the Bet was a winner. The amount of money won is available via bet method
     * winAmount().
     *
     * @param bet the bet which won
     */
    public void win(Bet bet) {
        stake += bet.winAmount();
    }

    /**
     * Notification from the Game that the Bet was a loser. Note that the amount was already deducted from the stake
     * when the bet was created.
     *
     * @param bet the bet which lost
     */
    public void lose(Bet bet) {

    }
}
