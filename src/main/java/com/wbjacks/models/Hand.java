package com.wbjacks.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand> {
    public static final int NUMBER_OF_CARDS_IN_COMPLETE_HAND = 5;
    private final List<Card> _cards = new ArrayList<>();
    private HandClassification _handClassification;

    // TODO: (wbjacks) assert maximum size?
    public Hand(List<Card> cards) throws TooManyCardsException {
        addCards(cards);
    }

    public void addCards(List<Card> cards) throws TooManyCardsException {
        if (cards.size() + _cards.size() > NUMBER_OF_CARDS_IN_COMPLETE_HAND) {
            throw new TooManyCardsException();
        }
        _cards.addAll(cards);
    }

    public List<Card> getCards() throws IncompleteHandException {
        if (_cards.size() != NUMBER_OF_CARDS_IN_COMPLETE_HAND) {
            throw new IncompleteHandException();
        }
        return _cards;
    }

    public HandClassification getHandClassification() {
        return _handClassification;
    }

    public void setHandClassification(HandClassification handClassification) {
        _handClassification = handClassification;
    }

    @Override
    public int compareTo(Hand hand) {
        int classificationCompareValue = Integer.compare(this.getHandClassification().getClassification().getValue(),
                hand.getHandClassification().getClassification().getValue());
        if (classificationCompareValue == 0) {
            // Tie, compare highest cards
            try {
                List<Integer> thisValueRankings = this.getCards().stream().map(card -> card.getRank().getValue())
                        .sorted().collect(Collectors.toList());
                List<Integer> thatValueRankings = hand.getCards().stream().map(card -> card.getRank().getValue())
                        .sorted().collect(Collectors.toList());
                for (Integer thisValue : thisValueRankings) {
                    int compareValue = Integer.compare(thisValue, thatValueRankings.remove(0));
                    if (compareValue != 0) {
                        return compareValue;
                    }
                }
                return 0;
            } catch (IncompleteHandException e) {
                return 0; // TODO: (wbjacks) Log error
            }
        }
        return classificationCompareValue;
    }

    public static class TooManyCardsException extends Exception {
        public TooManyCardsException() {
            super("Attempt to add more cards than the maximum allowed in a single hand. Maximum number of allowed " +
                    "cards is: " + NUMBER_OF_CARDS_IN_COMPLETE_HAND);
        }
    }

    public static class IncompleteHandException extends Exception {
        public IncompleteHandException() {
            super(String.format("Attempting to access cards in an incomplete hand. A complete hand contains %d cards"
                    + ".", NUMBER_OF_CARDS_IN_COMPLETE_HAND));
        }
    }
}