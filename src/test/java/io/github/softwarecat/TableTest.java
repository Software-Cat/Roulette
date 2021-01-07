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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class TableTest {

    @Test
    public void placeBet() {
        Bet bet1 = new Bet(2, new Outcome("Name 1", 1));
        Bet bet2 = new Bet(4, new Outcome("Name 2", 6));

        Table table = new Table(new Wheel());

        try {
            table.placeBet(bet1);
            table.placeBet(bet2);
        } catch (InvalidBetException e) {
            fail();
        }

        ArrayList<Bet> bets = new ArrayList<>();
        for (ListIterator<Bet> it = table.iterator(); it.hasNext(); ) {
            bets.add(it.next());
        }

        assertArrayEquals(bets.toArray(), List.of(bet1, bet2).toArray());
    }
}