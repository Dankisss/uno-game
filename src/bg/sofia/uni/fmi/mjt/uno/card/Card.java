package bg.sofia.uni.fmi.mjt.uno.card;

import bg.sofia.uni.fmi.mjt.uno.game.UnoCardGame;
import bg.sofia.uni.fmi.mjt.uno.player.Player;

public abstract class Card {

    int index;
    private final CardColor color;
    private final String value;
    private final CardEffect effect;

    public Card(int index, CardColor color, String value, CardEffect effect) {
        this.index = index;
        this.color = color;
        this.value = value;
        this.effect = effect;
    }

    public void play(UnoCardGame unoGame) {
        effect.applyEffect(unoGame);
    }

    public int index() {
        return index;
    }

    public CardColor color() {
        return color;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return "index=" + index +
                ", color=" + color +
                ", value=" + value;
    }
}
