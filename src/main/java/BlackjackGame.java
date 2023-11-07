import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();
    public static void main(String[] args) {
        playGame();
    }

    public static void playGame() {
        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");
        String[] cardsArr = DeckManager.drawCards(deckId, 2);
        List<String> cards = new ArrayList<>(Arrays.asList(cardsArr));
        String result;

        while (true) {
            System.out.println("Your cards: " + cards);
            int[] score = findScore(cards);
            System.out.println("Total score: " + Arrays.toString(score));

            result = getResult(score);

            if (result == null) {
                String move = "N";

                while (!move.equals("H") && !move.equals("S")) {
                    System.out.println("H: to Hit  S: to Stop");
                    move = consoleInput.nextLine().toUpperCase();
                }

                if (move.equals("H")) {
                    String newCard = DeckManager.drawCards(deckId, 1)[0];
                    cards.add(newCard);
                } else {
                    result = String.valueOf(score[score.length - 1]);
                    break;
                }

            } else break;
        }

        System.out.println("Your result: " + result);
    }

    public static String getResult(int[] score) {

        if (score[score.length - 1] == 21)
            return "Blackjack";
        else if (score[0] > 21)
            return "Bust";

        return null;
    }

    public static int[] findScore(List<String> cards) {
        int sumCards = findSumCards(cards);

        if (sumCards <= 11 && containsAce(cards))
            return new int[]{sumCards, sumCards + 10};

        return new int[]{sumCards};
    }

    public static int findSumCards(List<String> cards) {
        return Arrays.stream(cards.toArray())
                .map(card -> String.valueOf(card).charAt(0))
                .map(BlackjackGame::findValue)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public static int findValue(char c) {
        return switch (c) {
            case 'A' -> 1;
            case '0', 'Q', 'J', 'K' -> 10;
            default -> parseInt(String.valueOf(c));
        };
    }

    public static boolean containsAce(List<String> cards) {
        return String.join("", cards).contains("A");
    }
}
