package bg.sofia.uni.fmi.mjt.uno.card;

public class WildCard extends Card {

    private static final String WILD_CARD_COLOR = "WILD";

    public WildCard(String value, CardEffect effect) {
        super(WILD_CARD_COLOR, value, effect);
    }

}
