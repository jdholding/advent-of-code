import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day3{
    public static HashMap<Character, Integer> priority = new HashMap<>(); 
    public static HashMap<Integer, List<String>> groups = new HashMap<>();
    public static void main(String[] args){
        char a = 'a';

        //if c<97 ? c-38 : c-96 for ascii value of char
        System.out.println((int) a); 
        List<String> allLines;
        try{
        Scanner sc = new Scanner(System.in); 
        allLines = Files.readAllLines(Paths.get(sc.nextLine()));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        Integer p = 1; 
        for (char alphabet = 'a'; alphabet <='z'; alphabet++){
            priority.put(alphabet, p);
            p++;
        }
        for (char uppercase = 'A'; uppercase <='Z'; uppercase++){
            priority.put(uppercase, p);
            p++;
        }
        Integer totalPriority = 0; 
        //for(String line : allLines){
            for(int i=0; i<allLines.size()-2; i++){
            if(i % 3 == 0){
                groups.put(i, List.of(allLines.get(i), allLines.get(i+1), allLines.get(i+2)));
            }

            
            // Integer half = line.length() / 2; 
            // String[] subs = {line.substring(0, half), line.substring(half)};
            // System.out.println(subs[0]+ "   and   " + subs[1]);
            // for (Character c : subs[0].toCharArray()){
            //     if(subs[1].contains(String.valueOf(c))){
            //         System.out.println("the common element: "+c+ " with a priority of "+priority.get(c));
            //         totalPriority += priority.get(c);
            //         break;  
            //     }
            // } 
            // System.out.println("-------------------------------");
        }
        for(Map.Entry elfGroup : groups.entrySet()){
            List<String> rucksacks = (List<String>) elfGroup.getValue();
            for(Character c : rucksacks.get(0).toCharArray()){
                if(rucksacks.get(1).contains(String.valueOf(c)) && rucksacks.get(2).contains(String.valueOf(c))){
                    System.out.println("Group badge:  " + c);
                    System.out.println("Group  \n"+rucksacks.get(0)+"\n"
                                                +rucksacks.get(1)+"\n"
                                                +rucksacks.get(2)+"\n"
                                                +"------------------------");
                    totalPriority += priority.get(c);
                    break;
                }
            }
        }
        System.out.println("Grouped packing priority: "+ totalPriority);
    }
}