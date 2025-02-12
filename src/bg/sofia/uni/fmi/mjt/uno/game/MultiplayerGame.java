package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.util.List;

public interface MultiplayerGame {

    List<Player> remainingPlayers();

    int remainingPlayersCount();

    Player nextPlayer();

}
