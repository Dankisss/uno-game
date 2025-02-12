package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;

public class JoinGameCommand extends AbstractCommand {
    private String gameId;
    private final String displayName;
    private UnoGameService gameService;

    public JoinGameCommand(SocketChannel channel, UnoGameService gameService, String gameId, String displayName) {
        super(channel);
        this.gameService = gameService;
        this.gameId = gameId;
        this.displayName = displayName;
    }

    @Override
    public String execute() {
        return gameService.joinGame(channel, gameId, displayName);
    }
}

