package bg.sofia.uni.fmi.mjt.uno.command;

public class CommandExecutor {

    public String executeCommand(Command command) {
        return command.execute();
    }

}
