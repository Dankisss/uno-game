package bg.sofia.uni.fmi.mjt.uno.player;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;

import java.util.List;

import static bg.sofia.uni.fmi.mjt.uno.card.utils.Validation.checkIndex;

public class Player {

    private final String username;
    private final List<Card> remainingCards;
    private Card lastPlayedCard;

    public Player(String username, List<Card> remainingCards) {
        this.username = username;
        this.remainingCards = remainingCards;
    }

    public void drawCards(UnoDeck unoDeck, int amount) {
        for (int i = 0; i < amount; i++) {
            remainingCards.add(unoDeck.removeTop());
        }
    }

    public void playCard(int index) {
        checkIndex(index, remainingCards);

        lastPlayedCard = remainingCards.get(index);
    }

    public Card lastPlayedCard() {
        return lastPlayedCard;
    }

}
