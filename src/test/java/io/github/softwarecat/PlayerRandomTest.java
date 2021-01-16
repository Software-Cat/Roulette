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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import static org.junit.Assert.*;

public class PlayerRandomTest {

    Wheel wheel;

    Table table;

    PlayerRandom player;

    Random rng = new Random(1);

    List<Outcome> ALL_OUTCOMES;

    @Before
    public void setUp() {
        wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        ALL_OUTCOMES = new ArrayList<>(wheel.ALL_OUTCOMES.values());

        table = new Table(wheel);

        player = new PlayerRandom(table, new Random(1));
        player.stake = 100;
    }

    @Test
    public void playing() {
        player.stake = 100;
        player.roundsToGo = 1;
        assertTrue(player.playing());

        player.stake = 0;
        assertFalse(player.playing());

        player.stake = 100;
        player.roundsToGo = 0;
        assertFalse(player.playing());
    }

    @Test
    public void placeBets() {
        for (int i = 0; i < 1000; i++) {
            // Generate expected outcome with known RNG
            Outcome expectedOutcome = ALL_OUTCOMES.get(rng.nextInt(ALL_OUTCOMES.size()));
            Bet expectedBet = new Bet(player.baseBet, expectedOutcome, player);

            // Player bet placing
            player.stake = player.baseBet;
            try {
                player.placeBets();
            } catch (InvalidBetException e) {
                fail("Player should not fail in placing bet");
            }

            // Get the bet player placed
            Bet actualBet = table.iterator().next();
            for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
                it.next();
                it.remove();
            }

            assertEquals(expectedBet, actualBet);
        }
    }
}