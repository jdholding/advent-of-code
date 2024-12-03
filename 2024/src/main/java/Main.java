package main.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                throw new InputException("Provide a day to solve.");
            }
            int day;
            try {
                day = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                throw new InputException(String.format("The provided day ('%s') is not a valid integer.", args[0]));
            }

            boolean test = args.length > 1 && args[1].equals("test");
            String input = loadInput(day, test);
            Day daySolver = getDaySolver(day, input);

            System.out.printf("Solving day %d...%n", day);
            if (test) {
                System.out.println("Using a test input file.");
            }

            String part1Res = daySolver.part1();
            System.out.printf("Part 1: %s  %n", part1Res);

            String part2Res = daySolver.part2();
            System.out.printf("Part 2: %s %n", part2Res);
        } catch (InputException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }


    private static String loadInput(int day, boolean test) throws InputException {
        StringBuilder path = new StringBuilder();
        if (test) {
            path.append("test/resources/input/");
        } else {
            path.append("main/resources/input/");
        }
        path.append(day >= 1 && day <= 9 ? String.format("0%d", day) : Integer.toString(day));
        path.append(".txt");

        try {
            return Files.readString(new File(path.toString()).toPath());
        } catch (IOException e) {
            throw new InputException(String.format("Could not access input data for day %d.", day));
        }
    }


    private static Day getDaySolver(int day, String input) throws InputException {
        return switch (day) {
            case 1 -> new Day01(input);
            case 2 -> new Day02(input);
//            case 3 -> new Day03(input);
//            case 4 -> new Day04(input);
//            case 5 -> new Day05(input);
//            case 6 -> new Day06(input);
//            case 7 -> new Day07(input);
//            case 8 -> new Day08(input);
//            case 9 -> new Day09(input);
//            case 10 -> new Day10(input);
//            case 11 -> new Day11(input);
//            case 12 -> new Day12(input);
//            case 13 -> new Day13(input);
//            case 14 -> new Day14(input);
//            case 15 -> new Day15(input);
//            case 16 -> new Day16(input);
//            case 17 -> new Day17(input);
//            case 18 -> new Day18(input);
//            case 19 -> new Day19(input);
//            case 20 -> new Day20(input);
//            case 21 -> new Day21(input);
//            case 22 -> new Day22(input);
//            case 23 -> new Day23(input);
//            case 24 -> new Day24(input);
//            case 25 -> new Day25(input);
            default ->
                    throw new InputException(String.format("Day %d has not been solved yet, or is invalid.", day));
        };
    }

    private static class InputException extends Exception {
        public InputException(String msg) {
            super(msg);
        }
    }

}