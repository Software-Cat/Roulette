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

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class BinTest {

    @Test
    public void testConstructor() {
        Outcome outcome1 = new Outcome("Name 1", 1);
        Outcome outcome2 = new Outcome("Name 2", 2);
        Outcome[] outcomes = new Outcome[]{outcome1, outcome2};

        Bin bin1 = new Bin();

        Bin bin2 = new Bin(outcomes);

        Bin bin3 = new Bin(new ArrayList<>(Arrays.asList(outcomes)));
    }
}