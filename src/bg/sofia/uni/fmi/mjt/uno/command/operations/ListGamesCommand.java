package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;

public class ListGamesCommand extends AbstractCommand {
    private final GameStatus status;
    private final UnoGameService gameService;

    public ListGamesCommand(SocketChannel channel, UnoGameService gameService, GameStatus status) {
        super(channel);
        this.gameService = gameService;
        this.status = status;
    }

    @Override
    public String execute() {
        return gameService.listGames(status);
    }
}

