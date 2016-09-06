package com.wbjacks.models.services;

import com.wbjacks.models.Card;
import com.wbjacks.models.Hand;
import com.wbjacks.models.HandClassification;
import com.wbjacks.models.HandClassification.Classification;

import java.util.List;
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
        // TODO: (wbjacks) Check hand validity?

        if (isHandFiveOfAKind(handRanks, handSuites)) {
            return new HandClassification(Classification.FIVE_OF_A_KIND, highCard);
        } else if (isHandStraightFlush(handRanks, handSuites)) {
            return new HandClassification(Classification.STRAIGHT_FLUSH, highCard);
        } else if (isHandFourOfAKind(handRanks)) {
            return new HandClassification(Classification.FOUR_OF_A_KIND, highCard);
        } else if (isHandFullHouse(handRanks)) {
            return new HandClassification(Classification.FULL_HOUSE, highCard);
        } else if (isHandFlush(handSuites)) {
            return new HandClassification(Classification.FLUSH, highCard);
        } else if (isHandStraight(handRanks)) {
            return new HandClassification(Classification.STRAIGHT, highCard);
        } else if (isHandThreeOfAKind(handRanks)) {
            return new HandClassification(Classification.THREE_OF_A_KIND, highCard);
        } else if (isHandTwoPair(handRanks)) {
            return new HandClassification(Classification.TWO_PAIR, highCard);
        } else if (isHandPair(handRanks)) {
            return new HandClassification(Classification.ONE_PAIR, highCard);
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

    private boolean isHandFullHouse(Map<Card.Rank, Integer> handRanks) {
        List<Integer> rankCountsExcludingJokers = handRanks.entrySet().stream().filter(rankCount -> rankCount.getKey
                () != Card.Rank.JOKER).map(Map.Entry::getValue).collect(Collectors.toList());
        // We only need to check that two ranks exist: the only other combination of two ranks is 4-1, which is
        // four-of-a-kind. With jokers, if there is only a single rank left of 3, then the hand is 5-of-a-kind
        return rankCountsExcludingJokers.size() == 2;
    }

    private boolean isHandFourOfAKind(Map<Card.Rank, Integer> handRanks) {
        return checkNOfAKind(4, handRanks);
    }

    private boolean isHandThreeOfAKind(Map<Card.Rank, Integer> handRanks) {
        return checkNOfAKind(3, handRanks);
    }

    private boolean isHandTwoPair(Map<Card.Rank, Integer> handRanks) {
        // Can only use one joker, as 2 will also win with 3 of a kind. Only needs one actual pair if you have a joker.
        return handRanks.entrySet().stream().filter(rankCount -> rankCount.getKey() != Card.Rank.JOKER).map(Map
                .Entry::getValue).filter(count -> count >= 2).count() >= (handRanks.containsKey(Card.Rank.JOKER) ? 1
                : 2);
    }

    private boolean isHandPair(Map<Card.Rank, Integer> handRanks) {
        return checkNOfAKind(2, handRanks);
    }

    private boolean checkNOfAKind(int n, Map<Card.Rank, Integer> handRanks) {
        int numberOfJokers = handRanks.containsKey(Card.Rank.JOKER) ? handRanks.get(Card.Rank.JOKER) : 0;
        return handRanks.entrySet().stream().filter(rankCount -> rankCount.getKey() != Card.Rank.JOKER).map(Map
                .Entry::getValue).anyMatch(count -> count + numberOfJokers >= n);

    }
}