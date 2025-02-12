package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.AbstractCommand;
import bg.sofia.uni.fmi.mjt.uno.services.PlayerService;

import java.nio.channels.SocketChannel;

public class LoginCommand extends AbstractCommand {
    private final PlayerService userService;
    private final String username;
    private final String password;

    public LoginCommand(SocketChannel channel, PlayerService userService, String username, String password) {
        super(channel);
        this.userService = userService;
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        return userService.login(channel, username, password);
    }
}
