package bg.sofia.uni.fmi.mjt.uno.command;

import bg.sofia.uni.fmi.mjt.uno.card.exception.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.uno.command.operations.*;
import bg.sofia.uni.fmi.mjt.uno.game.GameStatus;
import bg.sofia.uni.fmi.mjt.uno.services.PlayerService;
import bg.sofia.uni.fmi.mjt.uno.services.UnoGameService;

import java.nio.channels.SocketChannel;
import java.util.List;

import static bg.sofia.uni.fmi.mjt.uno.card.utils.Validation.isNumeric;

public class CommandParser {

    private static final String ARGUMENT_DELIMITER = "=";
    private static final List<String> GAME_STATUS_OPTIONS = List.of("STARTED", "ENDED", "AVAILABLE", "ALL");

    private final PlayerService playerService = new PlayerService();
    private final UnoGameService gameService = new UnoGameService(playerService);

    public Command parseCommand(SocketChannel channel, String input) {
        String trimmedInput = input.trim();

        String[] parts = trimmedInput.split("\\s+");
        String commandName = parts[0];

        return switch (commandName) {
            case "register" -> ofRegisterCommand(playerService, parts);
            case "login" -> ofLoginCommand(channel, playerService, parts);
            case "logout" -> ofLogoutCommand(channel, playerService);
            case "list-games" -> ofListGamesCommand(channel, gameService, parts);
            case "create-game" -> ofCreateGameCommand(channel, gameService, parts);
            case "join" ->  ofJoinGameCommand(channel, gameService, parts);
            case "start" -> ofStartGameCommand(channel, gameService);
            case "show-hand" -> ofShowHandCommand(channel, playerService);
            case "show-last-card" -> ofShowLastCardCommand(channel, gameService);
            case "play" -> ofPlayCardCommand(channel, gameService, parts);
            default -> throw new IllegalArgumentException("Unknown command: " + commandName);
        };
    }

    private static Command ofRegisterCommand(PlayerService playerService, String[] parts) {
        if (parts.length != 3) {
            throw new InvalidCommandException("The register command is invalid");
        }

        String[] firstArg = parts[1].split(ARGUMENT_DELIMITER);
        String[] secondArg = parts[2].split(ARGUMENT_DELIMITER);

        if (isArgumentInvalid(firstArg)|| isArgumentInvalid(secondArg)) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (firstArg[0].equals("--username") && secondArg[0].equals("--password")) {
            return new RegisterCommand(playerService, firstArg[1], secondArg[1]);
        } else if (firstArg[0].equals("--password") && secondArg[0].equals("--username")) {
            return new RegisterCommand(playerService, secondArg[1], firstArg[1]);
        } else {
            throw new InvalidCommandException("The register command is invalid");
        }
    }

    private static Command ofLoginCommand(SocketChannel channel, PlayerService playerService, String[] parts) {
        if (parts.length != 3) {
            throw new InvalidCommandException("The login command is invalid");
        }

        String[] firstArg = parts[1].split(ARGUMENT_DELIMITER);
        String[] secondArg = parts[2].split(ARGUMENT_DELIMITER);

        if (isArgumentInvalid(firstArg) || isArgumentInvalid(secondArg)) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (firstArg[0].equals("--username") && secondArg[0].equals("--password")) {
            return new LoginCommand(channel, playerService, firstArg[1], secondArg[1]);
        } else if (firstArg[0].equals("--password") && secondArg[0].equals("--username")) {
            return new LoginCommand(channel, playerService, secondArg[1], firstArg[1]);
        } else {
            throw new InvalidCommandException("The login command is invalid");
        }
    }

    private static Command ofLogoutCommand(SocketChannel channel, PlayerService playerService) {
        return new LogoutCommand(channel, playerService);
    }

    private static Command ofListGamesCommand(SocketChannel channel, UnoGameService gameService, String[] parts) {
        if (parts.length != 2 && parts.length != 1) {
            throw new InvalidCommandException("Invalid parameters");
        }

        if (parts.length == 1) {
            return new ListGamesCommand(channel, gameService, null);
        }

        String uppercaseArgument = getGameStatus(parts);

        if (uppercaseArgument.equalsIgnoreCase("ALL")) {
            return new ListGamesCommand(channel, gameService, null);
        }
        
        return new ListGamesCommand(channel, gameService, GameStatus.valueOf(uppercaseArgument));
    }

    private static String getGameStatus(String[] parts) {
        String argumentOne = parts[1];
        String[] argumentOneParts = argumentOne.split(ARGUMENT_DELIMITER);

        if (!argumentOneParts[0].equals("--status") || isArgumentInvalid(argumentOneParts)) {
            throw new InvalidCommandException("Invalid argument");
        }

        String uppercaseArgument = argumentOneParts[1].toUpperCase();
        if (!GAME_STATUS_OPTIONS.contains(uppercaseArgument)) {
            throw new InvalidCommandException("Invalid status option");
        }
        return uppercaseArgument;
    }

    private static Command ofCreateGameCommand(SocketChannel channel, UnoGameService gameService, String[] parts) {
        if (parts.length != 3) {
            throw new InvalidCommandException("The login command is invalid");
        }

        String[] firstArg = parts[1].split(ARGUMENT_DELIMITER);
        String[] secondArg = parts[2].split(ARGUMENT_DELIMITER);

        if (isArgumentInvalid(firstArg) || isArgumentInvalid(secondArg)) {
            throw new IllegalArgumentException("Invalid parameters");
        }

        if (firstArg[0].equals("--number-of-players") && secondArg[0].equals("--game-id")) {
            return createGame(channel, gameService, firstArg[1], secondArg[1]);
        } else if (firstArg[0].equals("--game-id") && secondArg[0].equals("--number-of-players")) {
            return createGame(channel, gameService, secondArg[1], firstArg[1]);
        } else {
            throw new InvalidCommandException("The login command is invalid");
        }
    }

    private static Command createGame(SocketChannel channel, UnoGameService gameService, String argOne, String argTwo) {
        if (!isNumeric(argOne)) {
            throw new InvalidCommandException("The number of players must be a number");
        }

        return new CreateGameCommand(channel, gameService, Integer.parseInt(argOne), argTwo);
    }

    private static boolean isArgumentInvalid(String[] argument) {
        return argument.length != 2;
    }

    private static Command ofJoinGameCommand(SocketChannel channel, UnoGameService gameService, String[] parts) {
        if (parts.length != 2 && parts.length != 3) {
            throw new InvalidCommandException("The command is invalid");
        }

        if (parts.length == 2) {
           return joinGameNoDisplayName(channel, gameService, parts);
        }

        String[] argOne = parts[1].split(ARGUMENT_DELIMITER);
        String[] argTwo = parts[2].split(ARGUMENT_DELIMITER);

        if (argOne[0].equals("--game-id") && argTwo[0].equals("--display-name")) {
            return joinGameDisplayName(channel, gameService, argOne[1], argTwo[1]);
        } else if (argOne[0].equals("--display-name") && argTwo[0].equals("--game-id")) {
            return joinGameDisplayName(channel, gameService, argTwo[1], argOne[1]);
        } else {
            throw new InvalidCommandException("The command is invalid. Usage join --game-id=<game-id> --display-name=<display-name>");
        }
    }

    private static Command joinGameNoDisplayName(SocketChannel channel, UnoGameService gameService, String[] parts) {
        String[] parameterOne = parts[1].split(ARGUMENT_DELIMITER);

        if (!parameterOne[0].equals("--game-id")) {
            throw new InvalidCommandException("Invalid command. Please provide the game id of the game you want to join");
        }

        return new JoinGameCommand(channel, gameService, parameterOne[1], null);
    }

    private static Command joinGameDisplayName(SocketChannel channel, UnoGameService gameService, String gameId, String displayName) {
        return new JoinGameCommand(channel, gameService, gameId, displayName);
    }

    private static Command ofStartGameCommand(SocketChannel channel, UnoGameService gameService) {
        return new StartGameCommand(channel, gameService);
    }

    private static Command ofShowHandCommand(SocketChannel channel, PlayerService playerService) {
        return new ShowHandCommand(channel, playerService);
    }

    private static Command ofShowLastCardCommand(SocketChannel channel, UnoGameService gameService) {
        return new ShowLastCardCommand(channel, gameService);
    }

    private static Command ofPlayCardCommand(SocketChannel channel, UnoGameService gameService, String[] parts) {
        if (parts.length != 2) {
            throw new InvalidCommandException("The play card command is invalid");
        }

        String[] parameterOne = parts[1].split(ARGUMENT_DELIMITER);

        if (!parameterOne[0].equals("--card-id")) {
            throw new InvalidCommandException("The command parameter is invalid");
        }

        if (!isNumeric(parameterOne[1])) {
            throw new InvalidCommandException(parameterOne[1] + " is not a positive number");
        }

        return new PlayCardCommand(channel, gameService, Integer.parseInt(parameterOne[1]));
    }
}
