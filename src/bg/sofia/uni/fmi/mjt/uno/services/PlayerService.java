package bg.sofia.uni.fmi.mjt.uno.services;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerIsNotRegisteredException;
import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerNotLoggedInException;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerService {
    private final Set<Player> players = new HashSet<>();
    private final Map<SocketChannel, Player> channels = new HashMap<>();
    private final Set<Player> loggedPlayers = new HashSet<>();

    public String register(String username, String password) {
        if (playerAlreadyExists(username, password)) {
            throw new PlayerAlreadyExistsException("The player username already exists");
        }

        players.add(new Player(username, password));
        return "Successful registration";
    }

    private boolean playerAlreadyExists(String username, String password) {
        return players.contains(new Player(username, password));
    }

    public String login(SocketChannel channel, String username, String password) {
        if (loggedPlayers.contains(new Player(username, password))) {
            throw new PlayerAlreadyExistsException("The player is already logged in");
        }

        if (!players.contains(new Player(username, password))) {
            throw new PlayerIsNotRegisteredException("The player does not exist. You should register first.");
        }

        channels.put(channel, new Player(username, password));
        return "Logged in successfully";
    }

    public String logout(SocketChannel channel) {
        Player loggedOutPlayer = channels.put(channel, null);
        loggedPlayers.remove(loggedOutPlayer);

        return "Logged out successfully";
    }

    public Player getPlayer(SocketChannel channel) {
        if (!channels.containsKey(channel)) {
            throw new PlayerNotLoggedInException("You need to log in first");
        }

        return channels.get(channel);
    }

    public String showHand(SocketChannel channel) {
        Player player = channels.get(channel);

        return player.hand()
                .stream()
                .map(Card::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
