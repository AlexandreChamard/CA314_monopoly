
package monopoly;

class GoRule implements Rule {
    public void apply(Gameplate game, Player p) {
        game.bank.transfert(game.bank, p, 200);
        /** @notify to player p */ // maybe not because bank.tranfert does
    }
}

class FreeParkingRule implements Rule {
    public void apply(Gameplate game, Player p) {
        /** do nothing */
    }
}

class VisitJailRule implements Rule {
    public void apply(Gameplate game, Player p) {
        /** do nothing */
    }
}

class GoToJailRule implements Rule {
    public void apply(Gameplate game, Player p) {
        game.moveToPrison(p);
    }
}