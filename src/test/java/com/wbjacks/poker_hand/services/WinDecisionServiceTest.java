package com.wbjacks.poker_hand.services;

import com.wbjacks.poker_hand.models.Card;
import com.wbjacks.poker_hand.models.Hand;
import org.junit.Test;

import java.util.ArrayList;

import static com.wbjacks.poker_hand.models.Card.Rank;
import static com.wbjacks.poker_hand.models.Card.Suite;
import static org.junit.Assert.assertEquals;

public class WinDecisionServiceTest {
    @Test
    public void testHandWins() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        // 4 of a kind
        Hand hand1 = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.HEART));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(Card.joker());
            add(new Card(Rank.THREE, Suite.SPADE));
        }});

        // Full house
        Hand hand2 = new Hand(new ArrayList() {{
            add(new Card(Rank.SIX, Suite.SPADE));
            add(new Card(Rank.SIX, Suite.HEART));
            add(new Card(Rank.SIX, Suite.DIAMOND));
            add(new Card(Rank.KING, Suite.CLUB));
            add(new Card(Rank.KING, Suite.HEART));
        }});
        WinDecisionService winDecisionService = new WinDecisionService(new HandClassifierService());
        assertEquals(WinDecisionService.Decision.WIN, winDecisionService.decideVictory(hand1, hand2));
    }

    @Test
    public void testHandLoses() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        // Two pairs
        Hand hand1 = new Hand(new ArrayList() {{
            add(new Card(Rank.SIX, Suite.SPADE));
            add(new Card(Rank.SIX, Suite.HEART));
            add(new Card(Rank.JACK, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.CLUB));
            add(new Card(Rank.JACK, Suite.HEART));
        }});

        // 4 of a kind
        Hand hand2 = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.HEART));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(Card.joker());
            add(new Card(Rank.QUEEN, Suite.SPADE));
        }});

        WinDecisionService winDecisionService = new WinDecisionService(new HandClassifierService());
        assertEquals(WinDecisionService.Decision.LOSE, winDecisionService.decideVictory(hand1, hand2));
    }

    @Test
    public void testHandTies() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        // Flush
        Hand hand1 = new Hand(new ArrayList() {{
            add(new Card(Rank.ACE, Suite.SPADE));
            add(new Card(Rank.TWO, Suite.SPADE));
            add(new Card(Rank.JACK, Suite.SPADE));
            add(new Card(Rank.SIX, Suite.SPADE));
            add(new Card(Rank.JACK, Suite.SPADE));
        }});

        // Flush
        Hand hand2 = new Hand(new ArrayList() {{
            add(new Card(Rank.THREE, Suite.DIAMOND));
            add(new Card(Rank.FOUR, Suite.DIAMOND));
            add(new Card(Rank.FIVE, Suite.DIAMOND));
            add(Card.joker());
            add(new Card(Rank.KING, Suite.DIAMOND));
        }});

        WinDecisionService winDecisionService = new WinDecisionService(new HandClassifierService());
        assertEquals(WinDecisionService.Decision.TIE, winDecisionService.decideVictory(hand1, hand2));

    }
}