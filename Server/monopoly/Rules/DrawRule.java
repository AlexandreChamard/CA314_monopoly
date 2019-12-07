
package monopoly;

abstract class DrawRule implements Rule {
    public void apply(Gameplate game, Player p) {
        Deck deck = getDeck(game);
        Card card = deck.drawCard();
        try {
            card.doEffect(game, p);
        } catch (ErrorState e) {
            /** I think there is an error with the Card implementation and its throw. */
        }
    }

    abstract public Deck getDeck(Gameplate game);
}

class ChanceDrawRule extends DrawRule {
    public Deck getDeck(Gameplate game) {
        return game.chanceDeck;
    }
}

class CommunityDrawRule extends DrawRule {
    public Deck getDeck(Gameplate game) {
        return game.communityDeck;
    }
}