package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.CardColor;
import bg.sofia.uni.fmi.mjt.uno.card.exception.GameAlreadyStartedException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.InvalidCardException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.InvalidGameException;
import bg.sofia.uni.fmi.mjt.uno.deck.UnoDeck;
import bg.sofia.uni.fmi.mjt.uno.game.history.GameHistory;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.uno.card.utils.Validation.checkReference;

public class UnoCardGame implements UnoGame, MultiplayerGame {

    private static final int INITIAL_DRAW = 7;

    private final List<GameHistory> gameHistory = new ArrayList<>();
    private final List<Player> remainingPlayers;
    private final List<Player> finishedPlayers;
    private final UnoDeck unoStartDeck;
    private final UnoDeck unoToAddDeck;
    private GameStatus gameStatus = GameStatus.AVAILABLE;
    private boolean isClockwiseDirection;
    private int currentPlayerIndex = -1;
    private Player currentPlayer;
    private CardColor currentColor;

    private UnoCardGame(List<Player> finishedPlayers, boolean isClockwiseDirection) {
        this.unoStartDeck = UnoDeck.of();
        this.unoToAddDeck = UnoDeck.ofEmpty();
        this.remainingPlayers = new ArrayList<>();
//        remainingPlayers.add(owner);

        this.finishedPlayers = finishedPlayers;
        this.isClockwiseDirection = isClockwiseDirection;
    }

    public static UnoCardGame of() {
        return new UnoCardGame(new ArrayList<>(), false);
    }

    @Override
    public void playCard(Player player, Card card) {
        Card lastPlayedCard = currentPlayer.lastPlayedCard();

//        player.playCard();
        if (!isCardPlayable(lastPlayedCard)) {
            throw new InvalidCardException("Neither the color nor the value of this card matches");
        }

        if (unoStartDeck.isEmpty()) {
            unoStartDeck.refill(unoToAddDeck);
            addFirstCard();
        }

        lastPlayedCard.play(this);

        unoToAddDeck.addCard(lastPlayedCard);

        gameHistory.add(new GameHistory(currentPlayer, currentPlayer.lastPlayedCard()));

        nextPlayer();
    }

    @Override
    public void playWildCard(Player player, Card card, CardColor color) {
        checkReference(color);

        Card lastPlayed = currentPlayer.lastPlayedCard();

        if (!lastPlayed.color().equals(CardColor.WILD)) {
            throw new InvalidCardException("This card is not wild");
        }

        currentPlayer.lastPlayedCard().play(this);

        if (unoStartDeck.isEmpty()) {
            unoStartDeck.refill(unoToAddDeck);
            addFirstCard();
        }

        currentColor = color;
        unoToAddDeck.addCard(lastPlayed);

        gameHistory.add(new GameHistory(currentPlayer, currentPlayer.lastPlayedCard()));
        nextPlayer();
    }

    @Override
    public List<Player> remainingPlayers() {
        return Collections.unmodifiableList(remainingPlayers);
    }

    @Override
    public int remainingPlayersCount() {
        return remainingPlayers.size();
    }

    @Override
    public UnoDeck toAddDeck() {
        return unoStartDeck;
    }

    public UnoDeck startDeck() {
        return unoStartDeck;
    }

    public Player currentPlayer() {
        return currentPlayer;
    }

    @Override
    public Player nextPlayer() {
        moveToNextPlayer();

        currentPlayer = remainingPlayers.get(currentPlayerIndex);
        return currentPlayer;
    }

    @Override
    public List<GameHistory> history() {
        return Collections.unmodifiableList(gameHistory);
    }

    public void joinPlayer(Player player) {
        checkReference(player);

        remainingPlayers.add(player);
    }

    public void startGame() {

        if (gameStatus != GameStatus.AVAILABLE) {
            throw new GameAlreadyStartedException("Game has already started!");
        }

        if (remainingPlayers.size() < 2) {
            throw new InvalidGameException("At least two players are required to start the game!");
        }

        gameStatus = GameStatus.STARTED;

        for (Player p : remainingPlayers) {
            p.drawCards(unoStartDeck, unoToAddDeck, INITIAL_DRAW);
        }

        Card firstCard = unoStartDeck.removeTop();

        while (firstCard.color().equals(CardColor.WILD)) {
            unoToAddDeck.addCard(firstCard);
            firstCard = unoStartDeck.removeTop();
        }

        currentColor = firstCard.color();
        unoToAddDeck.addCard(firstCard);

        nextPlayer();
    }

    public List<Player> finishedPlayers() {
        return Collections.unmodifiableList(finishedPlayers);
    }

    public void skipPlayer() {
        moveToNextPlayer();
    }

    public void changeDirection() {
        isClockwiseDirection = !isClockwiseDirection;
    }

    public GameStatus gameStatus() {
        return gameStatus;

    }

    private void addFirstCard() {
        Card firstCard = unoStartDeck.removeTop();
        while (firstCard.color().equals(CardColor.WILD)) {
            unoToAddDeck.addCard(firstCard);
            firstCard = unoStartDeck.removeTop();
        }

        currentColor = firstCard.color();
        unoToAddDeck.addCard(firstCard);
    }

    private void moveToNextPlayer() {
        if (isClockwiseDirection) {
            currentPlayerIndex = currentPlayerIndex - 1 < 0 ? remainingPlayers.size() - 1 : currentPlayerIndex - 1;
        } else {
            currentPlayerIndex = currentPlayerIndex + 1 >= remainingPlayers.size() ? 0 : currentPlayerIndex + 1;
        }
    }

    public boolean isCardPlayable(Card card) {
        return !card.color().equals(currentColor) ||
                (!card.value().equals(unoStartDeck.getTopCard().value()) && !unoStartDeck.getTopCard().color().equals(CardColor.WILD));
    }

}
