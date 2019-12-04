
package monopoly;

import java.util.Vector;

enum PropertyColor {
    BROWN,
    LBLUE,
    PURPLE,
    ORANGE,
    RED,
    YELOW,
    GREEN,
    BLUE,
    STATION,
    UTILITIES,
    SPECIAL
}

interface IProperty {
    public void     apply(Gameplate game, Player p);

    public void     setNewOwner(Player p);
    public boolean  buyHomes(Gameplate game, Player p, int n); // sell homes if n < 0

    public PropertyColor getColor();
    public int      getNumHomes();
    public Player   getOwner();
    public int[]    getPrices();
    public int      computePrice(Gameplate game);
    public void     mortgage(Gameplate game, Player p);
}

class Property implements IProperty {
    protected int id; /*id of a property, no.00 to no.28*/
    protected PropertyColor color; /*colour of a property, eg blue, red*/
    protected int[] prices;
    protected Player owner; /*owner for a property, bank or player*/
    protected boolean mortgaged = false; /*mortgaged price of a property*/
    protected int homes = 0; /* number of homes on the property */

    public void apply(Gameplate game, Player p) {
        if (p == game.bank) {
            /** rule want buy */
        } else if (p != owner && mortgaged == false) {
            game.bank.transfert(p, owner, computePrice(game));
        } else if (p == owner && mortgaged == true) {
            if (game.bank.tryTransfert(owner, game.bank, (int)((double)prices[0]*1.1)) == true) { // 10% interest
                mortgaged = false;
                // @notify player unmortgaged
            } else {
                // @notify player fail unmortgaged
            }
        } else {
            /** @notify error */
        }
    }

    public int getNumHomes() {
        return homes;
    }

    public PropertyColor getColor() {
        return color;
    }

    public void setNewOwner(Player p) {
        owner = p;
    }

    public Player getOwner() {
        return owner;
    }

    public int[] getPrices() {
        return prices;
    }

    public int    computePrice(Gameplate game) {
        if (homes > 0) {
            return prices[homes+1];
        }
        Vector<Property> properties = game.promotor.getColor(color);
        for (Property prop : properties) {
            if (prop.getOwner() != owner)
                return prices[1];
        }
        return prices[1]*2;
    }

    public void mortgage(Gameplate game, Player p) {
        if (p == owner && mortgaged == false && homes == 0) {
            game.bank.transfert(game.bank, p, prices[0] / 2);
            mortgaged = true;
            /** @notify player it has mortgaged. */
        } else {
            /** @notify player it failed to mortaged. */
        }
    }

    public boolean buyHomes(Gameplate game, Player p, int n) {
        assert n != 0;

        if (p == owner) {
            if (homes + n > 5 || homes + n < 0) {
                /** @notify internal error */
                return false;
            }
            boolean ret;
            if (n > 0) {
                ret = game.bank.tryTransfert(p, game.bank, prices[7] * n);
            } else {
                ret = game.bank.tryTransfert(game.bank, p, (prices[7] * -n) / 2);
            }
            if (ret == true) {
                homes += n;
                /** @notify player that it buy n homes */
            } else {
                /** @notify player that it fail to buy */

            }
            return ret;
        }
        /** @notify access error */
        return false;
    }
}

