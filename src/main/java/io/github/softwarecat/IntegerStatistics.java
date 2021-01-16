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

import java.util.List;


/**
 * IntegerStatistics computes several simple descriptive statistics of Integer values in a List.
 * Like the java.util.Math class, this class simply has a bunch of ‘static’ methods.
 */
public class IntegerStatistics {

    /**
     * Returns the sum of elements in the {@link List<Integer>}.
     *
     * @param values the list
     * @return the sum of elements in the list
     */
    public static int sum(List<Integer> values) {
        return values.stream()
                .mapToInt(Integer::valueOf)
                .sum();
    }

    /**
     * Returns the number of elements in the list.  If the list contains
     * more than {@code Integer.MAX_VALUE} elements, returns
     * {@code Integer.MAX_VALUE}.
     *
     * @param values the list
     * @return the number of elements in the list
     */
    public static int len(List<Integer> values) {
        return values.size();
    }

    /**
     * Computes the mean of the List of Integer values.
     *
     * @param values the list of values we are summarizing
     * @return the mean of the elements in the list
     */
    public static double mean(List<Integer> values) {
        return (double) sum(values) / len(values);
    }

    /**
     * Computes the standard deviation of the List of Integer values.
     *
     * @param values the list of values we are summarizing
     * @return the standard deviation of the elements in the list
     */
    public static double std(List<Integer> values) {
        double mean = mean(values);

        double sum = 0;
        for (Integer value : values) {
            double diff = value - mean;
            sum += diff * diff;
        }

        double variance = sum / (len(values) - 1);

        return Math.sqrt(variance);
    }
}
