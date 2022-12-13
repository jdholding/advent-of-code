import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Day4{
        // find which list sl has the smaller size
        // is the start of sl >= start of other list AND end of sl <= end of other list
        // are the lists equal in size and start of sl == start of other list
    public static void main(String[] args){
        List<String> allLines; 
        try{
            Scanner sc = new Scanner(System.in); 
            allLines = Files.readAllLines(Paths.get(sc.nextLine()));
            } catch(IOException e) {
                e.printStackTrace();
                return;
            }
        int containedPairs = 0;
        int overlappingPairs = 0;
        for(String line : allLines){
            //int comma = line.indexOf(",");
            //String[] subs = {line.substring(0, comma), line.substring(comma+1)};
            String[] subs = line.split(",");
            // PART 1
            if(pairContained(sectionLimits(subs[0]), sectionLimits(subs[1]))){
                containedPairs++; 
            }

            // PART 2
            if(pairsOverlap(sectionLimits(subs[0]), sectionLimits(subs[1]))){
                overlappingPairs++;
            }
        }    
        System.out.println("There are  " + containedPairs + "  fully contained pairs.");
        System.out.println("There are  " + overlappingPairs + "  overlapping pairs.");
    }

    public static boolean pairContained(int[] elf1, int[] elf2){

        return (elf2[0] >= elf1[0] && elf2[1] <= elf1[1])||
            (elf1[0] >= elf2[0] && elf1[1] <= elf2[1]); 
    }
    // part 2 
    // if there is total overlap
    // if start1 <= start2 <= end1
    // if start2 <= start1 <= end2
    public static boolean pairsOverlap(int[] elf1, int[] elf2){
        return (elf1[0] <= elf2[0] && elf2[0] <= elf1[1]) ||
                (elf2[0] <= elf1[0] && elf1[0] <= elf2[1]); 
    }

    public static int[] sectionLimits(String sections){
        String[] ss = sections.split("-");
        return new int[] {Integer.parseInt(ss[0]), Integer.parseInt(ss[1])}; 
    }
}