package com.wbjacks.models.services;

import com.wbjacks.models.Hand;

import java.util.List;
import java.util.stream.Collectors;

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
            // Tie, compare highest cards
            List<Integer> thisValueRankings = hand1.getCards().stream().map(card -> card.getRank().getValue()).sorted().collect(Collectors.toList());
            List<Integer> thatValueRankings = hand2.getCards().stream().map(card -> card.getRank().getValue()).sorted().collect(Collectors.toList());
            for (Integer thisValue : thisValueRankings) {
                Integer thatValue = thatValueRankings.remove(0);
                if (thisValue > thatValue) {
                    return Decision.WIN;
                } else if (thisValue < thatValue) {
                    return Decision.LOSE;
                }
            }
            return Decision.TIE;
        }
    }

    public enum Decision {
        WIN, LOSE, TIE;
    }
}
