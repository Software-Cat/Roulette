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
 * Player1326 follows the 1-3-2-6 betting system. The player has a preferred Outcome, an even money bet like red,
 * black, even, odd, high or low. The player also has a current betting state that determines the current bet to place, and
 * what next state applies when the bet has won or lost.
 */
public class Player1326 extends Player {

    /**
     * This is the player’s preferred Outcome. During construction, the Player must fetch this from the Wheel.
     */
    protected final Outcome OUTCOME;

    /**
     * The Base bet.
     */
    protected int baseBet = Game.TABLE_MINIMUM;

    /**
     * This is the current state of the 1-3-2-6 betting system. It will be an instance of a subclass of
     * Player1326.State. This will be one of the four states: No Wins, One Win, Two Wins or Three Wins.
     */
    protected State state;

    /**
     * Initializes the state and the outcome. The state is set to the initial state of an instance of
     * Player1326NoWins. The outcome is set to some even money proposition, for example "Black".
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

    /**
     * Updates the Table with a bet created by the current state. This method delegates the bet creation to state
     * object’s currentBet() method.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit.
     */
    @Override
    public void placeBets() throws InvalidBetException {
        table.placeBet(state.currentBet());
    }

    /**
     * Uses the superclass method to update the stake with an amount won. Uses the current state to determine what
     * the next state will be by calling state‘s objects nextWon() method and saving the new state in state
     *
     * @param bet the Bet which won
     */
    @Override
    public void win(Bet bet) {
        super.win(bet);
        state = state.nextWon();
    }

    /**
     * Uses the current state to determine what the next state will be. This method delegates the next state decision to
     * state object’s nextLost() method, saving the result in state.
     *
     * @param bet the Bet which lost
     */
    @Override
    public void lose(Bet bet) {
        super.lose(bet);
        state = state.nextLost();
    }

    /**
     * Player1326.State is the superclass for all of the states in the 1-3-2-6 betting system.
     */
    protected abstract class State {

        /**
         * The Player1326 player who is currently in this state. This player will be used to provide the Outcome that
         * will be used to create the Bet.
         */
        protected Player1326 player;

        /**
         * The multiplier to multiply the base bet with.
         */
        protected int multiplier;

        /**
         * The next state to move to in the case of a win.
         */
        protected State nextStateWin;

        /**
         * The constructor for this class saves the Player1326 which will be used to provide the Outcome on which
         * we will bet.
         *
         * @param player       the player
         * @param multiplier   the multiplier
         * @param nextStateWin the next state to move to in the case of a win
         */
        public State(Player1326 player, int multiplier, State nextStateWin) {
            this.player = player;
            this.multiplier = multiplier;
            this.nextStateWin = nextStateWin;
        }

        /**
         * Constructs a new Bet from the player’s preferred Outcome. Each subclass has a different multiplier used when
         * creating this Bet.
         *
         * @return the bet
         */
        public Bet currentBet() {
            return new Bet(player.baseBet * multiplier, player.OUTCOME, player);
        }

        /**
         * Constructs the new Player1326.State instance to be used when the bet was a winner.
         *
         * @return the state
         */
        public State nextWon() {
            return nextStateWin;
        }

        /**
         * Constructs the new Player1326.State instance to be used when the bet was a loser. This method is the same
         * for each subclass: it creates a new instance of Player1326.NoWins.
         *
         * @return the state
         */
        public State nextLost() {
            return new NoWins(player);
        }
    }

    /**
     * Player1326.NoWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are no wins,
     * the base bet value of 1 is used.
     */
    protected class NoWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 1.
         *
         * @param player the player
         */
        public NoWins(Player1326 player) {
            super(player, 1, new OneWin(player));
        }
    }

    /**
     * Player1326.OneWin defines the bet and state transition rules in the 1-3-2-6 betting system. When there is one wins,
     * the base bet value of 3 is used.
     */
    protected class OneWin extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 3.
         *
         * @param player the player
         */
        public OneWin(Player1326 player) {
            super(player, 3, new TwoWins(player));
        }
    }

    /**
     * Player1326.TwoWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are two
     * wins, the base bet value of 2 is used.
     */
    protected class TwoWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 2.
         *
         * @param player the player
         */
        public TwoWins(Player1326 player) {
            super(player, 2, new ThreeWins(player));
        }
    }

    /**
     * Player1326.ThreeWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are
     * three wins, the base bet value of 6 is used.
     */
    protected class ThreeWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 6.
         *
         * @param player the player
         */
        public ThreeWins(Player1326 player) {
            super(player, 6, new NoWins(player));
        }
    }
}


