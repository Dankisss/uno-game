package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.PlayerService;

import java.nio.channels.SocketChannel;

public class ShowHandCommand extends AbstractCommand {

    private final PlayerService playerService;

    public ShowHandCommand(SocketChannel channel, PlayerService playerService) {
        super(channel);
        this.playerService = playerService;
    }

    @Override
    public String execute() {
        return playerService.showHand(channel);
    }
}
