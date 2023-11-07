import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Blackjack!");
        Scanner consoleInput = new Scanner(System.in);

        Player human = new Player(false);
        Player dealer = new Player(true);

        String again;

        do {
            System.out.println();
            playGame(human, dealer);
            again = "";

            while (!again.equals("Y") && !again.equals("N")) {
                System.out.println("Would you like to play again? (Y/N)");
                again = consoleInput.nextLine().toUpperCase();
            }
        } while (!again.equals("N"));

        System.out.println();
        System.out.println("Final Score:");
        System.out.println(human.name + " - " + human.score);
        System.out.println(dealer.name + " - " + dealer.score);

        if (human.score == dealer.score)
            System.out.println("It's A Tie");
        else if (human.score > dealer.score)
            System.out.println("The Winner Is " + human.name);
        else
            System.out.println("The Winner Is " + dealer.name);
    }

    public static String displayList(List<String> cards) {
        return String.join(", ", cards);
    }

    public static String displayArray(int[] arr) {
        return String.join(" or ", Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .toArray(String[]::new));
    }

    public static void playGame(Player human, Player dealer) {
        human.setCards(DeckManager.drawCards(deckId, 2));
        dealer.setCards(DeckManager.drawCards(deckId, 2));
        System.out.println("Dealer Cards: " + displayList(List.of(dealer.cards.get(0), "??")));
        System.out.println();
        String humanResult = playSingleGame(human);
        System.out.println();
        String dealerResult = playSingleGame(dealer);

        System.out.println();
        System.out.println(human.name + " result: " + humanResult);
        System.out.println(dealer.name + " result: " + dealerResult);
        System.out.println();

        if (humanResult.equals(dealerResult))
            System.out.println("Tie");
        else if (humanResult.equals("Bust") || dealerResult.equals("Blackjack")
                || !humanResult.equals("Blackjack") && !dealerResult.equals("Bust")
                && (parseInt(dealerResult) > parseInt(humanResult)))
            dealer.wins();
        else human.wins();
    }

    public static String playSingleGame(Player player) {
        String name = player.name;
        List<String> cards = player.cards;
        String result;

        System.out.println(name + "'s Turn");
        System.out.println();

        while (true) {
            System.out.println(name + " cards: " + displayList(cards));
            int[] allResults = findTotalValue(cards);
            System.out.println(name + " Result: " + displayArray(allResults));

            int highestResult = allResults[allResults.length - 1];
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

    public static int[] findTotalValue(List<String> cards) {
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
