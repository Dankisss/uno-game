package bg.sofia.uni.fmi.mjt.uno.deck;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.CardColor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.uno.card.factory.UnoCardFactory.*;
import static bg.sofia.uni.fmi.mjt.uno.card.utils.Constants.ZERO;

public class UnoDeck extends Deck {

    private static final List<CardColor> COLORS = List.of(
            CardColor.RED,
            CardColor.GREEN,
            CardColor.BLUE,
            CardColor.YELLOW
    );

    private static final int CARD_COUNT_COLORS = 25;
    private static final int NORMAL_CARD_COUNT = 9;
    private static final int NORMAL_CARD_DUPLICATIONS_COUNT = 2;
    private static final int ACTION_CARD_DUPLICATIONS_COUNT = 2;
    private static final int WILD_CARD_COUNT = 4;

    private UnoDeck(boolean isEmpty) {
        List<Card> cardList = isEmpty ? new ArrayList<>() : fillDeck();
        Collections.shuffle(cardList);

        super(new ArrayDeque<>(cardList));
    }

    public static UnoDeck of() {
        return new UnoDeck(false);
    }

    public static UnoDeck ofEmpty() {
        return new UnoDeck(true);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card getTopCard() {
        return cards.element();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void refill(UnoDeck deck) {
        cards.addAll(deck.cards);
        deck.cards.clear();
    }

    private static List<Card> fillDeck() {
        List<Card> cardList = new ArrayList<>();

        int startIndex = 0;
        for (CardColor color: CardColor.values()) {
            if (!color.equals(CardColor.WILD)) {
                addFromColor(startIndex, cardList, color);
                startIndex += CARD_COUNT_COLORS;
            }
        }

        for (int i = 0; i < WILD_CARD_COUNT; i++) {
            cardList.add(ofPlusFourWildCard(startIndex++));
            cardList.add(ofChangeColorWildCard(startIndex++));
        }

        return cardList;
    }

    private static void addFromColor(int startIndex, List<Card> cardList, CardColor color) {
        cardList.add(ofNormalCard(startIndex, color, ZERO));

        for (int i = 1; i <= NORMAL_CARD_COUNT; i++) {
            for (int j = 0; j < NORMAL_CARD_DUPLICATIONS_COUNT; j++) {
                cardList.add(ofNormalCard(++startIndex, color, String.valueOf(i)));
            }
        }

        for (int i = 0; i < ACTION_CARD_DUPLICATIONS_COUNT; i++) {
            cardList.add(ofPlusTwoCard(++startIndex, color));
            cardList.add(ofSkipCard(++startIndex, color));
            cardList.add(ofReverseCard(++startIndex, color));
        }
    }

}
