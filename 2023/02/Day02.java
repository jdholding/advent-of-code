import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day02 {
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        Map<Integer, Integer[]> draws = createMap(allLines);
        // Part 1
        // var possibleGames = findPossibleGames(draws);
        // var result = possibleGames.stream().reduce(0, Integer::sum); 
        // System.out.println("The sum of all possible gameIds is: " + result);

        var powers = findPowerSets(draws); 
        var result = powers.stream().reduce(0, Integer::sum);
        System.out.println("The sum of the power of all cubes is: " + result); 
    }

    public static Map<Integer, Integer[]> createMap(List<String> allLines) {
        Map<Integer,Integer[]> theMap = new HashMap<>();
        for (var line : allLines) {
            var gameId = Integer.parseInt(line.substring(5, line.indexOf(":")));
            Integer[] maxes = {0,0,0};
            theMap.put(gameId, maxes); 
            var game = line.substring(line.indexOf(":")+1); 
            var matches = game.split(";");
            for (var match : matches) {
                var maxList = theMap.get(gameId); 
                var draws = match.split(",");
                for (var draw : draws) {
                    if (draw.contains("red")) {
                        Integer red = Integer.parseInt(draw.replaceAll("[^0-9]", "")); 
                        if (red > maxList[0]) maxList[0] = red; 
                    }
                    if (draw.contains("green")) {
                        Integer green = Integer.parseInt(draw.replaceAll("[^0-9]", ""));
                        if (green > maxList[1]) maxList[1] = green; 
                    }
                    if (draw.contains("blue")) {
                        Integer blue = Integer.parseInt(draw.replaceAll("[^0-9]", ""));
                        if (blue > maxList[2]) maxList[2] = blue;
                    }
                } 
            }
        }
        return theMap; 
    }

    public static List<Integer> findPossibleGames(Map<Integer, Integer[]> games) {
        Integer[] bagMax = {12,13,14}; 
        List<Integer> possibleGames = new ArrayList<>();
        games.forEach((k, v) -> {if(v[0] <= bagMax[0] && v[1] <= bagMax[1] && v[2] <= bagMax[2]) possibleGames.add(k);});
        return possibleGames; 
    }

    public static List<Integer> findPowerSets(Map<Integer, Integer[]> games) {
        List<Integer> powers = new ArrayList<>(); 
        games.values().forEach(v -> powers.add(v[0] * v[1] * v[2]));
        return powers; 
    }
}
