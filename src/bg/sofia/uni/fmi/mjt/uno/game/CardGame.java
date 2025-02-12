package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.game.history.GameHistory;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.util.List;

public interface CardGame {

    void playCard(Player player, Card card);

    UnoDeck toAddDeck();

    List<GameHistory> history();
}
