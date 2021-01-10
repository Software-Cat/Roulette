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

import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegerStatisticsTest {

    protected final List<Integer> DATA = List.of(9, 8, 5, 9, 9, 4, 5, 8, 10, 7, 8, 8);

    @Test
    public void sum() {
        assertEquals(90, IntegerStatistics.sum(DATA));
    }

    @Test
    public void len() {
        assertEquals(12, IntegerStatistics.len(DATA));
    }

    @Test
    public void mean() {
        assertEquals(7.5, IntegerStatistics.mean(DATA), 0.1);
    }

    @Test
    public void std() {
        assertEquals(1.88293, IntegerStatistics.std(DATA), 0.00001);
    }
}