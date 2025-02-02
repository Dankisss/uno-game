package bg.sofia.uni.fmi.mjt.uno.deck;

import bg.sofia.uni.fmi.mjt.uno.card.Card;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.uno.card.factory.UnoCardFactory.*;
import static bg.sofia.uni.fmi.mjt.uno.card.utils.Constants.ZERO;

public class UnoDeck extends Deck {

    private static final List<String> COLORS = List.of(
            "RED",
            "GREEN",
            "BLUE",
            "YELLOW"
    );
    private static final int NORMAL_CARD_COUNT = 9;
    private static final int NORMAL_CARD_DUPLICATIONS_COUNT = 2;
    private static final int ACTION_CARD_DUPLICATIONS_COUNT = 2;
    private static final int WILD_CARD_COUNT = 4;

    public UnoDeck() {
        List<Card> cardList = fillDeck();
        Collections.shuffle(cardList);

        super(new ArrayDeque<>(cardList));
    }

    private static List<Card> fillDeck() {
        List<Card> cardList = new ArrayList<>();

        COLORS.forEach(color -> addFromColor(cardList, color));

        for (int i = 0; i < WILD_CARD_COUNT; i++) {
            cardList.add(ofPlusFourWildCard());
            cardList.add(ofChangeColorWildCard());
        }

        return cardList;
    }

    private static void addFromColor(List<Card> cardList, String color) {
        cardList.add(ofNormalCard(color, ZERO));

        for (int i = 1; i <= NORMAL_CARD_COUNT; i++) {
            for (int j = 0; j < NORMAL_CARD_DUPLICATIONS_COUNT; j++) {
                cardList.add(ofNormalCard(color, String.valueOf(i)));
            }
        }

        for (int i = 0; i < ACTION_CARD_DUPLICATIONS_COUNT; i++) {
            cardList.add(ofPlusTwoCard(color));
            cardList.add(ofSkipCard(color));
            cardList.add(ofReverseCard(color));
        }
    }

}
