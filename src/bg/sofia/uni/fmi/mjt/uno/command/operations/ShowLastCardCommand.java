package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;

public class ShowLastCardCommand extends AbstractCommand {

    private final UnoGameService gameService;

    public ShowLastCardCommand(SocketChannel channel, UnoGameService gameService) {
        super(channel);
        this.gameService = gameService;
    }

    @Override
    public String execute() {
        return gameService.showLastCard(channel);
    }
}
