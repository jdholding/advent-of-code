import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5{
    // split the file into two list based on the first line break
    // For the first list (drawing):
    //  reverse the order of the list ==> the first line is the number of stacks
    //     no-- find the longest line (the number of stacks + (number of stacks-1))
    //      iterate the line i=1 i+4, if "\\s+" then empty for that stack else add crate to stack

    // second list
    // make an array of integers from each procedure line string.replaceAll("[^0-9]+", " ") then split on " "
    //      [times to iterate, start stack, destination stack]
    
    // result string is the last entry in each stack list
    public static void main(String[] args){
        List<String> allLines;
        try{
        // Scanner sc = new Scanner(System.in); 
        // allLines = Files.readAllLines(Paths.get(sc.nextLine()));
        allLines = Files.readAllLines(Paths.get("stacks.txt"));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        List<String> stackLines = allLines.subList(0, allLines.indexOf(""));
        List<String> procedures = allLines.subList(allLines.indexOf("")+1, allLines.size()); 

        Collections.reverse(stackLines);
        List<List<String>> stacks = parseStacks(stackLines);
        List<List<Integer>> steps = parseProcedure(procedures);

        for (List<Integer> step : steps){
            var quantity = step.get(0);
            var start = step.get(1)-1;
            var destination = step.get(2)-1; 

            // for(var i=0; i<quantity; i++){
            //     // var stackFrom = stacks.get(start);
            //     // var stackTo = stacks.get(destination);
            //     // var toMove = stackFrom.get(stackFrom.size()-1);
            //     // stackTo.add(toMove);
            //     // stackFrom.remove(stackFrom.size()-1); 
            // }
            var stackFrom = stacks.get(start);
            var stackTo = stacks.get(destination);
            var toMove = stackFrom.subList(stackFrom.size()-(quantity), stackFrom.size());
            stackTo.addAll(toMove);
            for (var i=0; i<quantity; i++){
                stackFrom.remove(stackFrom.size()-1);
            }

        }
        String result = "";
        for (List<String> stack : stacks){
            System.out.println(stack);
            result += stack.get(stack.size()-1);
        }
        System.out.println(result);

    }

    public static List<List<String>> parseStacks(List<String> stackLines){

        String stackNums = stackLines.get(0).trim();
        List<List<String>> stacks = new ArrayList<>();

        // make the list of lists with the number of the stack as the first string entry 
        // THIS IS DUMB AS HELL, I DON'T CARE ABOUT THE INDEXES AFTER I GET THE LETTERS SORTED
        // for(char c: stackNums.toCharArray()){
        //     if(Character.isDigit(c)){
        //     stacks.add(new ArrayList<String>(Arrays.asList(new String[]{String.valueOf(stackLines.get(0).indexOf(c))})));
        //     }
        // }
        List<Integer> indexes = new ArrayList<>(); 
        for(char c: stackNums.toCharArray()){
            if(Character.isDigit(c)){
                indexes.add(stackLines.get(0).indexOf(c));
                stacks.add(new ArrayList<String>()); 
            }
        }

        for (String line : stackLines){
            if(line != stackLines.get(0)){

                // cut out all the bullshit, replace blank colums with "-"
                // String stackLine = line.replaceAll("\\s\\s\\s", "-")
                //                         .replaceAll("\\s", "")
                //                         .replaceAll("\\s?\\[","")
                //                         .replaceAll("\\]\\s?", "");
                // System.out.println(stackLine);                                     
                
                // put each container in the line into it's column list                                    
                for(var i = 0; i<indexes.size(); i++){
                    if(line.charAt(indexes.get(i)) != ' '){
                        stacks.get(i).add(String.valueOf(line.charAt(indexes.get(i))));
                    }
                }                                  
            }
        }
        return stacks; 
    }

    public static List<List<Integer>> parseProcedure(List<String> procedures){
        List<List<Integer>> steps = new ArrayList<>();
        for (String procedure : procedures){
            Matcher matcher = Pattern.compile("\\d+").matcher(procedure);
            List<Integer> proSteps = new ArrayList<>();
            while (matcher.find()){
                proSteps.add(Integer.valueOf(matcher.group()));
            }
                
                steps.add(proSteps); 
        }
        return steps; 
    }
}