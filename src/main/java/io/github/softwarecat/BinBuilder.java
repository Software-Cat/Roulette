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

import java.text.MessageFormat;

public class BinBuilder {

    /**
     * Initializes the BinBuilder.
     */
    public BinBuilder() {

    }

    /**
     * Creates the Outcome instances and uses the addOutcome() method to place each Outcome in the
     * appropriate Bin of wheel.
     *
     * @param wheel the Wheel with Bins that must be populated with Outcomes
     */
    public void buildBins(Wheel wheel) {
        // Straight Bets
        for (int i = 1; i < 37; i++) {
            wheel.addOutcome(i, new Outcome(String.valueOf(i), Game.STRAIGHT_BET_PAYOUT));
        }

        wheel.addOutcome(0, new Outcome("0", Game.STRAIGHT_BET_PAYOUT));
        wheel.addOutcome(37, new Outcome("00", Game.STRAIGHT_BET_PAYOUT));

        // Split Bets
        for (int row = 0; row < 12; row++) {
            int n = 3 * row + 1;
            Outcome outcome = new Outcome(
                    MessageFormat.format(Game.LABELS.getString("split"), String.format(" %d-%d", n, n + 1)),
                    Game.SPLIT_BET_PAYOUT);

        }
    }
}
