package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.card.exception.PlayerNotLoggedInException;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.*;

public class GameRoom {
    private final UnoCardGame game;
    private final Map<SocketChannel, Player> players = new HashMap<>();
    private final int maxPlayers;

    public GameRoom(int maxPlayers) {
        this.game = UnoCardGame.of();
        this.maxPlayers = maxPlayers;
    }

    public int remainingSlots() {
        return maxPlayers - players.size();
    }

    public UnoCardGame game() {
        return game;
    }

    public void addPlayer(SocketChannel channel, Player player) {
        players.put(channel, player);
        game.joinPlayer(player);
    }

    public Collection<SocketChannel> channels() {
        return players.keySet();
    }

    public Collection<Player> players() {
        return players.values();
    }

    public void broadcastMessage(String message, SocketChannel channel) {
        for (SocketChannel ch : players.keySet()) {
            if (!ch.equals(channel)) {
                ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes());

                try {
                    ch.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void sendMessage(String message, Player player) {
        SocketChannel channel = players.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(player))
                .findFirst()
                .orElseThrow(() -> new PlayerNotLoggedInException("Player left"))
                .getKey();

        ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes());

        try {
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "maxPlayers=" + maxPlayers +
                ", game=" + game;
    }
}
