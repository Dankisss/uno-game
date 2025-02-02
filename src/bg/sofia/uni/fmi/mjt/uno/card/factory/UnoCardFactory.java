package bg.sofia.uni.fmi.mjt.uno.card.factory;

import bg.sofia.uni.fmi.mjt.uno.card.ActionCard;
import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.NormalCard;
import bg.sofia.uni.fmi.mjt.uno.card.WildCard;
import bg.sofia.uni.fmi.mjt.uno.game.GameAPI;

public class UnoCardFactory {
    private static final String SKIP_CARD = "SKIP";
    private static final String REVERSE_CARD = "REVERSE";
    private static final String PLUS_TWO_CARD = "PLUS2";
    private static final String PLUS_FOUR_CARD = "PLUS4";
    private static final String CHANGE_COLOR_CARD = "CHANGE_COLOR";

    private static final int ACTION_CARD_DRAW = 2;
    private static final int WILD_CARD_DRAW = 4;

    public static Card ofNormalCard(String color, String value) {
        return new NormalCard(color, value, GameAPI::playCard);
    }

    public static Card ofSkipCard(String color) {
        return new ActionCard(color, SKIP_CARD, (game, player) -> {
            game.playCard(player);
            game.skipPlayer();
        });
    }

    public static Card ofReverseCard(String color) {
        return new ActionCard(color, REVERSE_CARD, (game, player) -> {
            game.playCard(player);

            if (game.remainingPlayersCount() <= 2) {
                game.skipPlayer();
            } else {
                game.changeDirection();
            }

        });
    }

    public static Card ofPlusTwoCard(String color) {
        return new ActionCard(color, PLUS_TWO_CARD, (game, player) -> {
            game.playCard(player);
            game.nextPlayer().drawCards(game.deck(), ACTION_CARD_DRAW);
        });
    }

    public static Card ofPlusFourWildCard() {
        return new WildCard(PLUS_FOUR_CARD, (game, player) -> {
            game.playCard(player);
            game.nextPlayer().drawCards(game.deck(), WILD_CARD_DRAW);
        });
    }

    public static Card ofChangeColorWildCard() {
        return new WildCard(CHANGE_COLOR_CARD, ((game, player) -> {
            game.playCard(player);
            game.chooseColor(player);
        }));
    }
}
