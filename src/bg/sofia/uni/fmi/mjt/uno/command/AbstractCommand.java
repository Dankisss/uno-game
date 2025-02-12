package bg.sofia.uni.fmi.mjt.uno.command;

import java.nio.channels.SocketChannel;

public abstract class AbstractCommand implements Command {
    protected SocketChannel channel;

    public AbstractCommand(SocketChannel channel) {
        this.channel = channel;
    }
}
