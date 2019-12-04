
package monopoly;

class BankruptRule implements Rule {
    int n;

    public BankruptRule(int _n) {
        n = _n;
    }

    public void apply(Gameplate game, Player p) {
        /** remove items from the player p until it have the amount n in its bank account or nothing to sell */
        /** if the ammount is not reached, set player in bankrupty. */
        game.bank.setBankrupt(p);
    }
}