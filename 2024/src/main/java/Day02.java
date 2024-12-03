package main.java;

import java.util.ArrayList;
import java.util.List;

public class Day02 implements Day{
    private final String[] lines;

    public Day02(String input) {
        lines = input.split("\n");
    }

    @Override
    public String part1() {
        int safeCount = 0;
        List<List<Integer>> reports = parseInput(lines);

        for(var report: reports) {
            if(checkIfSafe(report)) safeCount++;
        }
        return String.valueOf(safeCount);
    }

    @Override
    public String part2() {
        int safeCount = 0;
        List<List<Integer>> reports = parseInput(lines);

        for(var report: reports) {
            if(checkSafetyWithDampener(report)) safeCount++;
        }
        return String.valueOf(safeCount);
    }

    List<List<Integer>> parseInput(String[] lines) {
        List<List<Integer>> reports = new ArrayList<>();
        for(var line: lines) {
            List<Integer> levels = new ArrayList<>();
            String[] levelStrings = line.split(" ");
            for(var levelString: levelStrings) {
                try {
                    levels.add(Integer.parseInt(levelString));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
            reports.add(levels);
        }
        return reports;
    }

    boolean checkIfSafe(List<Integer> report) {
        int diff = 0;
        for (int i = 0; i < report.size() - 1; i++) {
            int currentDiff = report.get(i) - report.get(i + 1);
            if (Math.abs(currentDiff) < 1 || Math.abs(currentDiff) > 3) {
                return false;
            }

            // if the numbers go down (currentDiff>0), adding currentDiff + diff should always be greater than diff

            //numbers descending
            if (diff > 0 && diff + currentDiff < diff) {
                return false;
            }

            if (diff < 0 && diff + currentDiff > diff) {
                return false;
            }
            diff += currentDiff;
        }
        return true;
    }

    boolean checkSafetyWithDampener(List<Integer> report) {
        if(checkIfSafe(report)) {
            return true;
        } else {
            int unsafeIndex = findFirstUnsafeIndex(report);
            if(unsafeIndex > 0 && unsafeIndex < report.size()) {
                List<Integer> after = new ArrayList<>(report.subList(0, unsafeIndex+1));
                List<Integer> before = new ArrayList<>(report.subList(0, unsafeIndex));
                List<Integer> first = new ArrayList<>(report.subList(0, unsafeIndex-1));
                before.addAll(report.subList(unsafeIndex+1, report.size()));
                after.addAll(report.subList(unsafeIndex+2, report.size()));
                first.addAll(report.subList(unsafeIndex, report.size()));
                return checkIfSafe(before) || checkIfSafe(after) || checkIfSafe(first);
            }
            if(unsafeIndex == 0 ) {
                List<Integer> after = new ArrayList<>(report.subList(0, unsafeIndex+1));
                List<Integer> before = new ArrayList<>(report.subList(0, unsafeIndex));
                before.addAll(report.subList(unsafeIndex+1, report.size()));
                after.addAll(report.subList(unsafeIndex+2, report.size()));
                return checkIfSafe(before) || checkIfSafe(after);
            }
            if(unsafeIndex == report.size()) {
                List<Integer> before = new ArrayList<>(report.subList(0, unsafeIndex));


                return checkIfSafe(before);
            }


        return false;
        }
    }

    int findFirstUnsafeIndex(List<Integer> report) {
        int diff = 0;

        for (int i = 0; i < report.size() - 1; i++) {
            int currentDiff = report.get(i) - report.get(i + 1);
            if (Math.abs(currentDiff) < 1 || Math.abs(currentDiff) > 3) {
                return i;
            }

            // if the numbers go down (currentDiff>0), adding currentDiff + diff should always be greater than diff

            //numbers descending
            if (diff > 0 && diff + currentDiff < diff) {
                return i;
            }

            if (diff < 0 && diff + currentDiff > diff) {
                return i;
            }
            diff += currentDiff;
        }
        return 999;
    }
}
