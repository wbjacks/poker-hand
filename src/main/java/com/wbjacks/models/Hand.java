package com.wbjacks.models;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int MAX_NUMBER_OF_CARDS_IN_HAND = 6;
    private final List<Card> _cards = new ArrayList<>();

    // TODO: (wbjacks) assert maximum size?
    public Hand(List<Card> cards) throws TooManyCardsException {
        addCards(cards);
    }

    public void addCards(List<Card> cards) throws TooManyCardsException {
        if (cards.size() + _cards.size() > MAX_NUMBER_OF_CARDS_IN_HAND) {
            throw new TooManyCardsException();
        }
        _cards.addAll(cards);
    }

    public static class TooManyCardsException extends Exception {
        public TooManyCardsException() {
            super("Attempt to add more cards than the maximum allowed in a single hand. Maximum number of allowed cards is: " + MAX_NUMBER_OF_CARDS_IN_HAND);
        }
    }
}