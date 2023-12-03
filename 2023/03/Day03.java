import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day03 {
    public static char[][] grid; 
    public static void main(String[] args) {
        List<String> allLines;
        try{
            allLines = Files.readAllLines(Paths.get("realInput.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        List<char[]> gridLines = new ArrayList<>();
        for (var line : allLines) {
            gridLines.add(line.toCharArray()); 
        }
        grid = gridLines.toArray(new char[gridLines.size()][]);
 
        var partNumbers = findPartNumbers(grid); 
        var result = partNumbers.stream().reduce(0, Integer::sum);
        // System.out.println(partNumbers); 
        System.out.println("The sum of the part numbers is: " + result);
    }

    public static List<Integer> findPartNumbers(char[][] grid) {
        List<Gear> gearList = new ArrayList<>();
        for (var i = 0; i<grid.length; i++) {
            for(var j = 0; j<grid[i].length; j++) {
                if (Character.isDigit(grid[i][j])) {
                    Integer number = Character.getNumericValue(grid[i][j]);
                    var jj = j+1;  
                    while (isInBounds(i,jj) && Character.isDigit(grid[i][jj])){
                        number = number * 10 + Character.getNumericValue(grid[i][jj]);
                        jj++; 
                        if (jj == grid[i].length) break;
                    }
                    if (isEngineGear(i, j, jj-1)) {
                        // partNumbers.add(number);
                        var gearXY = gearCoordinates(i, j, jj-1);
                        var gear = new Gear(number, gearXY[0], gearXY[1]);
                        gearList.add(gear); 
                    } 
                    j = jj; 
                    
                } 
            }
        }
        var ratioList = calculateRatios(gearList);  
        return ratioList; 
    }

    public static Boolean isEnginePart(Integer i, Integer j, Integer jj) { 
        
        if ((isInBounds(i, j-1) && isSymbol(grid[i][j-1])) ||
            (isInBounds(i, jj+1) && isSymbol(grid[i][jj+1]))) return true;
            
        for (var y = j-1; y < jj+2; y++) {
            if ((isInBounds(i-1, y) && isSymbol(grid[i-1][y])) ||
                (isInBounds(i+1, y) && isSymbol(grid[i+1][y]))) return true; 
        } 
        return false;  
    }

    public static Boolean isEngineGear(Integer i, Integer j, Integer jj) { 
        int c = 0;

        if (isInBounds(i, j-1) && isGear(grid[i][j-1])) c++;
        if (isInBounds(i, jj+1) && isGear(grid[i][jj+1])) c++;
            
        for (var y = j-1; y < jj+2; y++) {
            if (isInBounds(i-1, y) && isGear(grid[i-1][y])) c++;
            if (isInBounds(i+1, y) && isGear(grid[i+1][y])) c++;
        } 
        if (c > 1) System.out.println("More than 1 star");
        if (c > 0) return true;
        return false;  
    }

    public static Integer[] gearCoordinates(Integer i, Integer j, Integer jj) {
        Integer[] gear = new Integer[2]; 
        if (isInBounds(i, j-1) && isGear(grid[i][j-1])) {
            gear[0] = i; 
            gear[1] = j-1;
            return gear; 
        }
        if (isInBounds(i, jj+1) && isGear(grid[i][jj+1])) {
            gear[0] = i; 
            gear[1] = jj+1;
            return gear;  
        }
        for (var y = j-1; y < jj+2; y++) {
            if (isInBounds(i-1, y) && isGear(grid[i-1][y])) {
                gear[0] = i-1;
                gear[1] = y;
                return gear;   
            }
        }
        for (var y = j-1; y < jj+2; y++) {
            if ((isInBounds(i+1, y) && isGear(grid[i+1][y]))) {
                gear[0] = i+1; 
                gear[1] = y;
                return gear;  
            } 
        }  
        return gear; 
    }

    public static List<Integer> calculateRatios(List<Gear> gears) {
        List<Integer> ratioList = new ArrayList<>(); 
        
        for (var i = 0; i < grid.length; i++) {
            for (var j = 0; j < grid[i].length; j++) {
                if (isGear(grid[i][j])) {
                    var ratio = 1;
                    var c = 0; 
                    for (var g : gears) {
                        if (g.getX() == i && g.getY() == j) {
                            c++;
                            ratio = ratio * g.getNumber();
                        }
                    }
                    if (c == 2) {
                        ratioList.add(ratio);
                    }
                }
            }
        }
        return ratioList; 
    }

    public static Boolean isGear(char c) {
        return Character.toString(c).equals("*");
    }

    public static Boolean isSymbol(char s) {
        return !("0123456789.".contains(Character.toString(s)));
    }

    public static Boolean isInBounds(Integer x, Integer y) {
        return (x >= 0 && 
            x <= grid.length-1 && 
            y >= 0 && 
            y <= grid[x].length-1); 
    }

    public static class Gear {
        Integer number; 
        Integer x; 
        Integer y; 
        public Gear(Integer number, Integer x, Integer y) {
            this.number = number;
            this.x = x; 
            this.y = y; 
        }

        public String toString() {
            return "Gear : " + number + " x: " + x + " y: " + y; 
        }

        public Integer getX() { return x; }
        public Integer getY() { return y; }
        public Integer getNumber() { return number; }
    }

}
