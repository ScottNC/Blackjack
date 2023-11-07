import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();

    public static void main(String[] args) {
        System.out.println("Welcome to Blackjack!");

        Player human = new Player(false, DeckManager.drawCards(deckId, 2));
        Player dealer = new Player(true, DeckManager.drawCards(deckId, 2));

        String humanScore = playGame(human);
        String dealerScore = playGame(dealer);

        System.out.println(human.name + " result: " + humanScore);
        System.out.println(dealer.name + " result: " + dealerScore);
    }


    public static String playGame(Player player) {
        String name = player.name;
        List<String> cards = player.cards;
        String result;

        while (true) {
            System.out.println(name + " cards: " + cards);
            int[] score = findScore(cards);
            System.out.println(name + " score: " + Arrays.toString(score));

            int highestScore = score[score.length - 1];
            result = getResult(highestScore);

            if (result.length() <= 2) {
                boolean isHit = player.hit(highestScore);

                if (isHit) {
                    String newCard = DeckManager.drawCards(deckId, 1)[0];
                    cards.add(newCard);
                } else break;

            } else break;
        }

        return result;
    }

    public static String getResult(int score) {

        if (score == 21)
            return "Blackjack";
        else if (score > 21)
            return "Bust";

        return String.valueOf(score);
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
