package bg.sofia.uni.fmi.mjt.uno.player;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.game.UnoCardGame;

import java.util.*;

import static bg.sofia.uni.fmi.mjt.uno.card.utils.Validation.checkIndex;

public class Player {

    private final String username;
    private String displayName;
    private final String password;
    private Map<Integer,Card> cards;
    private Card lastPlayedCard;

    public Player(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName == null ? username : displayName;
    }

    public void drawCards(UnoDeck unoStartDeck, UnoDeck toAddDeck, int amount) {
        if (cards == null) {
            cards = new HashMap<>();
        }

        for (int i = 0; i < amount; i++) {
            if (unoStartDeck.isEmpty()) {
                unoStartDeck.refill(toAddDeck);
            }

            Card top = unoStartDeck.removeTop();

            cards.put(top.index(), top);
        }
    }

    public Card playCard(int index) {
        checkIndex(index, cards);

        lastPlayedCard = cards.remove(index);

        return cards.get(index);
    }

    public Card lastPlayedCard() {
        return lastPlayedCard;
    }

    public Collection<Card> hand() {
        return cards.values();
    }

    public void joinGame(UnoCardGame game) {
        game.joinPlayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '}';
    }
}
