import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();
    public static void main(String[] args) {
        System.out.println("Welcome to Blackjack!");
        String[] cards = DeckManager.drawCards(deckId, 2);
        System.out.println(Arrays.toString(cards));
        System.out.println("Total score: " + Arrays.toString(findScore(cards)));
    }

    public static int[] findScore(String[] cards) {
        int sumCards = findSumCards(cards);

        if (sumCards <= 21 && containsAce(cards))
            return new int[]{sumCards, sumCards + 10};

        return new int[]{sumCards};
    }

    public static int findSumCards(String[] cards) {
        return Arrays.stream(cards)
                .map(card -> card.charAt(0))
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

    public static boolean containsAce(String[] cards) {
        return String.join("", cards).contains("A");
    }
}
