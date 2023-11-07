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
        assertEquals(11, BlackjackGame.findSumCards(new String[]{"5H", "6S"}));
        assertEquals(25, BlackjackGame.findSumCards(new String[]{"KD", "QC", "5H"}));
    }

    @Test
    void findScore() {
        assertArrayEquals(new int[]{11}, BlackjackGame.findScore(new String[]{"5H", "6S"}));
        assertArrayEquals(new int[]{25}, BlackjackGame.findScore(new String[]{"KD", "QC", "5H"}));
        assertArrayEquals(new int[]{26}, BlackjackGame.findScore(new String[]{"KD", "QC", "5H", "AS"}));
        assertArrayEquals(new int[]{8,18}, BlackjackGame.findScore(new String[]{"AH", "7H"}));
    }
}