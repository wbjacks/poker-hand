package com.wbjacks.poker_hand.models;

import java.util.Arrays;
import java.util.Optional;

public class Card {
    private Suite _suite;
    private Rank _rank;

    public static Card joker() {
        return new Card(Rank.JOKER, Suite.JOKER);
    }

    public Card(Rank rank, Suite suite) {
        _rank = rank;
        _suite = suite;
    }

    public Suite getSuite() {
        return _suite;
    }

    public Rank getRank() {
        return _rank;
    }

    public enum Suite {
        HEART, DIAMOND, CLUB, SPADE, JOKER
    }

    public enum Rank {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(11),
        QUEEN(12),
        KING(13),
        ACE(14),
        JOKER(-1);

        private int _value;

        Rank(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }

        public static Optional<Rank> getForValue(int value) {
            return Arrays.stream(Rank.values()).filter(rank -> rank.getValue() == value).findFirst();
        }
    }
}
