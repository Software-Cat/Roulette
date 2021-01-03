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
        System.out.println(bin3.toString());
    }
}