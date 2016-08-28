package com.wbjacks;

import com.wbjacks.models.Card;
import com.wbjacks.models.Card.Rank;
import com.wbjacks.models.Card.Suite;
import com.wbjacks.models.Hand;
import com.wbjacks.models.services.HandClassifierService;
import com.wbjacks.models.services.WinDecisionService;

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
            WinDecisionService winDecisionService = new WinDecisionService(new HandClassifierService());
            System.out.println(String.format("Hand1 %ss!", winDecisionService.decideVictory(hand1, hand2)));
        }
        catch (Hand.TooManyCardsException ignored) {
            System.out.println("Too many cards in hand.");
        }

        catch (Hand.IncompleteHandException ignored) {
            System.out.println("Can't classify incomplete hand.");
        }
    }
}