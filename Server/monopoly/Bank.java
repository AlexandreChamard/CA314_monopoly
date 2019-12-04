
package monopoly;

class Bank {
    Gameplate game;

    public Bank(Gameplate _game) {
        game = _game; // do not do this. use master.getGame(p);
    }

    boolean bankrupt(Player p) {
        return true;
    }

}