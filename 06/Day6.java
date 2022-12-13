import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day6{

    public static void main(String[] args){
        List<String> allLines;
        try{
            Scanner sc = new Scanner(System.in);
            allLines = Files.readAllLines(Paths.get(sc.nextLine()));
        } catch(IOException e){
            e.printStackTrace();
            return; 
        }
        // for(String line: allLines){
            var line = allLines.get(0);
            var lineChars = line.trim().toCharArray();
             
            for(var i = 0; i<(lineChars.length-13); i++){
                var message = false;
                List<Character> seen = new ArrayList<>();  
                for(var j=i+13; j>i; j--){
                    if(lineChars[i] != lineChars[j]){
                        if(seen.contains(lineChars[j])){
                            message = false;
                            break; 
                        }
                        seen.add(lineChars[j]); 
                        message = true; 
                    } else {
                        message = false;
                        break; 
                    }
                }
                if(message){
                    System.out.println("first message after character " + (i+14));
                    break;  
                }
                // var j = i+3; 
                // System.out.println("this is i: " + i);
                // System.out.println("this is j: " + j);
                // if((lineChars[i] != lineChars[j]) &&
                // (lineChars[i+1] != lineChars[j-1]) &&
                // (lineChars[i] != lineChars[i+1]) &&
                // (lineChars[j-1] != lineChars[j]) &&
                // (lineChars[i] != lineChars[j-1]) &&
                // (lineChars[i+1] != lineChars[j])){
                //     System.out.println(lineChars[i]);
                //     System.out.println(lineChars[i+1]);
                //     System.out.println(lineChars[j-1]);
                //     System.out.println(lineChars[j]);
                //     System.out.println("first marker after character " + (j+1));
                //     break;
                // }
            }
        // }
    }
}