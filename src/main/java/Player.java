import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Player {
    public String name;
    public boolean dealer;
    public List<String> cards;
    public int score;


    public Player(boolean isDealer) {
        score = 0;
        dealer = isDealer;
        if (dealer)
            name = "Dealer";
        else {
            Scanner consoleInput = new Scanner(System.in);
            System.out.println();
            System.out.println("Please enter your name");
            name = consoleInput.nextLine();
        }
    }

    public void setCards(String[] cardsArr) {
        cards = new ArrayList<>(Arrays.asList(cardsArr));
    }

    public void wins() {
        System.out.println(name + " Wins Round");
        score++;
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
