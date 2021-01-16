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

public class Player1326 extends Player {

    protected final Outcome OUTCOME;

    protected int BASE_BET = Game.TABLE_MINIMUM;

    protected State state;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public Player1326(Table table) {
        super(table);

        OUTCOME = table.wheel.getOutcomes("Black").get(0);
    }

    @Override
    public boolean playing() {
        return false;
    }

    @Override
    public void placeBets() throws InvalidBetException {

    }

    protected abstract class State {

        protected Player1326 player;

        public State(Player1326 player) {
            this.player = player;
        }

        public abstract Bet currentBet();

        public abstract State nextWon();

        public State nextLost() {
            return new NoWins(player);
        }
    }

    protected class NoWins extends State {

        public NoWins(Player1326 player) {
            super(player);
        }

        @Override
        public Bet currentBet() {
            return new Bet(player.BASE_BET, player.OUTCOME, player);
        }

        @Override
        public State nextWon() {
            return new OneWin(player);
        }
    }

    protected class OneWin extends State {

        public OneWin(Player1326 player) {
            super(player);
        }

        @Override
        public Bet currentBet() {
            return new Bet(player.BASE_BET * 3, player.OUTCOME, player);
        }

        @Override
        public State nextWon() {
            return new TwoWins(player);
        }
    }

    protected class TwoWins extends State {

        public TwoWins(Player1326 player) {
            super(player);
        }

        @Override
        public Bet currentBet() {
            return new Bet(player.BASE_BET * 2, player.OUTCOME, player);
        }

        @Override
        public State nextWon() {
            return new ThreeWins(player);
        }
    }

    protected class ThreeWins extends State {

        public ThreeWins(Player1326 player) {
            super(player);
        }

        @Override
        public Bet currentBet() {
            return new Bet(player.BASE_BET * 6, player.OUTCOME, player);
        }

        @Override
        public State nextWon() {
            return new NoWins(player);
        }
    }
}


