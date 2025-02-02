package bg.sofia.uni.fmi.mjt.uno.card;

import bg.sofia.uni.fmi.mjt.uno.game.GameAPI;
import bg.sofia.uni.fmi.mjt.uno.game.UnoGame;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

public interface CardEffect {

    void applyEffect(UnoGame unoGame, Player player);

}
