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

/**
 * Martingale is a Player who places bets in Roulette. This player doubles their bet on every loss and resets their
 * bet to a base amount on each win.
 */
public class Martingale extends Player {

    private final Outcome BLACK;

    private final int BET_BASE_AMOUNT = 1;

    /**
     * The number of losses. This is the number of times to double the bet.
     */
    private int lossCount = 0;

    /**
     * The the bet multiplier, based on the number of losses. This starts at 1, and is reset to 1 on each win. It is doubled
     * in each loss. This is always equal to 2 to the power of lossCount.
     */
    private int betMultiple = 1;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public Martingale(Table table) {
        super(table);
        BLACK = table.wheel.getOutcomes(Game.BET_NAMES.getString("black")).get(0);
    }

    @Override
    public boolean playing() {
        return BET_BASE_AMOUNT * betMultiple <= stake;
    }

    /**
     * Updates the Table with a bet on “black”. The amount bet is 2lossCount, which is the value of betMultiple.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(new Bet(BET_BASE_AMOUNT * betMultiple, BLACK, this));
    }

    /**
     * Uses the superclass win() method to update the stake with an amount won. This method then resets loss-
     * Count to zero, and resets betMultiple to 1.
     *
     * @param bet the bet which won
     */
    @Override
    public void win(Bet bet) {
        super.win(bet);
        lossCount = 0;
        betMultiple = 1;
        System.out.println("Now I have " + stake);
    }

    /**
     * Uses the superclass lose() to do whatever bookkeeping the superclass already does. Increments lossCount
     * by 1 and doubles betMultiple.
     *
     * @param bet the bet which lost
     */
    @Override
    public void lose(Bet bet) {
        super.lose(bet);
        lossCount += 1;
        betMultiple *= 2;
        System.out.println("Now I have " + stake);
    }

    public int getLossCount() {
        return lossCount;
    }

    public int getBetMultiple() {
        return betMultiple;
    }

    public int getBetBaseAmount() {
        return BET_BASE_AMOUNT;
    }
}
