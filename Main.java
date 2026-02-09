import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Player 1's name: ");
        String player1 = scanner.nextLine();
        
        System.out.print("Enter Player 2's name: ");
        String player2 = scanner.nextLine();
        
        System.out.println("Starting the game between " + player1 + " and " + player2 + "...");
        
        // Add game logic here.
        
        scanner.close();
    }
}