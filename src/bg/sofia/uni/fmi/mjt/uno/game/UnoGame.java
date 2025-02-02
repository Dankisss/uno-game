package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.game.history.GameHistory;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnoGame implements GameAPI {

    private final List<GameHistory> gameHistory = new ArrayList<>();
    private final UnoDeck unoDeck;
    private final List<Player> remainingPlayers;
    private final List<Player> finishedPlayers;
    private GameStatus gameStatus;
    private final boolean isClockwiseDirection;

    public UnoGame(UnoDeck unoDeck, List<Player> remainingPlayers, List<Player> finishedPlayers, boolean isClockwiseDirection) {
        this.unoDeck = unoDeck;
        this.remainingPlayers = remainingPlayers;
        this.finishedPlayers = finishedPlayers;
        this.isClockwiseDirection = isClockwiseDirection;
    }

    @Override
    public void playCard(Player player) {
        //TODO:
        //index should be taken from NIO Communication
        player.playCard(0);

        gameHistory.add(new GameHistory(player, player.lastPlayedCard()));
    }

    @Override
    public List<Player> remainingPlayers() {
        return Collections.unmodifiableList(remainingPlayers);
    }

    @Override
    public int remainingPlayersCount() {
        return remainingPlayers.size();
    }

    public List<Player> finishedPlayers() {
        return Collections.unmodifiableList(finishedPlayers);
    }

    @Override
    public UnoDeck deck() {
        return unoDeck;
    }

    public void skipPlayer() {

    }

    public void changeDirection() {

    }

    public void chooseColor(Player player) {

    }

    @Override
    public Player nextPlayer() {
        return null;
    }
}
