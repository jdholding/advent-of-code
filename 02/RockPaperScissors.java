import java.util.Scanner;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List; 

public class RockPaperScissors{
    public static HashMap<String, Integer> elfMoves = new HashMap<>(); 
    public static HashMap<String, Integer> humanMoves = new HashMap<>(); 
    
    public static void main(String[] args){
        elfMoves.put("A", 1);
        elfMoves.put("B", 2);
        elfMoves.put("C", 3);
        humanMoves.put("X", 1);
        humanMoves.put("Y", 2);
        humanMoves.put("Z", 3);
        List<String> allLines;
        try{
        Scanner sc = new Scanner(System.in); 
        allLines = Files.readAllLines(Paths.get(sc.nextLine()));
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        Integer totalPoints = 0; 

        for (String line: allLines){
            String[] round = line.split("\\s+");
            var elfPoints = elfMoves.get(round[0]);
            var humanPoints = humanMoves.get(round[1]);
            
            // For part 1, following the guide exactly

            // totalPoints += humanPoints;
            // if(elfPoints == humanPoints){
            //     totalPoints += 3; 
            // } else {
            //     switch (humanPoints) {
            //         case 1:
            //             if (elfPoints == 3) { totalPoints += 6; }
            //             break;
            //         case 2:
            //             if (elfPoints == 1) { totalPoints += 6; }
            //             break; 
            //         case 3:
            //             if (elfPoints == 2) { totalPoints += 6; }
            //             break; 
            //     }
            // }

            // For Part 2, the instructions for the human change to X(1) = lose, Y(2) = draw, Z(3) = win
            switch (humanPoints){
                case 1:
                    totalPoints += winningByCheating(elfPoints, false);
                    break;
                case 2:
                    totalPoints += (elfPoints + 3);
                    break; 
                case 3:
                    totalPoints += winningByCheating(elfPoints, true);
                    break;      
            }    

        }
        System.out.println("The total points possible by following the guide: " + totalPoints);

    }
    public static Integer winningByCheating(Integer elfPoints, boolean isWinner){
        switch (elfPoints){
            case 1:
                // lose with scissors(3) OR win with paper(2) plus 6 for winning 
                return isWinner ? 8 : 3; 
            case 2:
                // lose with rock(1) OR win with scissors(3) plus 6 for winning
                return isWinner ? 9 : 1; 
            case 3:
                // lose with paper(2) OR win with rock(1) plus 6 for winning
                return isWinner ? 7 : 2;      
            default:
                return 0; 
        } 
    }
}