
package monopoly;

import java.util.Queue;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import monopoly.Cards.*;

class Deck {
    private Queue<Card> cards;

    Deck(Card[] _cards) {
        List<Card> cs = Arrays.asList(_cards);
        Collections.shuffle(cs);
        for (Card c : cs) {
            cards.add(c);
        }
        /** shuffle cards */
        /** improve cards with all of cards. */
    }

    public Card drawCard()
    {
        return cards.poll();
    }

    public void addCard(Card c)
    {
        cards.add(c);
    }
}

class ChanceDeck {
    public static final Card[] allCards = {
        new TestCard(),
    };
}