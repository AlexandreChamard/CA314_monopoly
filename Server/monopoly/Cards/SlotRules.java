
package monopoly;

class GoRule implements Rule {
    public void apply(Gameplate game, Player p) {
        game.bank.transfert(game.bank, p, 200);
        /** send modification to player p */
    }
}
