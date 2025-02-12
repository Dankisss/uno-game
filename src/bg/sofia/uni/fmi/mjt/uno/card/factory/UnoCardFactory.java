package bg.sofia.uni.fmi.mjt.uno.card.factory;

import bg.sofia.uni.fmi.mjt.uno.card.ActionCard;
import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.NormalCard;
import bg.sofia.uni.fmi.mjt.uno.card.WildCard;
import bg.sofia.uni.fmi.mjt.uno.card.CardColor;
import bg.sofia.uni.fmi.mjt.uno.game.UnoCardGame;

public class UnoCardFactory {
    private static final String SKIP_CARD = "SKIP";
    private static final String REVERSE_CARD = "REVERSE";
    private static final String PLUS_TWO_CARD = "PLUS2";
    private static final String PLUS_FOUR_CARD = "PLUS4";
    private static final String CHANGE_COLOR_CARD = "CHANGE_COLOR";

    private static final int ACTION_CARD_DRAW = 2;
    private static final int WILD_CARD_DRAW = 4;

    public static Card ofNormalCard(int index, CardColor color, String value) {
        return new NormalCard(index, color, value, (_) -> {});
    }

    public static Card ofSkipCard(int index, CardColor color) {
        return new ActionCard(index, color, SKIP_CARD, UnoCardGame::skipPlayer);
    }

    public static Card ofReverseCard(int index, CardColor color) {
        return new ActionCard(index, color, REVERSE_CARD, (game) -> {
            if (game.remainingPlayersCount() <= 2) {
                game.skipPlayer();
            } else {
                game.changeDirection();
            }

        });
    }

    public static Card ofPlusTwoCard(int index, CardColor color) {
        return new ActionCard(index, color, PLUS_TWO_CARD, (game) -> {
            game.nextPlayer().drawCards(game.startDeck(), game.toAddDeck(), ACTION_CARD_DRAW);
        });
    }

    public static Card ofPlusFourWildCard(int index) {
        return new WildCard(index, PLUS_FOUR_CARD, (game) -> {
            game.nextPlayer().drawCards(game.startDeck(), game.toAddDeck(), WILD_CARD_DRAW);
        });
    }

    public static Card ofChangeColorWildCard(int index) {
        return new WildCard(index, CHANGE_COLOR_CARD, ((_) -> {}));
    }
}
