package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;

public class PlayCardCommand extends AbstractCommand {

    private final UnoGameService gameService;
    private int cardId;

    public PlayCardCommand(SocketChannel channel, UnoGameService gameService, int cardId) {
        super(channel);
        this.gameService = gameService;
        this.cardId = cardId;
    }

    @Override
    public String execute() {
        return gameService.playCard(channel, cardId);
    }
}
