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

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Wheel wheel = new Wheel();
        BinBuilder binBuilder = new BinBuilder();
        binBuilder.buildBins(wheel);

        Table table = new Table(wheel);

        Player player = new PlayerRandom(table);

        Game game = new Game(wheel, table);

        Simulator simulator = new Simulator(game, player);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Session Duration: ");
            simulator.sessionDuration = Integer.parseInt(scanner.nextLine());

            System.out.print("Initial Stake: ");
            simulator.initialStake = Integer.parseInt(scanner.nextLine());

            System.out.print("Samples: ");
            simulator.samples = Integer.parseInt(scanner.nextLine());
        }

        try {
            simulator.gather();
        } catch (InvalidBetException e) {
            System.out.println("Player placed invalid bet.");
        }

        System.out.println("Mean Duration: " + simulator.meanDuration);
        System.out.println("Duration STD: " + simulator.stdDuration);
        System.out.println("Mean Maximum: " + simulator.meanMaximum);
        System.out.println("Maximum STD: " + simulator.stdMaximum);
    }
}
