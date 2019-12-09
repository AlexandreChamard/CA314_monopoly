
package monopoly;

import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

class Bank implements Player {
    private Gameplate game;
    private Map<Player, Integer> accounts;

    public Bank(Gameplate _game, Vector<Player> players) {
        game = _game;
        accounts = new HashMap<Player, Integer>();
        accounts.put(this, new Integer(42424242));
        for (Player p : players) {
            accounts.put(p, new Integer(1500));
        }
    }

    public boolean transfert(Player pFrom, Player pTo, int n) {
        assert n > 0;
        if (bankrupt(pFrom) == true || bankrupt(pTo) == true)
            return false;

        Integer pFromA = getAccount(pFrom);
        Integer pToA = getAccount(pTo);

        if (pFromA.intValue() < n) {
            Rule r = new BankruptRule(pFromA.intValue() + n);
            r.apply(game, pFrom);
            if (bankrupt(pFrom) == true)
                return false;
            pFromA = getAccount(pFrom);
        }

        int pFromNA = pFromA.intValue() - n;
        int pToNA = pToA.intValue() - n;

        accounts.put(pFrom, new Integer(pFromNA));
        accounts.put(pTo, new Integer(pToNA));
        if (pFrom != this)
            game.broadcast(new ErrorState(105, game.getPlayerInfos(pFrom)+","+accounts.get(pFromNA)));
        if (pTo != this)
            game.broadcast(new ErrorState(105, game.getPlayerInfos(pTo)+","+accounts.get(pToNA)));
        return true;
    }

    public boolean tryTransfert(Player pFrom, Player pTo, int n) {
        assert n > 0;
        if (bankrupt(pFrom) == true || bankrupt(pTo) == true)
            return false;

        Integer pFromA = getAccount(pFrom);
        Integer pToA = getAccount(pTo);

        if (pFromA.intValue() < n)
            return false;

        int pFromNA = pFromA.intValue() - n;
        int pToNA = pToA.intValue() - n;

        accounts.put(pFrom, new Integer(pFromNA));
        accounts.put(pTo, new Integer(pToNA));
        if (pFrom != this)
            game.broadcast(new ErrorState(105, game.getPlayerInfos(pFrom).id+","+accounts.get(pFromNA)));
        if (pTo != this)
            game.broadcast(new ErrorState(105, game.getPlayerInfos(pTo).id+","+accounts.get(pToNA)));
        return true;
    }

    public void setBankrupt(Player p) {
        accounts.put(p, null);
        game.broadcast(new ErrorState(106, Integer.toString(game.getPlayerInfos(p).id)));
    }

    public boolean bankrupt(Player p) {
        return getAccount(p) == null;
    }

    public Integer getAccount(Player p) {
        return accounts.getOrDefault(p, null);
    }

    /** Player implementation */

    public void    send(String msg) {}
    public boolean isRunning() { return true; }
    public void    stopConnection() {}
    public void    close() {}
    public void    join() {}

}