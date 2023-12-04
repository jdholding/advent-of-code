import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day04 {
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        var inputMap = parseInput(allLines); 
        // var result1 = solvePart1(inputMap);
        // System.out.println("The sum of the match points is: " + result1); 
        var result2 = solvePart2(inputMap); 
        System.out.println("The total number of cards is: " + result2); 
    }

    public static Map<Integer, List<List<Integer>>> parseInput(List<String> allLines) {
        Map<Integer, List<List<Integer>>> theMap = new HashMap<>(); 
        for(var line : allLines) {
            List<List<Integer>> segments = new ArrayList<>(); 
            var cardId = Integer.parseInt(line.substring(5, line.indexOf(":")).replaceAll("\\s*", ""));
            var cardString = line.substring(line.indexOf(":")+1);
            var cardParts = cardString.split("\\|",2);
            
            for (var part : cardParts) {
                List<Integer> numbers = new ArrayList<>(); 
                var numStrings = part.trim().split(" "); 
                for (var numString : numStrings) {
                    if(!numString.isEmpty()){
                        numString.replaceAll("\\s*", "");
                        numbers.add(Integer.parseInt(numString.trim()));
                    }
                }
                segments.add(numbers); 
            }
            theMap.put(cardId, segments); 
        }
        return theMap; 
    }

    public static Integer solvePart1(Map<Integer, List<List<Integer>>> theMap) {
        List<Integer> points = new ArrayList<>();
        theMap.values().forEach(v -> {
            var match = 0;
            for(var chance : v.get(1)) {
                if(v.get(0).contains(chance)){
                    match++; 
                }
            }
            var cardPoints = 0; 
            if (match > 0) {
                cardPoints = match == 1 ? 1 : Double.valueOf(Math.pow(2, match-1)).intValue();
            }
            points.add(cardPoints); 
        });
        return points.stream().reduce(0, Integer::sum); 
    }

    public static Integer solvePart2(Map<Integer, List<List<Integer>>> theMap) {
        List<Integer> scores = new ArrayList<>(); 
        Map<Integer, Integer> cardWins = new HashMap<>(); 
        theMap.forEach((k,v) -> {
                Integer matches = 0; 
                for(var chance : v.get(1)) {
                    if(v.get(0).contains(chance)){
                        matches++; 
                    }
                    cardWins.put(k, matches); 
                }
        });
        return findCopies(cardWins);
    }

    public static Integer findCopies(Map<Integer, Integer> cardWins) {
        
            Integer[] inventory = new Integer[cardWins.size()]; 
            
            //iterate over the list of cards
            //a later card will never add a copy of an earlier card
            //for each card x[i] look at the number of matches m
            //iterate through x[i+1] for m rounds, each time adding 1 for the number of cards of x[i]
            for (var i=0; i<cardWins.size(); i++) {
                inventory[i] = 1;  
            }
            for (var i=0; i<cardWins.size(); i++) {
                var m = cardWins.get(i+1) != null ? cardWins.get(i+1) : 0;

                for(var j=i+1; j<=i+m && j<inventory.length; j++) {
                    inventory[j] += inventory[i];  
                } 
            }
            Integer cards = 0; 
            for (var i=0; i<inventory.length; i++) {
                cards += inventory[i]; 
            }
        return cards; 
    }

    public static void printMap(Map<Integer, List<List<Integer>>> theMap) {
        theMap.forEach((k,v) -> { System.out.println(k + " : " + v.get(0) + " | " + v.get(1));});
    }
}
