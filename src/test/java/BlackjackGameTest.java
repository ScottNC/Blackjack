import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlackjackGameTest {

    @Test
    void findValue() {
        assertEquals(9, BlackjackGame.findValue('9'));
        assertEquals(10, BlackjackGame.findValue('0'));
    }

    @Test
    void sumCards() {
        assertEquals(11, BlackjackGame.findSumCards(List.of(new String[]{"5H", "6S"})));
        assertEquals(25, BlackjackGame.findSumCards(List.of(new String[]{"KD", "QC", "5H"})));
    }

    @Test
    void findScore() {
        assertArrayEquals(new int[]{11}, BlackjackGame.findScore(List.of(new String[]{"5H", "6S"})));
        assertArrayEquals(new int[]{25}, BlackjackGame.findScore(List.of(new String[]{"KD", "QC", "5H"})));
        assertArrayEquals(new int[]{26}, BlackjackGame.findScore(List.of(new String[]{"KD", "QC", "5H", "AS"})));
        assertArrayEquals(new int[]{8,18}, BlackjackGame.findScore(List.of(new String[]{"AH", "7H"})));
        assertArrayEquals(new int[]{19}, BlackjackGame.findScore(List.of(new String[]{"AH", "8H", "0S"})));
    }

    @Test
    void getResult() {
        assertEquals("Bust", BlackjackGame.getResult(24));
        assertEquals("Blackjack", BlackjackGame.getResult(21));
        assertEquals("8", BlackjackGame.getResult(8));
        assertEquals("8", BlackjackGame.getResult(18));
    }
}