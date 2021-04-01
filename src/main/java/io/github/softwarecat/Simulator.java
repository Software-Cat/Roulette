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

import io.github.softwarecat.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulator exercises the Roulette simulation with a given Player placing bets. It reports raw statistics on a number
 * of sessions of play.
 */
public class Simulator {

    /**
     * The duration value to use when initializing a Player for a session. A default value of 250 is a good choice
     * here.
     */
    public int sessionDuration = 250;

    /**
     * The stake value to use when initializing a Player for a session. This is a count of the number of bets placed;
     * i.e., 100 $10 bets is $1000 stake. A default value of 100 is sensible.
     */
    public int initialStake = Game.TABLE_MINIMUM * 100;

    /**
     * The number of game cycles to simulate. A default value of 50 makes sense.
     */
    public int samples = 50;

    public double meanDuration;

    public double stdDuration;

    public double meanMaximum;

    public double stdMaximum;

    /**
     * A List of lengths of time the Player remained in the game. Each session of play producrs a duration metric,
     * which are collected into this list.
     */
    protected List<Integer> durations = new ArrayList<>();

    /**
     * A List of maximum stakes for each Player. Each session of play producers a maximum stake metric, which
     * are collected into this list.
     */
    protected List<Integer> maxima = new ArrayList<>();

    /**
     * The Player; essentially, the betting strategy we are simulating.
     */
    protected Player player;

    /**
     * The casino game we are simulating. This is an instance of Game, which embodies the various rules, the Table
     * and the Wheel.
     */
    protected Game game;

    /**
     * Saves the Player and Game instances so we can gather statistics on the performance of the player’s betting
     * strategy.
     *
     * @param game   the Game we’re simulating. This includes the Table and Wheel
     * @param player the Player. This encapsulates the betting strategy
     */
    public Simulator(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    /**
     * Executes a single game session. The Player is initialized with their initial stake and initial cycles to go. An
     * empty List of stake values is created. The session loop executes until the Player playing() returns false.
     * This loop executes the Game cycle(); then it gets the stake from the Player and appends this amount to the
     * List of stake values. The List of individual stake values is returned as the result of the session of play.
     *
     * @return list of stake values
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    public List<Integer> session() throws InvalidBetException {
        player.stake = initialStake;
        player.roundsToGo = sessionDuration;
        player.newRound();

        List<Integer> stakeRecords = new ArrayList<>();
        for (int i = 0; i < sessionDuration; i++) {
            if (!player.playing()) {
                break;
            }

            game.cycle(player);
            stakeRecords.add(player.stake);
        }

        return stakeRecords;
    }

    /**
     * Executes the number of games sessions in samples. Each game session returns a List of stake values. When
     * the session is over (either the play reached their time limit or their stake was spent), then the length of the session
     * List and the maximum value in the session List are the resulting duration and maximum metrics. These two
     * metrics are appended to the durations list and the maxima list.
     *
     * @throws InvalidBetException if the Player attempts to place a bet which exceeds the table’s limit
     */
    public void gather() throws InvalidBetException {
        for (int i = 0; i < samples; i++) {
            List<Integer> sessionResult = session();

            durations.add(sessionResult.size());

            maxima.add(sessionResult.stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(initialStake));
        }

        meanDuration = IntegerStatistics.mean(durations);
        stdDuration = IntegerStatistics.std(durations);

        meanMaximum = IntegerStatistics.mean(maxima);
        stdMaximum = IntegerStatistics.std(maxima);
    }
}
