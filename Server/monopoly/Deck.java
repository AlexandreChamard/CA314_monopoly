
package monopoly;

import java.util.Queue;

import monopoly.Cards.*;

class Deck {
    private Queue<Card> cards;

    Deck(Card[] cards) {
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