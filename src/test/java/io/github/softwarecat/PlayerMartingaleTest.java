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

import java.util.ListIterator;

import static org.junit.Assert.*;

public class PlayerMartingaleTest {

    Wheel wheel;

    Table table;

    PlayerMartingale player;

    @Before
    public void setUp() {

        wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        table = new Table(wheel);
        player = new PlayerMartingale(table);
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
        // Initial bet should be normal
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bet under the designated test condition.");
        }

        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            Bet bet = it.next();
            assertEquals(player.BASE_BET, bet.amountBet);
            it.remove();
        }

        // Bet after losing should be doubled
        player.lose(new Bet(1, new Outcome("Name", 1)));

        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bet under the designated test condition.");
        }

        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            Bet bet = it.next();
            assertEquals(player.BASE_BET * 2, bet.amountBet);
            it.remove();
        }

        // Bet after winning should reset
        player.win(new Bet(1, new Outcome("Name", 1)));

        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bet under the designated test condition.");
        }

        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            Bet bet = it.next();
            assertEquals(player.BASE_BET, bet.amountBet);
            it.remove();
        }
    }
}