package bg.sofia.uni.fmi.mjt.uno.card.server;

import bg.sofia.uni.fmi.mjt.uno.command.Command;
import bg.sofia.uni.fmi.mjt.uno.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.uno.command.CommandParser;
import bg.sofia.uni.fmi.mjt.uno.game.GameRoom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class UnoServer {
    private static final int PORT = 8080;
    private final Selector selector;
    private final ServerSocketChannel serverChannel;
    private final CommandExecutor executor = new CommandExecutor();
    private final CommandParser parser = new CommandParser();

    public UnoServer() throws IOException {
        selector = Selector.open();
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(PORT));
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Uno Server started on port " + PORT);
    }

    public void start() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    acceptConnection();
                } else if (key.isReadable()) {
                    processRequest(key);
                }
            }
        }
    }

    private void acceptConnection() throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        System.out.println("New connection: " + clientChannel.getRemoteAddress());
        sendMessage(clientChannel, "Welcome to Uno! Use 'create-room' or 'join-room'.");
    }

    private void processRequest(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int bytesRead = clientChannel.read(buffer);

        if (bytesRead == -1) {
            disconnectClient(clientChannel);
            return;
        }

        buffer.flip();
        String command = new String(buffer.array(), 0, buffer.limit()).trim();
        System.out.println("Received: " + command);

        handleCommand(clientChannel, command);
    }

    private void handleCommand(SocketChannel clientChannel, String input) throws IOException {

        try {
            Command command = parser.parseCommand(clientChannel, input);
            String message = executor.executeCommand(command);
            sendMessage(clientChannel, message);
        } catch (RuntimeException e) {
            e.printStackTrace();
            sendMessage(clientChannel, e.getMessage());
        }

    }

    private GameRoom getPlayerGameRoom(SocketChannel channel) {
//        return gameRooms.values().stream()
//                .filter(room -> room.getPlayers().contains(channel))
//                .findFirst()
//                .orElse(null);
        return null;
    }

    private void sendMessage(SocketChannel clientChannel, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap((message + "\n").getBytes());
        clientChannel.write(buffer);
    }

    private void disconnectClient(SocketChannel clientChannel) throws IOException {
//        System.out.println("Player disconnected: " + clientChannel.getRemoteAddress());
//        playerService.remove(clientChannel);
//
//        GameRoom room = getPlayerGameRoom(clientChannel);
//        if (room != null) {
//            room.getPlayers().remove(clientChannel);
//            room.broadcastMessage("A player has left.");
//        }
//
//        clientChannel.close();
    }

    public static void main(String[] args) {
        try {
            UnoServer server = new UnoServer();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
