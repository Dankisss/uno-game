package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.command.Command;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;

public class CreateGameCommand extends AbstractCommand {

    private final UnoGameService gameService;
    private final String gameId;
    private final int numberOfPlayers;

    public CreateGameCommand(SocketChannel channel, UnoGameService gameService, int numberOfPlayers, String gameId) {
        super(channel);
        this.gameService = gameService;
        this.gameId = gameId;
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public String execute() {
        return gameService.createGame(channel, gameId, numberOfPlayers);
    }

}

