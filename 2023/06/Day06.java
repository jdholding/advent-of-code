import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day06 {
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        List<List<Integer>> parsed = parseInput(allLines);
        Integer result = solvePart1(parsed); 
        System.out.println(result);

        Long[] timeDist = parseForPartTwo(allLines);
        Integer result2 = solvePartTwo(timeDist); 
        System.out.println(result2); 
    }

    public static List<List<Integer>> parseInput(List<String> allLines) {
        Pattern intPattern = Pattern.compile("\\d+");
        List<List<Integer>> timeDist = new ArrayList<>();
        
        for (var line : allLines) {
            List<Integer> oneLine = new ArrayList<>(); 
            Matcher matcher = intPattern.matcher(line); 

            while (matcher.find()) {
                Integer num = Integer.parseInt(matcher.group());
                oneLine.add(num); 
            }
            timeDist.add(oneLine); 
        }

        return timeDist; 
    }

    public static Integer solvePart1(List<List<Integer>> td) {
        var times = td.get(0); 
        var distances = td.get(1); 
        Integer result = 1; 

        for (var i=0; i<times.size(); i++) {
            var t = times.get(i); 
            var d = distances.get(i); 
            Integer count = 0; 

            for (var j=0; j<times.get(i); j++) {
                if (t*j > d) count += 1;
                t--;  
            }
            result *= count; 
        }
        return result; 
    }

    public static Long[] parseForPartTwo(List<String> allLines) {
        Long[] timeDist = new Long[2];
        Pattern intPattern = Pattern.compile("\\d+");
             
        for (var i=0; i<allLines.size(); i++) {
            List<Long> oneLine = new ArrayList<>(); 
            Matcher matcher = intPattern.matcher(allLines.get(i)); 
            String intString = ""; 
            while (matcher.find()) {
                intString += matcher.group();
            }
            timeDist[i] = Long.parseLong(intString);
        }
        return timeDist; 
    }

    public static Integer solvePartTwo(Long[] td) {
            var t = td[0]; 
            var d = td[1]; 
            Integer count = 0; 

            for (var j=0; j<td[0]; j++) {
                if (t*j > d) count += 1;
                t--;  
            }
        return count; 
    }
}
