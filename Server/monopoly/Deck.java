
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
        new SetPositionChanceCard(0),
        new SetPositionChanceCard(-1),
        new SetPositionChanceCard(-1),
        new AdvanceToUtilityChanceCard(),
        new AdvanceToRailroadChanceCard(),
        new ReceiveChanceCard(50),
        // new null, // Get out of jail
        new AdvanceChanceCard(-3),
        new GoToJailChanceCard(),
        new GoToJailChanceCard(),
        new PayEachHomeChanceCard(25, 100),
        new PayChanceCard(15),
        new SetPositionChanceCard(15),
        new SetPositionChanceCard(39),
        new PayEachPlayerChanceCard(50),
        new ReceiveChanceCard(150),
        new ReceiveChanceCard(100)
    };

    protected final Card[] getDeck() {
        return allCards;
    }
}

final class CommunityDeck extends Deck {
    public static final Card[] allCards = {
        new SetPositionCommunityCard(0),
        new ReceiveCommunityCard(200),
        new PayCommunityCard(50),
        new ReceiveCommunityCard(50),
        // new null, // Get out of jail
        new GoToJailCommunityCard(),
        new GoToJailCommunityCard(),
        new ReceiveEachPlayerCommunityCard(50),
        new ReceiveCommunityCard(100),
        new ReceiveCommunityCard(20),
        new ReceiveEachPlayerCommunityCard(10),
        new ReceiveCommunityCard(100),
        new PayCommunityCard(50),
        new PayCommunityCard(50),
        new ReceiveCommunityCard(25),
        new PayEachHomeCommunityCard(40, 115),
        new ReceiveCommunityCard(10),
        new ReceiveCommunityCard(100)
    };

    protected final Card[] getDeck() {
        return allCards;
    }
}