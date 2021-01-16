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

    protected int baseBet = Game.TABLE_MINIMUM;

    protected State state;

    /**
     * Constructs the Player with a specific Table for placing Bets.
     * Since the table has access to the Wheel, we can use this wheel to extract Outcome objects.
     *
     * @param table the table to use
     */
    public Player1326(Table table) {
        super(table);

        OUTCOME = table.wheel.getOutcomes(Game.BET_NAMES.getString("black")).get(0);

        state = new NoWins(this);
    }

    @Override
    public boolean playing() {
        return (stake >= state.currentBet().amountBet) && (roundsToGo > 0);
    }

    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(state.currentBet());
    }

    @Override
    public void win(Bet bet) {
        super.win(bet);
        state = state.nextWon();
    }

    @Override
    public void lose(Bet bet) {
        super.lose(bet);
        state = state.nextLost();
    }

    protected abstract class State {

        protected Player1326 player;

        protected int multiplier;

        protected State nextStateWin;

        public State(Player1326 player, int multiplier, State nextStateWin) {
            this.player = player;
            this.multiplier = multiplier;
            this.nextStateWin = nextStateWin;
        }

        public Bet currentBet() {
            return new Bet(player.baseBet * multiplier, player.OUTCOME, player);
        }

        public State nextWon() {
            return nextStateWin;
        }

        public State nextLost() {
            return new NoWins(player);
        }
    }

    protected class NoWins extends State {

        public NoWins(Player1326 player) {
            super(player, 1, new OneWin(player));
        }
    }

    protected class OneWin extends State {

        public OneWin(Player1326 player) {
            super(player, 3, new TwoWins(player));
        }
    }

    protected class TwoWins extends State {

        public TwoWins(Player1326 player) {
            super(player, 2, new ThreeWins(player));
        }
    }

    protected class ThreeWins extends State {

        public ThreeWins(Player1326 player) {
            super(player, 6, new NoWins(player));
        }
    }
}


