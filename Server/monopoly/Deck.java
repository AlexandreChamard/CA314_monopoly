
package monopoly;

import java.util.Queue;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import monopoly.Cards.*;

abstract class Deck {
    private Queue<Card> cards;

    Deck() {
        List<Card> cs = Arrays.asList(getDeck());
        Collections.shuffle(cs);
        for (Card c : cs) {
            cards.add(c);
        }
        /** shuffle cards */
        /** improve cards with all of cards. */
    }

    protected abstract Card[] getDeck();

    public Card drawCard()
    {
        return cards.poll();
    }

    public void addCard(Card c)
    {
        cards.add(c);
    }
}

final class ChanceDeck extends Deck {
    public static final Card[] allCards = {
        new TestCard(),
    };

    protected final Card[] getDeck() {
        return allCards;
    }
}

final class CommunityDeck extends Deck {
    public static final Card[] allCards = {
        new TestCard(),
    };

    protected final Card[] getDeck() {
        return allCards;
    }
}