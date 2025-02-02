package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.util.List;

public interface GameAPI {

    void playCard(Player player);

    List<Player> remainingPlayers();

    int remainingPlayersCount();

    UnoDeck deck();

    Player nextPlayer();

}
