import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List; 

public class ElfCalories{
    public static void main(String[] args){
        try {
        Scanner sc = new Scanner(System.in);
        // File file = new File(sc.nextLine());
        List<String> allLines = Files.readAllLines(Paths.get(sc.nextLine()));

        var elfCals = new ArrayList<Integer>();
        int elf = 0;
        for(String line: allLines){
            if(line.trim().equalsIgnoreCase("")){
                elfCals.add(elf);
                elf = 0;               
            } else {
                elf += Integer.parseInt(line);                
            }

        }
        
        Collections.sort(elfCals, Collections.reverseOrder());
        System.out.println("The top elf : " + elfCals.get(0));
        var topThree = elfCals.get(0) + elfCals.get(1) + elfCals.get(2);
        System.out.println("Part two, the top 3 elves : " + topThree);
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}