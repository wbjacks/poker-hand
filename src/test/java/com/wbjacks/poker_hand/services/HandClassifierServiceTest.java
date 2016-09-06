// Thanks to https://en.wikipedia.org/wiki/List_of_poker_hand_categories for the easy examples for testing

package com.wbjacks.poker_hand.services;

import com.wbjacks.poker_hand.models.Card;
import com.wbjacks.poker_hand.models.Card.Rank;
import com.wbjacks.poker_hand.models.Card.Suite;
import com.wbjacks.poker_hand.models.Hand;
import com.wbjacks.poker_hand.models.HandClassification;
import com.wbjacks.poker_hand.models.HandClassification.Classification;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SuppressWarnings("unchecked") // Raw ArrayList required for DBI
public class HandClassifierServiceTest {
    /*
    FIVE OF A KIND
     */
    @Test
    public void fiveOfAKindOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.KING, Suite.CLUB));
            add(new Card(Rank.KING, Suite.DIAMOND));
            add(new Card(Rank.KING, Suite.HEART));
            add(new Card(Rank.KING, Suite.SPADE));
            add(Card.joker());
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    public void fiveOfAKindTwoJokersIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.CLUB));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.HEART));
            add(Card.joker());
            add(Card.joker());
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.TWO, handClassification.getHighCard());
    }

    /*
    STRAIGHT FLUSH
     */

    @Test
    public void straightFlushIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.NINE, Suite.CLUB));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.JACK, Suite.CLUB));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT_FLUSH, handClassification.getClassification());
        assertEquals(Rank.JACK, handClassification.getHighCard());
    }

    @Test
    public void straightFlushOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.NINE, Suite.CLUB));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(Card.joker());
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT_FLUSH, handClassification.getClassification());
        assertEquals(Rank.TEN, handClassification.getHighCard());
    }

    @Test
    public void straightFlushTwoJokersIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.NINE, Suite.CLUB));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(Card.joker());
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT_FLUSH, handClassification.getClassification());
        assertEquals(Rank.TEN, handClassification.getHighCard());
    }

    /*
    FOUR OF A KIND
    */

    @Test
    public void fourOfAKindIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.HEART));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.SPADE));
            add(new Card(Rank.THREE, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FOUR_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.THREE, handClassification.getHighCard());
    }

    @Test
    public void fourOfAKindOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.HEART));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(Card.joker());
            add(new Card(Rank.THREE, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FOUR_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.THREE, handClassification.getHighCard());
    }

    @Test
    public void fourOfAKindTwoJokersIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.HEART));
            add(new Card(Rank.TWO, Suite.DIAMOND));
            add(Card.joker());
            add(Card.joker());
            add(new Card(Rank.THREE, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FOUR_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.THREE, handClassification.getHighCard());
    }

    /*
    FULL HOUSE
     */

    @Test
    public void fullHouseIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SIX, Suite.SPADE));
            add(new Card(Rank.SIX, Suite.HEART));
            add(new Card(Rank.SIX, Suite.DIAMOND));
            add(new Card(Rank.KING, Suite.CLUB));
            add(new Card(Rank.KING, Suite.HEART));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FULL_HOUSE, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    public void fullHouseOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SIX, Suite.SPADE));
            add(Card.joker());
            add(new Card(Rank.SIX, Suite.DIAMOND));
            add(new Card(Rank.KING, Suite.CLUB));
            add(new Card(Rank.KING, Suite.HEART));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FULL_HOUSE, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    /*
    FLUSH
     */

    @Test
    public void flushIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.ACE, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FLUSH, handClassification.getClassification());
        assertEquals(Rank.ACE, handClassification.getHighCard());
    }

    @Test
    public void flushOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.ACE, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FLUSH, handClassification.getClassification());
        assertEquals(Rank.ACE, handClassification.getHighCard());
    }

    @Test
    public void flushTwoJokersIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TWO, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.ACE, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FLUSH, handClassification.getClassification());
        assertEquals(Rank.ACE, handClassification.getHighCard());
    }

    /*
    STRAIGHT
     */

    @Test
    public void straightIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.HEART));
            add(new Card(Rank.EIGHT, Suite.CLUB));
            add(new Card(Rank.NINE, Suite.DIAMOND));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.SIX, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT, handClassification.getClassification());
        assertEquals(Rank.TEN, handClassification.getHighCard());
    }

    @Test
    public void straightOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.HEART));
            add(Card.joker());
            add(new Card(Rank.NINE, Suite.DIAMOND));
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.SIX, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT, handClassification.getClassification());
        assertEquals(Rank.TEN, handClassification.getHighCard());
    }

    @Test
    public void straightTwoJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.SEVEN, Suite.HEART));
            add(Card.joker());
            add(new Card(Rank.NINE, Suite.DIAMOND));
            add(Card.joker());
            add(new Card(Rank.SIX, Suite.CLUB));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.STRAIGHT, handClassification.getClassification());
        assertEquals(Rank.NINE, handClassification.getHighCard());
    }

    @Test
    public void wrappingStraightIsNotClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.JACK, Suite.HEART));
            add(Card.joker());
            add(new Card(Rank.KING, Suite.DIAMOND));
            add(new Card(Rank.ACE, Suite.CLUB));
            add(new Card(Rank.TWO, Suite.DIAMOND));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertNotEquals(Classification.STRAIGHT, handClassification.getClassification());
    }

    /*
    THREE OF A KIND
     */

    @Test
    public void threeOfAKindIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.QUEEN, Suite.CLUB));
            add(new Card(Rank.QUEEN, Suite.SPADE));
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.NINE, Suite.HEART));
            add(new Card(Rank.TWO, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.THREE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    @Test
    public void threeOfAKindOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.QUEEN, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.NINE, Suite.HEART));
            add(new Card(Rank.TWO, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.THREE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    @Test
    public void threeOfAKindTwoJokersIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.QUEEN, Suite.CLUB));
            add(Card.joker());
            add(Card.joker());
            add(new Card(Rank.NINE, Suite.HEART));
            add(new Card(Rank.TWO, Suite.SPADE));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.THREE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    /*
    TWO PAIR
     */

    @Test
    public void twoPairIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.TEN, Suite.SPADE));
            add(new Card(Rank.THREE, Suite.DIAMOND));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.TWO_PAIR, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    /*
    ONE PAIR
     */
    @Test
    public void onePairIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.TEN, Suite.HEART));
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.JACK, Suite.SPADE));
            add(new Card(Rank.THREE, Suite.DIAMOND));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.ONE_PAIR, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    @Test
    public void onePairOneJokerIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TEN, Suite.CLUB));
            add(Card.joker());
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.JACK, Suite.SPADE));
            add(new Card(Rank.THREE, Suite.DIAMOND));

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.ONE_PAIR, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }

    /*
    HIGH CARD
     */
    @Test
    public void noClassification() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
            add(new Card(Rank.TEN, Suite.CLUB));
            add(new Card(Rank.SIX, Suite.HEART));
            add(new Card(Rank.QUEEN, Suite.HEART));
            add(new Card(Rank.JACK, Suite.SPADE));
            add(new Card(Rank.THREE, Suite.DIAMOND));
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.NONE, handClassification.getClassification());
        assertEquals(Rank.QUEEN, handClassification.getHighCard());
    }
}