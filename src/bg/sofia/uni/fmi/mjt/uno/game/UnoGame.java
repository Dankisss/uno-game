package bg.sofia.uni.fmi.mjt.uno.game;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.card.CardColor;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

public interface UnoGame extends CardGame {

    void playWildCard(Player player, Card card, CardColor color);

}
