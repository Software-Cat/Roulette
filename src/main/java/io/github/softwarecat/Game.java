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

import java.util.*;

/**
 * Game manages the sequence of actions that defines the game of Roulette. This includes notifying the Player to place
 * bets, spinning the Wheel and resolving the Bets actually present on the Table.
 */
public class Game {

    // Locale
    public static final Locale LOCALE = Locale.US;
    public static final ResourceBundle BET_NAMES = ResourceBundle.getBundle("i18n.BetNames", LOCALE);

    // Inside Bet payouts
    public static final int STRAIGHT_BET_PAYOUT = 35;
    public static final int SPLIT_BET_PAYOUT = 17;
    public static final int STREET_BET_PAYOUT = 11;
    public static final int CORNER_BET_PAYOUT = 8;
    public static final int FIVE_BET_PAYOUT = 6;
    public static final int LINE_BET_PAYOUT = 5;

    // Outside Bet payouts
    public static final int DOZEN_BET_PAYOUT = 2;
    public static final int COLUMN_BET_PAYOUT = 2;
    public static final int EVEN_MONEY_BET_PAYOUT = 1;

    // Defaults
    public static final int TABLE_LIMIT = Integer.MAX_VALUE;
    public static final int TABLE_MINIMUM = 1;

    /**
     * The Wheel that returns a randomly selected Bin of Outcomes.
     */
    private final Wheel wheel;
    /**
     * The Table which contains the Bets placed by the Player.
     */
    private final Table table;

    /**
     * Constructs a new Game, using a given Wheel and Table.
     *
     * @param wheel the Wheel instance which produces random events
     * @param table the Table instance which holds bets to be resolved
     */
    public Game(Wheel wheel, Table table) {
        this.wheel = wheel;
        this.table = table;
    }

    /**
     * This will execute a single cycle of play with a given Player.
     *
     * @param player the individual player that places bets, receives winnings and pays losses
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    public void cycle(Player player) throws InvalidBetException {
        if (!player.playing()) {
            return;
        }

        player.placeBets();

        Bin winningBin = wheel.next();

        List<Bet> winningBets = new ArrayList<>();
        List<Bet> losingBets = new ArrayList<>();
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            Bet bet = it.next();
            if (winningBin.contains(bet.outcome)) {
                winningBets.add(bet);
            } else {
                losingBets.add(bet);
            }
            it.remove();
        }

        for (Bet bet : winningBets) {
            player.win(bet);
        }
        for (Bet bet : losingBets) {
            player.lose(bet);
        }

        player.roundsToGo -= 1;
    }
}
