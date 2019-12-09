
package monopoly;

import java.util.Vector;

abstract class CommunityCard implements Card {
    public void doEffect(Gameplate game, Player p) {
        /** @notify missing the message of the card */
        game.broadcast(new ErrorState(113, ""));
        effect(game, p);
        game.communityDeck.addCard(this);
    }

    abstract void effect(Gameplate game, Player p);
}

class SetPositionCommunityCard extends CommunityCard {
    int pos;

    public SetPositionCommunityCard(int _pos) {
        pos = _pos;
    }

    public void effect(Gameplate game, Player p) {
        game.setPosition(p, pos);
        game.applySlotRule(p);
    }
}

class AdvanceCommunityCard extends CommunityCard {
    int pos;

    public AdvanceCommunityCard(int _pos) {
        pos = _pos;
    }

    public void effect(Gameplate game, Player p) {
        game.advance(p, pos);
        game.applySlotRule(p);
    }
}

class AdvanceToUtilityCommunityCard extends CommunityCard {
    public void effect(Gameplate game, Player p) {
        PlayerInfos infos = game.getPlayerInfos(p);

        if (infos.pos >= 20) {
            game.advance(p, 28 - infos.pos);
        } else {
            game.advance(p, 12 - infos.pos);
        }
        game.applySlotRule(p);
    }
}

class AdvanceToRailroadCommunityCard extends CommunityCard {
    public void effect(Gameplate game, Player p) {
        PlayerInfos infos = game.getPlayerInfos(p);

        if (infos.pos >= 30) {
            game.setPosition(p, 35);
        } else if (infos.pos >= 20) {
            game.setPosition(p, 25);
        } else if (infos.pos >= 10) {
            game.setPosition(p, 15);
        } else {
            game.setPosition(p, 5);
        }
        game.applySlotRule(p);
    }
}

class ReceiveCommunityCard extends CommunityCard {
    int n;

    public ReceiveCommunityCard(int _n) {
        n = _n;
    }

    public void effect(Gameplate game, Player p) {
        game.bank.transfert(game.bank, p, n);
    }
}

class ReceiveEachPlayerCommunityCard extends CommunityCard {
    int n;

    public ReceiveEachPlayerCommunityCard(int _n) {
        n = _n;
    }

    public void effect(Gameplate game, Player p) {
        for (Player player : game.players) {
            if (game.canPlay(player) == true)
                game.bank.transfert(player, p, n);
        }
    }
}

class PayCommunityCard extends CommunityCard {
    int n;

    public PayCommunityCard(int _n) {
        n = _n;
    }

    public void effect(Gameplate game, Player p) {
        game.bank.transfert(p, game.bank, n);
    }
}

class PayEachPlayerCommunityCard extends CommunityCard {
    int n;

    public PayEachPlayerCommunityCard(int _n) {
        n = _n;
    }

    public void effect(Gameplate game, Player p) {
        for (Player player : game.players) {
            if (game.canPlay(player) == true)
                game.bank.transfert(p, player, n);
        }
    }
}

class PayEachHomeCommunityCard extends CommunityCard {
    int homePrice, hotelPrice;

    public PayEachHomeCommunityCard(int _homePrice, int _hotelPrice) {
        homePrice = _homePrice;
        hotelPrice = _hotelPrice;
    }

    public void effect(Gameplate game, Player p) {
        Vector<IProperty> properties = game.promotor.getProperties(p);
        int total = 0;
        for (IProperty prop : properties) {
            switch (prop.getColor()) {
                case STATION: case UTILITY: case SPECIAL:
                    continue;
                default:
                    int n = prop.getNumHomes();
                    if (n == 5) {
                        total += hotelPrice;
                    } else {
                        total += n * homePrice;
                    }
                    break;
            }
        }
        game.bank.transfert(p, game.bank, total);
    }
}

class GoToJailCommunityCard extends CommunityCard {
    public void effect(Gameplate game, Player p) {
        game.moveToPrison(p);
    }
}