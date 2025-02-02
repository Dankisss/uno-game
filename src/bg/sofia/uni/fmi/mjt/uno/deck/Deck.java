package bg.sofia.uni.fmi.mjt.uno.deck;

import bg.sofia.uni.fmi.mjt.uno.card.Card;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

public abstract class Deck {

    protected final Queue<Card> cards;

    public Deck(Queue<Card> cards) {
        this.cards = cards;
    }

    public Card removeTop() {
        return cards.remove();
    }

}
