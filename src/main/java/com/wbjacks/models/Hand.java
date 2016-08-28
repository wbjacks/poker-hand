package com.wbjacks.models;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    public static final int NUMBER_OF_CARDS_IN_COMPLETE_HAND = 5;
    private final List<Card> _cards = new ArrayList<>();

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

    public static class TooManyCardsException extends Exception {
        public TooManyCardsException() {
            super("Attempt to add more cards than the maximum allowed in a single hand. Maximum number of allowed cards is: " + NUMBER_OF_CARDS_IN_COMPLETE_HAND);
        }
    }

    public static class IncompleteHandException extends Exception {
        public IncompleteHandException() {
            super(String.format("Attempting to access cards in an incomplete hand. A complete hand contains %d cards.", NUMBER_OF_CARDS_IN_COMPLETE_HAND));
        }
    }
}