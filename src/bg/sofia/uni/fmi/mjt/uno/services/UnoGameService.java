package bg.sofia.uni.fmi.mjt.uno.services;

import bg.sofia.uni.fmi.mjt.uno.card.exception.GameAlreadyStartedException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.InvalidGameException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerAlreadyCreatedGameException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerAlreadyInGameException;
import bg.sofia.uni.fmi.mjt.uno.game.GameRoom;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UnoGameService {
    private final Map<String, GameRoom> games = new HashMap<>();
    private final Map<Player, GameRoom> playerGames = new HashMap<>();
    private final PlayerService playerService;

    public UnoGameService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public String createGame(SocketChannel channel, String gameId, int numberOfPLayers) {
        if (games.containsKey(gameId)) {
            throw new GameAlreadyStartedException("The game with the provided ID already exists");
        }

        if (numberOfPLayers < 2) {
            throw new InvalidGameException("The max number of players should be at at least two");
        }

        Player creator = playerService.getPlayer(channel);

        if (playerGames.containsKey(creator)) {
            throw new PlayerAlreadyCreatedGameException("You have already created a game.");
        }

        games.put(gameId, new GameRoom(numberOfPLayers));
        return "Game created successfully";
    }

    public String listGames(GameStatus status) {
        if (games.isEmpty()) {
            return "No games created";
        }

        if (status == null) {
            return games
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + " remaining slots: " + entry.getValue().remainingSlots())
                    .collect(Collectors.joining(System.lineSeparator()));
        }

        return games
                .values()
                .stream()
                .filter(gameRoom -> gameRoom.game().gameStatus().equals(status))
                .map(GameRoom::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String joinGame(SocketChannel channel, String gameId, String displayName) {
        Player player = playerService.getPlayer(channel);
        player.setDisplayName(displayName);

        if (isPlayerAlreadyJoinedGame(player)) {
            throw new PlayerAlreadyInGameException("You have already joined another game");
        }

        GameRoom gameRoom = games.get(gameId);

        if (gameRoom.remainingSlots() <= 0) {
            throw new GameAlreadyStartedException("The game is full. Please join another game");
        }

        gameRoom.addPlayer(channel, player);
        gameRoom.broadcastMessage(player.displayName() + " joined the game", channel);

        return "You joined the game successfully";
    }

    private boolean isPlayerAlreadyJoinedGame(Player player) {
        return games
                .values()
                .stream()
                .anyMatch(gameRoom -> gameRoom.players().contains(player));
    }
}
