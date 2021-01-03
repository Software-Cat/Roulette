package io.github.softwarecat;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Bin contains a collection of Outcomes which reflect the winning bets that are paid for a particular bin on a Roulette
 * wheel. In Roulette, each spin of the wheel has a number of Outcomes. Example: A spin of 1, selects the “1” bin
 * with the following winning Outcomes: “1” , “Red” , “Odd” , “Low” , “Column 1” , “Dozen 1-12” , “Split 1-2” ,
 * “Split 1-4” , “Street 1-2-3” , “Corner 1-2-4-5” , “Five Bet” , “Line 1-2-3-4-5-6” , “00-0-1-2-3” , “Dozen 1” , “Low”
 * and “Column 1” . These are collected into a single Bin .
 */
public class Bin extends HashSet<Outcome> {

    /**
     * Instantiates an empty Bin. Outcomes can be added to it later.
     */
    public Bin() {
        super();
    }

    /**
     * Creates an Bin using the super() statement, invoking the TreeSet constructor.
     * It then loads that collection using elements of the given array.
     *
     * @param outcomes A primitive array of outcomes
     */
    public Bin(Outcome[] outcomes) {
        super(Arrays.asList(outcomes));
    }

    /**
     * Creates a Bin using the super() statement to invoke the TreeSet constructor.
     * It then loads that collection using the collection parameter.
     * This relies on the fact that all classes that implement Collection will provide the iterator(); the
     * constructor can convert the elements of the input collection to a proper Set.
     *
     * @param outcomes A collection of outcomes
     */
    public Bin(Collection<? extends Outcome> outcomes) {
        super(outcomes);
    }

    /**
     * Adds an Outcome to this Bin. This can be used by a builder to construct all of the bets in this Bin.
     * Since this class is really just a facade over the underlying collection object, this method can simply
     * delegate the real work to the underlying collection.
     *
     * @param outcome An outcome to add to this Bin
     * @return true if this set did not already contain the specified element
     */
    @Override
    public boolean add(Outcome outcome) {
        return super.add(outcome);
    }

    /**
     * An easy-to-read representation of the list of Outcomes in this Bin.
     *
     * @return String of the form ‘[outcome, outcome, ...]’.
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
