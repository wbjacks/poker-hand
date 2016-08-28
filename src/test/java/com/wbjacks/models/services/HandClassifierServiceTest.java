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
    public void fiveOfAKindIsClassified() throws Hand.TooManyCardsException {
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
    @Ignore
    public void straightFlushIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{
        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void fourOfAKindIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void fullHouseIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void flushIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void straightIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void threeOfAKindIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void twoPairIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void onePairIsClassified() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }

    @Test
    @Ignore
    public void noClassification() throws Hand.TooManyCardsException {
        Hand hand = new Hand(new ArrayList() {{

        }});
        HandClassification handClassification = new HandClassifierService().classifyHand(hand);
        assertEquals(Classification.FIVE_OF_A_KIND, handClassification.getClassification());
        assertEquals(Rank.KING, handClassification.getHighCard());
    }
}