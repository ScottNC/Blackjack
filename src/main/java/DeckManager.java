import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.IntStream;

import com.google.gson.Gson;

public class DeckManager {
    public static String API_BASE_URL = "https://deckofcardsapi.com/api/deck";

    public static String newDeck() {
        String deckResponse = makeURLRequest("/new/shuffle/?deck_count=1");
        Deck deck = new Gson().fromJson(deckResponse, Deck.class);
        return deck.getDeckId();
    }

    /*
        Right now for Deck Of Cards API no matter what the count is, when making the request
        using Java the API will return only 1 card. Until this problem is solved we are
        requesting one card at a time.
     */
    public static String[] drawCards(String deckId, int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> makeURLRequest("/" + deckId + "/draw/?count=1"))
                .map(response -> {
                    Cards cards = new Gson().fromJson(response, Cards.class);
                    return Arrays.stream(cards.getCards())
                            .map(Card::getCode)
                            .toArray(String[]::new);
                })
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }

    private static String makeURLRequest(String endpoint) {

        try {
            URI uri = URI.create(API_BASE_URL + endpoint);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.close();

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();

            return response.toString();
        }
        catch (IOException e) {
            return null;
        }
    }

    private static class Deck {
        private String deck_id;

        public String getDeckId() {
            return deck_id;
        }
    }

    private static class Cards {
        private Card[] cards;

        public Card[] getCards() {
            return cards;
        }
    }

    private static class Card {
        private String code;

        public String getCode() {
            return code;
        }
    }
}
