import java.util.Arrays;

public class BlackjackGame {

    private static final String deckId = DeckManager.newDeck();
    public static void main(String[] args) {
        System.out.println("Welcome to Blackjack!");
        String[] cards = DeckManager.drawCards(deckId, 2);
        System.out.println(Arrays.toString(cards));
    }
}
