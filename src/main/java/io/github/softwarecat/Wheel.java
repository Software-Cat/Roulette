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

import java.util.*;

/**
 * Wheel contains the 38 individual bins on a Roulette WHEEL, plus a random number generator. It can select
 * a Bin at random, simulating a spin of the Roulette WHEEL.
 */
public class Wheel {

    protected final Map<String, Outcome> ALL_OUTCOMES = new HashMap<>();

    /**
     * Contains the individual Bin instances.
     * This is always a 'new List( 38 )'.
     */
    private final List<Bin> BINS;

    /**
     * The random number generator to use to select a Bin from the bins collection.
     * This is not always simply ‘new java.util.Random()’. For testing, we would
     * inject a non-random random number generator in place of the system random number generator.
     */
    private final Random RNG;

    /**
     * Create a WHEEL that will use a the default random number generator. The java.util.Random will be
     * used. This will define the various bins and outcomes using an instance of BinBuilder.
     */
    public Wheel() {
        this(new Random());
    }

    /**
     * Create a WHEEL with the given random number generator. This will define the various bins and outcomes
     * using an instance of BinBuilder.
     *
     * @param rng a “random” number generator. For testing, this may
     *            be a non-random number generator
     */
    public Wheel(Random rng) {
        List<Bin> bins = new ArrayList<>();
        for (int i = 0; i < 38; i++) {
            bins.add(new Bin());
        }
        this.BINS = bins;

        this.RNG = rng;
    }

    /**
     * Adds the given Outcome to the Bin with the given number.
     *
     * @param bin     bin number, in the range zero to 37 inclusive
     * @param outcome the Outcome to add to this Bin
     */
    public void addOutcome(int bin, Outcome outcome) {
        // Update Bin
        BINS.get(bin).add(outcome);

        // Update Map of all possible Outcomes
        ALL_OUTCOMES.put(outcome.toString(), outcome);
    }

    /**
     * Generates a random number between 0 and 37, and returns the randomly selected Bin.
     *
     * @return a Bin selected at random from the WHEEL
     */
    public Bin next() {
        return BINS.get(RNG.nextInt(38));
    }

    /**
     * Generates a random number between 0 and 37, and returns the randomly selected Bin.
     *
     * @param bin bin number, in the range zero to 37 inclusive
     * @return the requested Bin
     */
    public Bin getBin(int bin) {
        return BINS.get(bin);
    }

    /**
     * Get all outcomes previously added to the WHEEL that contains the queried name.
     *
     * @param name the name the matching outcomes must contain
     * @return set of Outcomes with the queried name
     */
    public List<Outcome> getOutcomes(String name) {
        List<Outcome> result = new ArrayList<>();
        for (Map.Entry<String, Outcome> entry : ALL_OUTCOMES.entrySet()) {
            if (entry.getKey().contains(name)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }
}
