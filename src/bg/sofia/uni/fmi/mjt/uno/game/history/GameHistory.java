package bg.sofia.uni.fmi.mjt.uno.game.history;

import bg.sofia.uni.fmi.mjt.uno.card.Card;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

public record GameHistory(Player player, Card playedCard) {
}
