package com.wbjacks.models.services;

import com.wbjacks.models.Card;
import com.wbjacks.models.Card.Rank;
import com.wbjacks.models.Card.Suite;
import com.wbjacks.models.Hand;
import com.wbjacks.models.HandClassification;
import com.wbjacks.models.HandClassification.Classification;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HandClassifierServiceTest {
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
    @Test
    @Ignore
    public void straightFlushIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void fourOfAKindIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void fullHouseIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void flushIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void straightIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void threeOfAKindIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void twoPairIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void onePairIsClassified() throws Hand.TooManyCardsException, Hand.IncompleteHandException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

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