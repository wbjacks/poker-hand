package com.wbjacks.models;

public class HandClassification {
    private Classification _classification;
    private Card.Rank _highCard;

    public HandClassification(Classification classification, Card.Rank highCard) {
        _classification = classification;
        _highCard = highCard;
    }

    public Classification getClassification() {
        return _classification;
    }

    public Card.Rank getHighCard() {
        return _highCard;
    }

    public enum Classification {
        FIVE_OF_A_KIND(9),
        STRAIGHT_FLUSH(8),
        FOUR_OF_A_KIND(7),
        FULL_HOUSE(6),
        FLUSH(5),
        STRAIGHT(4),
        THREE_OF_A_KIND(3),
        TWO_PAIR(2),
        ONE_PAIR(1),
        NONE(0);

        private int _value;

        Classification(int value) {
            _value = value;
        }

        public int getValue() {
            return _value;
        }
    }
}
