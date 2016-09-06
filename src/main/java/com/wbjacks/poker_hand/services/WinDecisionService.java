package com.wbjacks.poker_hand.services;

import com.wbjacks.poker_hand.models.Hand;

public class WinDecisionService {
    private HandClassifierService _handClassifierService;

    public WinDecisionService(HandClassifierService handClassifierService) {
        _handClassifierService = handClassifierService;
    }

    public Decision decideVictory(Hand hand1, Hand hand2) throws Hand.IncompleteHandException {
        hand1.setHandClassification(_handClassifierService.classifyHand(hand1));
        hand2.setHandClassification(_handClassifierService.classifyHand(hand2));
        if (hand1.getHandClassification().getClassification().getValue() > hand2.getHandClassification().getClassification().getValue()) {
            return Decision.WIN;
        }
        else if (hand1.getHandClassification().getClassification().getValue() < hand2.getHandClassification().getClassification().getValue()) {
            return Decision.LOSE;
        }
        else {
            return Decision.TIE; // NOTE: See README with information on ties
        }
    }

    public enum Decision {
        WIN, LOSE, TIE;
    }
}
