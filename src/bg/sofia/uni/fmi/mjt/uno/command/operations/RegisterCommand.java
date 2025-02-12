package bg.sofia.uni.fmi.mjt.uno.command.operations;

import bg.sofia.uni.fmi.mjt.uno.command.Command;
import bg.sofia.uni.fmi.mjt.uno.services.PlayerService;

import java.nio.channels.SocketChannel;

public class RegisterCommand implements Command {
    private PlayerService userService;
    private String username;
    private String password;

    public RegisterCommand(PlayerService userService, String username, String password) {
        this.userService = userService;
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        return userService.register(username, password);
    }
}

