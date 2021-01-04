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

import java.util.Locale;
import java.util.ResourceBundle;

public class Game {

    // Locale
    public static final Locale LOCALE = Locale.US;
    public static final ResourceBundle LABELS = ResourceBundle.getBundle("i18n.BetNames", LOCALE);

    // Inside Bet payouts
    public static final int STRAIGHT_BET_PAYOUT = 35;
    public static final int SPLIT_BET_PAYOUT = 17;
    public static final int STREET_BET_PAYOUT = 11;
    public static final int CORNER_BET_PAYOUT = 8;
    public static final int FIVE_BET_PAYOUT = 6;
    public static final int LINE_BET_PAYOUT = 5;

    // Outside Bet payouts
    public static final int DOZEN_BET_PAYOUT = 2;
    public static final int COLUMN_BET_PAYOUT = 2;
    public static final int EVEN_MONEY_BET_PAYOUT = 1;
}
