import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day8{
    public static void main(String[] args){
        List<String> allLines;
        try{
            // Scanner sc = new Scanner(System.in);
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e){
            e.printStackTrace();
            return; 
        }
        Integer squareSize = allLines.size(); 
        Integer visible = (2 * allLines.size()) + (2 * (allLines.get(0).length()-2)) ;
        Integer[][] theGrid = new Integer[allLines.size()][allLines.size()]; 
        for(var i=0; i<allLines.size(); i++){
            var line = allLines.get(i).trim().toCharArray();
            for(var j=0; j<line.length; j++){
                theGrid[i][j]= Character.getNumericValue(line[j]);
            }
        }

        Integer longestView = 0;

        for(var i=1; i<theGrid.length-1; i++){
            for(var j=1; j<theGrid.length-1; j++){
            //    System.out.println(" the tree ["+i+"]["+j+"] has view distance "+ calculateViewDistance(theGrid, i, j));
                if(calculateViewDistance(theGrid, i, j) > longestView){
                    longestView = calculateViewDistance(theGrid, i, j);
                }
            }
        }

        System.out.println("The longest possible view is: " + longestView);

        for(var i=1; i<theGrid.length-1; i++){
            for(var j=1; j<theGrid.length-1; j++){
                if(isVisibleFromLeft(theGrid, i, j) ||
                    isVisibleFromRight(theGrid, i, j) ||
                    isVisibleFromAbove(theGrid, i,  j) ||
                    isVisibleFromBelow(theGrid, i, j)){
                        visible++;
                    }
            }
        }

        System.out.println("There are " + visible + " trees visible from the exterior");
    }

    public static Integer calculateViewDistance(Integer[][] theGrid, Integer x, Integer y){
        return (viewDistanceLeft(theGrid, x, y) * viewDistanceRight(theGrid, x, y) * viewDistanceAbove(theGrid, x, y) * viewDistanceBelow(theGrid, x, y));
    }

    public static Integer viewDistanceLeft(Integer[][] theGrid, Integer x, Integer y){
        var distance = 1;
        for(var i=y-1; i>=1; i--){
            if(theGrid[x][i] < theGrid[x][y]){
                distance++; 
            } else {
                break; 
            }
        }
        return distance; 
        //1
    }

    public static Integer viewDistanceRight(Integer[][] theGrid, Integer x, Integer y){
        var distance = 1;
        for(var i=y+1; i<theGrid.length-1; i++){
            if(theGrid[x][i] < theGrid[x][y]){
                distance++; 
            } else {
                break; 
            }
        }
        return distance; 
        //3
    }

    public static Integer viewDistanceAbove(Integer[][] theGrid, Integer x, Integer y){
        var distance = 1;
        for(var i=x-1; i>=1; i--){
            if(theGrid[i][y] < theGrid[x][y]){
                distance++;
            } else {
                break;
            }
        }
        return distance;
    }

    public static Integer viewDistanceBelow(Integer[][] theGrid, Integer x, Integer y){
        var distance = 1;
        for(var i=x+1; i<theGrid.length-1; i++){
            if(theGrid[i][y] < theGrid[x][y]){
                distance++;
            } else {
                break;
            }
        }
        return distance;
    }

    public static boolean isVisibleFromLeft(Integer[][] theGrid, Integer x, Integer y){
        var isTallest = false; 
        for(var i=0; i<y; i++){
            if(theGrid[x][i] < theGrid[x][y]){
                isTallest = true; 
            } else {
                isTallest = false;
                break;
            }
        }
        return isTallest; 
    }

    public static boolean isVisibleFromRight(Integer[][] theGrid, Integer x, Integer y){
        var isTallest = false; 
        for(var i=(theGrid.length-1); i>y; i--){
            if(theGrid[x][i] < theGrid[x][y]){
                isTallest = true; 
            } else {
                isTallest = false; 
                break;
            }
        }
        return isTallest; 
    }

    public static boolean isVisibleFromAbove(Integer[][] theGrid, Integer x, Integer y){
        var isTallest = false;
        for(var i=0; i<x; i++){
            if(theGrid[i][y] < theGrid[x][y]){
                isTallest = true;
            } else {
                isTallest = false;
                break; 
            }
        }
        return isTallest;
    }

    public static boolean isVisibleFromBelow(Integer[][] theGrid, Integer x, Integer y){
        var isTallest = false;
        for(var i=(theGrid.length-1); i>x; i--){
            if(theGrid[i][y] < theGrid[x][y]){
                isTallest = true; 
            } else {
                isTallest = false;
                break;
            }
        }
        return isTallest; 
    }
}