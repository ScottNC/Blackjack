import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    @Test
    void findValue() {
        assertEquals(9, BlackjackGame.findValue('9'));
        assertEquals(10, BlackjackGame.findValue('0'));
    }

    @Test
    void sumCards() {
        assertEquals(11, BlackjackGame.sumCards(new String[]{"5H", "6S"}));
        assertEquals(25, BlackjackGame.sumCards(new String[]{"KD", "QC", "5H"}));
    }
}