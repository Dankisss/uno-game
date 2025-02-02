package bg.sofia.uni.fmi.mjt.uno.card;

import bg.sofia.uni.fmi.mjt.uno.game.UnoGame;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

public abstract class Card {

    private final String color;
    private final String value;
    private final CardEffect effect;

    public Card(String color, String value, CardEffect effect) {
        this.color = color;
        this.value = value;
        this.effect = effect;
    }

    public void play(UnoGame unoGame, Player player) {
        effect.applyEffect(unoGame, player);
    }

    @Override
    public String toString() {
        return "Card{" +
                "color='" + color + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
