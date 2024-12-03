package main.java;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Day01 implements Day {
    private final String[] lines;

    public Day01(String input) {
        lines = input.split("\n");
    }

    public Map<String, List<Integer>> buildLists(){
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : lines) {
            String[] subs = line.split(" ",2);
            left.add(Integer.parseInt(subs[0].trim()));
            right.add(Integer.parseInt(subs[1].trim()));
        }
        left.sort(Comparator.naturalOrder());
        right.sort(Comparator.naturalOrder());
        return Map.of("left", left, "right", right);
    }

    @Override
    public String part1(){
        int result = 0;
        var listMap = buildLists();
        List<Integer> left = listMap.get("left");
        List<Integer> right = listMap.get("right");

        for(var i = 0; left.size() > i; i++) {
            result += Math.abs(left.get(i) - right.get(i));
        }

        return String.valueOf(result);
    }

    @Override
    public String part2(){
        int result = 0;
        var listMap = buildLists();
        List<Integer> left = listMap.get("left");
        List<Integer> right = listMap.get("right");
        for(Integer leftEntry : left) {
            var similarity = 0;
            for(Integer rightEntry : right) {
                if(leftEntry.equals(rightEntry)) similarity += 1;
            }
            result += leftEntry * similarity;
            right.removeAll(List.of(leftEntry));
        }
        return String.valueOf(result);
    }
}
