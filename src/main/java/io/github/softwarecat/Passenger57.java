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
 * Passenger57 constructs a Bet based on the Outcome named "Black".
 * This is a very persistent player indeed.
 */
public class Passenger57 extends Player {

    protected final Outcome BLACK;

    protected final int BET_AMOUNT = Game.TABLE_MINIMUM;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public Passenger57(Table table) {
        super(table);

        BLACK = table.wheel.getOutcomes(Game.BET_NAMES.getString("black")).get(0);
    }

    @Override
    public boolean playing() {
        return (stake >= BET_AMOUNT) && (roundsToGo > 0);
    }

    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(new Bet(BET_AMOUNT, BLACK, this));
    }

    @Override
    public void newRound() {

    }

    @Override
    public void win(Bet bet) {
        super.win(bet);
    }

    @Override
    public void lose(Bet bet) {
        super.lose(bet);
    }
}
