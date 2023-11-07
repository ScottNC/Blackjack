import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Blackjack!");
        System.out.println();

        Player human = new Player(false, DeckManager.drawCards(deckId, 2));
        Player dealer = new Player(true, DeckManager.drawCards(deckId, 2));

        String humanResult = playGame(human);
        System.out.println();
        String dealerResult = playGame(dealer);

        System.out.println();
        System.out.println(human.name + " result: " + humanResult);
        System.out.println(dealer.name + " result: " + dealerResult);
        System.out.println();

        if (humanResult.equals(dealerResult))
            System.out.println("Tie");
        else if (humanResult.equals("Bust") || dealerResult.equals("Blackjack")
                || !humanResult.equals("Blackjack") && !dealerResult.equals("Bust")
                && (parseInt(dealerResult) > parseInt(humanResult)))
            System.out.println(dealer.name + " Wins");
        else
            System.out.println(human.name + " Wins");
    }


    public static String playGame(Player player) {
        String name = player.name;
        List<String> cards = player.cards;
        String result;

        System.out.println(name + "'s Turn");
        System.out.println();

        while (true) {
            System.out.println(name + " cards: " + cards);
            int[] Result = findResult(cards);
            System.out.println(name + " Result: " + Arrays.toString(Result));

            int highestResult = Result[Result.length - 1];
            result = getResult(highestResult);

            if (result.length() <= 2) {
                boolean isHit = player.hit(highestResult);

                if (isHit) {
                    String newCard = DeckManager.drawCards(deckId, 1)[0];
                    cards.add(newCard);
                } else break;

            } else break;
        }

        return result;
    }

    public static String getResult(int Result) {

        if (Result == 21)
            return "Blackjack";
        else if (Result > 21)
            return "Bust";

        return String.valueOf(Result);
    }

    public static int[] findResult(List<String> cards) {
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
