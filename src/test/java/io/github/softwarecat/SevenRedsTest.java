/*
 * MIT License
 *
 * Copyright © 2021 Bowen Wu
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
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SevenRedsTest {

    Outcome RED;
    Outcome BLACK;
    Wheel wheel;
    Table table;
    SevenReds player;

    @Before
    public void setUp() {
        wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        table = new Table(wheel);

        player = new SevenReds(table);
        player.stake = 100;

        RED = wheel.getOutcomes(Game.BET_NAMES.getString("red")).get(0);
        BLACK = wheel.getOutcomes(Game.BET_NAMES.getString("black")).get(0);
    }

    @Test
    public void placeBets() {
        // Make sure the player doesn't place bets after 1-6th Red
        for (int i = 0; i < 7; i++) {
            try {
                player.placeBets();
            } catch (InvalidBetException e) {
                fail("Player should not place invalid bet");
            }

            player.notifyWinners(Set.of(RED));

            int count = 0;
            ListIterator<Bet> iterator = table.iterator();
            for (; iterator.hasNext(); ++count) iterator.next();
            assertEquals(0, count);
        }
        // After this block player would have seen 7 Reds

        // Make sure the player starts placing bets after 7th Red
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not place invalid bet");
        }
        Bet bet = table.iterator().next();
        assertEquals(BLACK, bet.outcome);
        assertEquals(player.BASE_BET, bet.amountBet);

        // Clear the table
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            it.next();
            it.remove();
        }

        // Make sure the player doubles the bet after a loss
        player.lose(bet);
        player.notifyWinners(Set.of(RED));
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not place invalid bet");
        }
        bet = table.iterator().next();
        assertEquals(BLACK, bet.outcome);
        assertEquals(player.BASE_BET * 2, bet.amountBet);

        // Clear the table
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            it.next();
            it.remove();
        }

        // Make sure the player stops placing bets after the last 7 bets are not Red
        player.win(bet);
        player.notifyWinners(Set.of(BLACK));
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not place invalid bet");
        }
        int count = 0;
        ListIterator<Bet> iterator = table.iterator();
        for (; iterator.hasNext(); ++count) iterator.next();
        assertEquals(0, count);
    }
}