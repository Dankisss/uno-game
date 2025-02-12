package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.PlayerService;

import java.nio.channels.SocketChannel;

public class LogoutCommand extends AbstractCommand {
    private final PlayerService playerService;

    public LogoutCommand(SocketChannel channel, PlayerService playerService) {
        super(channel);
        this.playerService = playerService;
    }

    @Override
    public String execute()  {
        return playerService.logout(channel);
    }

}
