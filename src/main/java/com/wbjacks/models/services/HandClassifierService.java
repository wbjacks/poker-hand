package com.wbjacks.models.services;

import com.wbjacks.models.Card;
import com.wbjacks.models.Hand;
import com.wbjacks.models.HandClassification;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HandClassifierService {
    public HandClassifierService() {
    }

    public HandClassification classifyHand(Hand hand) throws Hand.IncompleteHandException {
        Map<Card.Rank, Integer> handRanks = getHandRankCounts(hand);
        Map<Card.Suite, Integer> handSuites = getHandSuiteCounts(hand);
        Card.Rank highCard = getHighCard(handRanks);

        if (isHandFiveOfAKind(handRanks, handSuites)) {
            return new HandClassification(HandClassification.Classification.FIVE_OF_A_KIND, highCard);
        } else {
            return new HandClassification(HandClassification.Classification.NONE, highCard);
        }
    }

    // Will throw for empty hand
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Card.Rank getHighCard(Map<Card.Rank, Integer> handRanks) {
        return handRanks.keySet().stream().max((c1, c2) -> Integer.compare(c1.getValue(), c2.getValue())).get();
    }

    private Map<Card.Rank, Integer> getHandRankCounts(Hand hand) throws Hand.IncompleteHandException {
        return hand.getCards().stream().map(Card::getRank).collect(Collectors.groupingBy(Function.identity(),
                Collectors.summingInt(rank -> 1)));
    }

    private Map<Card.Suite, Integer> getHandSuiteCounts(Hand hand) throws Hand.IncompleteHandException {
        return hand.getCards().stream().map(Card::getSuite).collect(Collectors.groupingBy(Function.identity(),
                Collectors.summingInt(rank -> 1)));
    }

    private boolean isHandFiveOfAKind(Map<Card.Rank, Integer> handRanks, Map<Card.Suite, Integer> handSuites) {
        return handSuites.keySet().size() == 5 && handRanks.values().stream().anyMatch(counts -> counts == 4) // 1 joker
                || handRanks.values().stream().anyMatch(counts -> counts == 3) && handSuites.get(Card.Suite.JOKER) ==
                2; // 2 joker
    }
}