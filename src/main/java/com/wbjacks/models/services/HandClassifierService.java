package com.wbjacks.models.services;

import com.wbjacks.models.Card;
import com.wbjacks.models.Hand;
import com.wbjacks.models.HandClassification;
import com.wbjacks.models.HandClassification.Classification;

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
            return new HandClassification(Classification.FIVE_OF_A_KIND, highCard);
        } else if (isHandStraightFlush(handRanks, handSuites)) {
            return new HandClassification(Classification.STRAIGHT_FLUSH, highCard);
        } else if (isHandFlush(handSuites)) {
            return new HandClassification(Classification.FLUSH, highCard);
        } else if (isHandStraight(handRanks)) {
            return new HandClassification(Classification.STRAIGHT, highCard);
        } else if (isHandThreeOfAKind(handRanks)) {
            return new HandClassification(Classification.THREE_OF_A_KIND, highCard);
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
                || handRanks.values().stream().anyMatch(counts -> counts == 3) && handSuites.containsKey(Card.Suite
                .JOKER) && handSuites.get(Card.Suite.JOKER) == 2; // 2 joker
    }

    private boolean isHandStraightFlush(Map<Card.Rank, Integer> handRanks, Map<Card.Suite, Integer> handSuites) {
        return isHandStraight(handRanks) && isHandFlush(handSuites);
    }

    private boolean isHandStraight(Map<Card.Rank, Integer> handRanks) {
        @SuppressWarnings("OptionalGetWithoutIsPresent") int minCardValue = handRanks.keySet().stream().filter(rank
                -> rank != Card.Rank.JOKER).map(Card.Rank::getValue).min(Integer::compare).get();
        if (handRanks.containsKey(Card.Rank.ACE)) { // Ace counts as lowest card if you have A 1 2 3 4 5
            minCardValue = 0;
        }
        for (int i = 1, jokersUsed = 0; i < Hand.NUMBER_OF_CARDS_IN_COMPLETE_HAND; i++) {
            if (!handRanks.containsKey(Card.Rank.getForValue(minCardValue + i).orElse(null))) {
                // If the card value for the straight exists but isn't present and we have a joker, use the joker
                // instead
                if (minCardValue + i <= Card.Rank.ACE.getValue() && handRanks.containsKey(Card.Rank.JOKER) &&
                        jokersUsed < handRanks.get(Card.Rank.JOKER)) {
                    jokersUsed++;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isHandFlush(Map<Card.Suite, Integer> handSuites) {
        int numberOfJokers = handSuites.containsKey(Card.Suite.JOKER) ? handSuites.get(Card.Suite.JOKER) : 0;
        return handSuites.values().stream().anyMatch(count -> count + numberOfJokers >= 5);
    }

    private boolean isHandThreeOfAKind(Map<Card.Rank, Integer> handRanks) {
        int numberOfJokers = handRanks.containsKey(Card.Rank.JOKER) ? handRanks.get(Card.Rank.JOKER) : 0;
        return handRanks.values().stream().anyMatch(count -> count + numberOfJokers >= 3);
    }
}