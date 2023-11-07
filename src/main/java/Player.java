import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player {
    public String name;
    public boolean dealer;
    public List<String> cards;

    public Player(boolean isDealer, String[] cardsArr) {
        dealer = isDealer;
        cards = new ArrayList<>(Arrays.asList(cardsArr));
        if (dealer)
            name = "Dealer";
        else {
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Please enter your name");
            name = consoleInput.nextLine();
        }
    }

    public boolean hit(int score) {
        if (dealer) return score <= 16;
        else {
            Scanner consoleInput = new Scanner(System.in);
            String move = "";

            while (!move.equals("H") && !move.equals("S")) {
                System.out.println("H: to Hit  S: to Stop");
                move = consoleInput.nextLine().toUpperCase();
            }

            return move.equals("H");
        }
    }
}
