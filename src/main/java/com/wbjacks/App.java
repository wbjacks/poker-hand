package com.wbjacks;

import com.wbjacks.models.Card;
import com.wbjacks.models.Card.Rank;
import com.wbjacks.models.Card.Suite;
import com.wbjacks.models.Hand;
import com.wbjacks.models.services.HandClassifierService;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        try {
            Hand hand1 = new Hand(new ArrayList() {{
                add(new Card(Rank.TWO, Suite.HEART));
                add(new Card(Rank.TWO, Suite.DIAMOND));
                add(new Card(Rank.TWO, Suite.DIAMOND));
                add(Card.joker());
                add(new Card(Rank.THREE, Suite.SPADE));
            }});
            Hand hand2 = new Hand(new ArrayList() {{
                add(new Card(Rank.SIX, Suite.SPADE));
                add(new Card(Rank.SIX, Suite.HEART));
                add(new Card(Rank.SIX, Suite.DIAMOND));
                add(new Card(Rank.KING, Suite.CLUB));
                add(new Card(Rank.KING, Suite.HEART));
            }});

            HandClassifierService handClassifierService = new HandClassifierService();
            hand1.setHandClassification(handClassifierService.classifyHand(hand1));
            hand2.setHandClassification(handClassifierService.classifyHand(hand2));

            // This is awful
            if (hand1.compareTo(hand2) == 1) {
                System.out.println("Hand 1 wins!");
            }
            else if (hand1.compareTo(hand2) == -1) {
                System.out.println("Hand 2 wins!");
            }
            else if (hand1.compareTo(hand2) == 0) {
                System.out.println("They tie");
            }
        }
        catch (Hand.TooManyCardsException ignored) {}
        catch (Hand.IncompleteHandException ignored) {}
    }
}