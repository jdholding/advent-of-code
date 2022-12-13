import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Day9{
    public static void main(String[] args){
        List<String> allLines; 
        try {
            Scanner sc = new Scanner(System.in);
            allLines = Files.readAllLines(Paths.get(sc.nextLine()));  
        } catch(IOException e) {
            e.printStackTrace();
            return; 
        }

    }
}