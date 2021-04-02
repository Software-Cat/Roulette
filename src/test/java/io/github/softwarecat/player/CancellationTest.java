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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class CancellationTest {

    Wheel wheel;

    Table table;

    Cancellation player;

    @Before
    public void setUp() throws Exception {
        wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        table = new Table(wheel);

        player = new Cancellation(table);
        player.stake = 100;
        player.roundsToGo = 250;
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
    public void placeBetsCombLWW() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Second bet (lost last) should be 1 + 7 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Third bet (won last) should be 2 + 6 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Fourth bet (won last) should be 3 + 5 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombLWL() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Second bet (lost last) should be 1 + 7 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Third bet (won last) should be 2 + 6 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Fourth bet (lost last) should be 2 + 8 = 10
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(10, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombLLW() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Second bet (lost last) should be 1 + 7 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Third bet (lost last) should be 1 + 8 = 9
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(9, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Fourth bet (won last) should be 2 + 7 = 9
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(9, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombLLL() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Second bet (lost last) should be 1 + 7 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Third bet (lost last) should be 1 + 8 = 9
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(9, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Fourth bet (lost last) should be 1 + 9 = 10
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(10, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombWWW() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Second bet (won last) should be 2 + 5 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Third bet (won last) should be 3 + 4 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Fourth bet (won last) should be 1 + 6 = 7 again
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombWWL() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Second bet (won last) should be 2 + 5 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Third bet (won last) should be 3 + 4 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Fourth bet (lost last) should be 3 + 7 = 10
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(10, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombWLW() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Second bet (won last) should be 2 + 5 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Third bet (lost last) should be 2 + 7 = 9
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(9, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Fourth bet (won last) should be 3 + 5 = 8
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(8, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void placeBetsCombWLL() {
        // First bet should be 1 + 6 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.win(getBets().get(0));
        clearTable();

        // Second bet (won last) should be 2 + 5 = 7
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(7, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Third bet (lost last) should be 2 + 7 = 9
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(9, player.OUTCOME, player), getBets().get(0));

        player.lose(getBets().get(0));
        clearTable();

        // Fourth bet (lost last) should be 2 + 9 = 11
        try {
            player.placeBets();
        } catch (InvalidBetException e) {
            fail("Player should not fail in placing bets");
        }

        assertEquals(new Bet(11, player.OUTCOME, player), getBets().get(0));
    }

    @Test
    public void resetSequence() {
        assertEquals(6, player.sequence.size());

        player.sequence.clear();
        player.resetSequence();

        assertEquals(6, player.sequence.size());
    }

    protected List<Bet> getBets() {
        List<Bet> bets = new ArrayList<>();
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            bets.add(it.next());
        }
        return bets;
    }

    protected void clearTable() {
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            it.next();
            it.remove();
        }
    }
}