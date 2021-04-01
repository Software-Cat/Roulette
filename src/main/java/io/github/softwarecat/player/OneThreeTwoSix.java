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

package io.github.softwarecat.player;


import io.github.softwarecat.*;

import java.util.HashMap;
import java.util.Map;

/**
 * OneThreeTwoSix follows the 1-3-2-6 betting system. The player has a preferred Outcome, an even money bet like red,
 * black, even, odd, high or low. The player also has a current betting state that determines the current bet to place, and
 * what next state applies when the bet has won or lost.
 */
public class OneThreeTwoSix extends Player {

    /**
     * This is the player’s preferred Outcome. During construction, the Player must fetch this from the Wheel.
     */
    protected final Outcome OUTCOME;

    /**
     * The State factory.
     */
    protected final StateFactory STATE_FACTORY = new StateFactory(this);

    /**
     * The Base bet.
     */
    protected int baseBet = Game.TABLE_MINIMUM;

    /**
     * This is the current state of the 1-3-2-6 betting system. It will be an instance of a subclass of
     * OneThreeTwoSix.State. This will be one of the four states: No Wins, One Win, Two Wins or Three Wins.
     */
    protected State state;

    /**
     * Initializes the state and the outcome. The state is set to the initial state of an instance of
     * Player1326NoWins. The outcome is set to some even money proposition, for example "Black".
     *
     * @param table the table to use
     */
    public OneThreeTwoSix(Table table) {
        super(table);

        OUTCOME = table.WHEEL.getOutcomes(Game.BET_NAMES.getString("black")).get(0);

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
     * The enum State type.
     */
    protected enum StateType {
        /**
         * No wins state type.
         */
        NO_WINS,
        /**
         * One win state type.
         */
        ONE_WIN,
        /**
         * Two wins state type.
         */
        TWO_WINS,
        /**
         * Three wins state type.
         */
        THREE_WINS
    }

    /**
     * A Factory object that produces state objects. This Factory can retain a small pool of object instances, eliminating
     * needless object construction.
     */
    protected class StateFactory {

        /**
         * This is a map from a StateType to an instance of State.
         */
        protected Map<StateType, State> states = new HashMap<>();

        /**
         * The Player.
         */
        protected OneThreeTwoSix player;

        /**
         * Create a new mapping from the class name to object instance. There are only four objects, so this is relatively
         * simple.
         *
         * @param player the player to create the states for
         */
        public StateFactory(OneThreeTwoSix player) {
            this.player = player;
        }

        /**
         * Gets the state corresponding to the StateType.
         *
         * @param type the type of state to get
         * @return the state object
         */
        public State getState(StateType type) {
            // Lazy creation
            if (!states.containsKey(type)) {
                switch (type) {
                    case NO_WINS:
                        states.put(StateType.NO_WINS, new NoWins(player));
                        break;
                    case ONE_WIN:
                        states.put(StateType.ONE_WIN, new OneWin(player));
                        break;
                    case TWO_WINS:
                        states.put(StateType.TWO_WINS, new TwoWins(player));
                        break;
                    case THREE_WINS:
                        states.put(StateType.THREE_WINS, new ThreeWins(player));
                        break;
                }
            }

            return states.get(type);
        }
    }

    /**
     * OneThreeTwoSix.State is the superclass for all of the states in the 1-3-2-6 betting system.
     */
    protected abstract class State {

        /**
         * The OneThreeTwoSix player who is currently in this state. This player will be used to provide the Outcome that
         * will be used to create the Bet.
         */
        protected OneThreeTwoSix player;

        /**
         * The multiplier to multiply the base bet with.
         */
        protected int multiplier;

        /**
         * The constructor for this class saves the OneThreeTwoSix which will be used to provide the Outcome on which
         * we will bet.
         *
         * @param player     the player
         * @param multiplier the multiplier
         */
        public State(OneThreeTwoSix player, int multiplier) {
            this.player = player;
            this.multiplier = multiplier;
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
         * Constructs the new OneThreeTwoSix.State instance to be used when the bet was a winner.
         *
         * @return the state
         */
        public abstract State nextWon();

        /**
         * Constructs the new OneThreeTwoSix.State instance to be used when the bet was a loser. This method is the same
         * for each subclass: it creates a new instance of OneThreeTwoSix.NoWins.
         *
         * @return the state
         */
        public State nextLost() {
            return STATE_FACTORY.getState(StateType.NO_WINS);
        }
    }

    /**
     * OneThreeTwoSix.NoWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are no wins,
     * the base bet value of 1 is used.
     */
    protected class NoWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 1.
         *
         * @param player the player
         */
        public NoWins(OneThreeTwoSix player) {
            super(player, 1);
        }

        @Override
        public State nextWon() {
            return STATE_FACTORY.getState(StateType.ONE_WIN);
        }
    }

    /**
     * OneThreeTwoSix.OneWin defines the bet and state transition rules in the 1-3-2-6 betting system. When there is one wins,
     * the base bet value of 3 is used.
     */
    protected class OneWin extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 3.
         *
         * @param player the player
         */
        public OneWin(OneThreeTwoSix player) {
            super(player, 3);
        }

        @Override
        public State nextWon() {
            return STATE_FACTORY.getState(StateType.TWO_WINS);
        }
    }

    /**
     * OneThreeTwoSix.TwoWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are two
     * wins, the base bet value of 2 is used.
     */
    protected class TwoWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 2.
         *
         * @param player the player
         */
        public TwoWins(OneThreeTwoSix player) {
            super(player, 2);
        }

        @Override
        public State nextWon() {
            return STATE_FACTORY.getState(StateType.THREE_WINS);
        }
    }

    /**
     * OneThreeTwoSix.ThreeWins defines the bet and state transition rules in the 1-3-2-6 betting system. When there are
     * three wins, the base bet value of 6 is used.
     */
    protected class ThreeWins extends State {

        /**
         * Constructs a new Bet from the player’s outcome information. The bet multiplier is 6.
         *
         * @param player the player
         */
        public ThreeWins(OneThreeTwoSix player) {
            super(player, 6);
        }

        @Override
        public State nextWon() {
            return STATE_FACTORY.getState(StateType.NO_WINS);
        }
    }
}
