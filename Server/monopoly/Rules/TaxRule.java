
package monopoly;

abstract class TaxRule implements Rule {
    public void apply(Gameplate game, Player p) {
        game.bank.transfert(p, game.bank, amount());
    }

    abstract int amount();
}

class IncomeTaxRule extends TaxRule {
    int amount() {
        return 200;
    }
}

class SuperTaxRule extends TaxRule {
    int amount() {
        return 100;
    }
}