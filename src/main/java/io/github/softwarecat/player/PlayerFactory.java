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

import io.github.softwarecat.Table;

public class PlayerFactory {
    public static Player getPlayer(PlayerType type, Table table) {
        switch (type) {
            case CANCELLATION:
                return new Cancellation(table);
            case FIBONACCI:
                return new Fibonacci(table);
            case MARTINGALE:
                return new Martingale(table);
            case ONE_THREE_TWO_SIX:
                return new OneThreeTwoSix(table);
            case PASSENGER57:
                return new Passenger57(table);
            case RANDOM_CHOICE:
                return new RandomChoice(table);
            case SEVEN_REDS:
                return new SevenReds(table);
            default:
                return new Player(table) {
                    @Override
                    public boolean playing() {
                        return false;
                    }

                    @Override
                    public void placeBets() {

                    }

                    @Override
                    public void newRound() {

                    }
                };
        }
    }
}
