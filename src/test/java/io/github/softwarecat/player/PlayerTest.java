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
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    Wheel wheel;

    Table table;

    Player player;

    @Before
    public void setUp() {
        wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        table = new Table(wheel);

        player = new Player(table) {
            @Override
            public boolean playing() {
                return true;
            }

            @Override
            public void placeBets() {

            }
        };
        player.stake = 100;
    }

    @Test
    public void win() {
        // Simulate placing bet
        player.stake -= 5;

        // Simulate winning bet
        player.win(new Bet(5, new Outcome("Name", 1)));

        assertEquals(100 + 5, player.stake);
    }
}