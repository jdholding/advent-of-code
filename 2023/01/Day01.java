import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day01 {
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        var result = findCoordinates(allLines); 
        System.out.println("The total is: " + result);
    }

    public static Integer findCoordinates(List<String> allLines) {

        List<Integer> codes = new ArrayList<>();
        var sum = 0;  
        for (var line : allLines) {
            line = convertNumbers(line);
            List<Integer> code = new ArrayList<>(); 
            for (Character c : line.toCharArray()) {
                if (Character.isDigit(c)) {
                    code.add(Integer.parseInt(c.toString())); 
                }
            }

            int[] codeArray = {code.get(0), code.get(code.size()-1)}; 

            var numCode = 0; 
            for (var co : codeArray) {
                numCode = numCode * 10 + co; 
            }
            codes.add(numCode); 
        }
        for (var codeInt : codes) {
            sum += codeInt;
        } 
        return sum; 
    }

    public static String convertNumbers(String input) {
        String realLine = "";
        String[] numbs = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String[] numerals = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for(var i = 0; i<input.length(); i++) {
            var line = input.substring(i); 
            
            if (Character.isDigit(line.charAt(0))) {
                realLine += line.charAt(0);
                
            } else {
                for(var j = 0; j < numbs.length; j++ ) {
                if (line.startsWith(numbs[j])) {
                    realLine += numerals[j]; 
                }
            }
        }
    }
        return realLine; 
    }
}