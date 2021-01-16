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

import java.util.Random;

import static org.junit.Assert.*;

public class GameTest {

    private Wheel wheel;

    private Random random;

    @Before
    public void setUp() {
        random = new Random();
        random.setSeed(1);
        wheel = new Wheel(new Random(1));
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);
    }

    @Test
    public void cycle() {
        Table table = new Table(wheel);

        final boolean[] won = new boolean[1];
        Player player = new PlayerPassenger57(table) {
            @Override
            public void win(Bet bet) {
                won[0] = true;
            }

            @Override
            public void lose(Bet bet) {
                won[0] = false;
            }
        };

        Game game = new Game(wheel, table);

        try {
            game.cycle(player);
        } catch (InvalidBetException e) {
            fail("Player placed invalid bet.");
        }

        int wheelResult = random.nextInt(38);
        if (wheel.getBin(wheelResult).contains(wheel.getOutcomes("Black").get(0))) {
            assertTrue(won[0]);
        } else {
            assertFalse(won[0]);
        }
    }
}