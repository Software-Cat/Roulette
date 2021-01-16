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

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SimulatorTest {

    Wheel wheel;

    Table table;

    PlayerPassenger57 player;

    Game game;

    Simulator simulator;

    Random rng;

    @Before
    public void setUp() {
        rng = new Random(1);

        wheel = new Wheel(new Random(1));
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        table = new Table(wheel);

        player = new PlayerPassenger57(table);

        game = new Game(wheel, table);

        simulator = new Simulator(game, player);
    }

    @Test
    public void session() {
        List<Integer> sessionResult = null;
        try {
            sessionResult = simulator.session();
        } catch (InvalidBetException e) {
            fail("Player is broken, should not place invalid bet");
        }

        assertEquals(simulator.sessionDuration, sessionResult.size());

        int expectedStake = simulator.initialStake;
        for (int result : sessionResult) {
            Bin winningBin = wheel.getBin(rng.nextInt(38));
            boolean shouldWin = winningBin.contains(wheel.getOutcomes(Game.BET_NAMES.getString("black")).get(0));

            if (shouldWin) {
                expectedStake += player.baseBet;
            } else {
                expectedStake -= player.baseBet;
            }

            assertEquals(expectedStake, result);
        }
    }

    @Test
    public void gather() {
        simulator = new Simulator(game, player) {

            int sessionCount = 1;

            @Override
            public List<Integer> session() {
                return IntStream.range(0, sessionCount++).boxed().collect(Collectors.toList());
            }
        };

        try {
            simulator.gather();
        } catch (InvalidBetException e) {
            fail("Unit test for simulator.gather() is broken");
        }

        assertEquals(IntStream.range(0, simulator.samples)
                        .boxed()
                        .collect(Collectors.toList()),
                simulator.maxima);
        assertEquals(IntStream.rangeClosed(1, simulator.samples)
                        .boxed()
                        .collect(Collectors.toList()),
                simulator.durations);
    }
}