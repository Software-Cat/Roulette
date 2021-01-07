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

    private final Bet black;

    /**
     * Constructs the Player with a specific table for placing bets.
     *
     * @param table the Table instance on which bets are placed
     */
    public Passenger57(Table table) {
        super(table);

        Wheel tempWheel = new Wheel();
        BinBuilder tempBinBuilder = new BinBuilder();
        tempBinBuilder.buildBins(tempWheel);
        black = new Bet(5, tempWheel.getOutcomes("Black").get(0));
    }

    @Override
    public boolean playing() {
        return true;
    }

    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(black);
    }

    @Override
    public void win() {
        System.out.println("I got " + black.winAmount());
    }

    @Override
    public void lose() {
        System.out.println("I lost " + black.loseAmount());
    }
}
