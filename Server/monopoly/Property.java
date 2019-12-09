
package monopoly;

import java.util.Vector;

enum PropertyColor {
    BROWN,
    LBLUE,
    PURPLE,
    ORANGE,
    RED,
    YELLOW,
    GREEN,
    BLUE,
    STATION,
    UTILITY,
    SPECIAL
}

interface IProperty {
    public void     apply(Gameplate game, Player p);

    public void     setNewOwner(Player p);
    public boolean  buyHomes(Gameplate game, Player p, int n); // sell homes if n < 0

    public PropertyColor getColor();
    public int      getId();
    public int      getNumHomes();
    public Player   getOwner();
    public int[]    getPrices();
    public int      computePrice(Gameplate game);
    public void     mortgage(Gameplate game, Player p);
}

class Property implements IProperty {
    protected int id; /*id of a property, no.00 to no.28*/
    protected Player owner; /*owner for a property, bank or player*/
    protected PropertyColor color; /*colour of a property, eg blue, red*/
    protected int[] prices;
    protected boolean mortgaged = false; /*mortgaged price of a property*/
    protected int homes = 0; /* number of homes on the property */

    public Property(Player _owner, int _id, PropertyColor _color, int[] _prices) {
        assert _prices.length == 8;
        id = _id;
        owner = _owner;
        color = _color;
        prices = _prices;
    }

    public void apply(Gameplate game, Player p) {
        if (p == game.bank) {
            /** @rule want buy */
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

    public int getId() {
        return id;
    }

    public int getNumHomes() {
        return homes;
    }

    public PropertyColor getColor() {
        return color;
    }

    public void setNewOwner(Player p) {
        try {
            Gameplate game = Master.getInstance().getCurrentGame(p);

            game.broadcast(new ErrorState(109, game.getPlayerInfos(p).id+","+id));
            owner = p;
        } catch (ErrorState e) {
            System.out.println(e.code+":"+e.message);
        }
    }

    public Player getOwner() {
        return owner;
    }

    public int[] getPrices() {
        return prices;
    }

    public int computePrice(Gameplate game) {
        if (mortgaged == true || owner == game.bank)
            return 0;

        if (homes > 0) {
            return prices[homes+1];
        }
        Vector<IProperty> properties = game.promotor.getColor(color);
        for (IProperty prop : properties) {
            if (prop.getOwner() != owner)
                return prices[1];
        }
        return prices[1]*2;
    }

    public void mortgage(Gameplate game, Player p) {
        if (p == owner && mortgaged == false && homes == 0) {
            game.bank.transfert(game.bank, p, prices[0] / 2);
            mortgaged = true;
            game.broadcast(new ErrorState(110, Integer.toString(id)));
        } else {
            System.out.println("game "+game.getId()+" fail to mortgaged "+id);
        }
    }

    public boolean buyHomes(Gameplate game, Player p, int n) {
        assert n != 0;

        if (p == owner) {
            if (homes + n > 5 || homes + n < 0) {
                System.out.println("game "+game.getId()+" bad number of homes in "+id);
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
                game.broadcast(new ErrorState(112, id+","+homes));
            } else {
                System.out.println("game "+game.getId()+" fail to add "+n+" homes in "+id);
            }
            return ret;
        }
        System.out.println("game "+game.getId()+" access error in Property "+id);
        return false;
    }
}



class Station implements IProperty {
    protected int id; /*id of a property, no.00 to no.28*/
    protected Player owner; /*owner for a property, bank or player*/
    protected PropertyColor color; /*colour of a property, eg blue, red*/
    protected int[] prices;
    protected boolean mortgaged = false; /*mortgaged price of a property*/

    public Station(Player _owner, int _id, int[] _prices) {
        assert _prices.length == 5;

        id = _id;
        owner = _owner;
        color = PropertyColor.STATION;
        prices = _prices;
    }

    public void apply(Gameplate game, Player p) {
        if (p == game.bank) {
            /** @rule want buy */
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

    public void setNewOwner(Player p) {
        owner = p;
    }

    public boolean buyHomes(Gameplate game, Player p, int n) {
        /** @notify internal error */
        return false;
    }

    public PropertyColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getNumHomes() {
        /** @notify internal error */
        return -1;
    }

    public Player getOwner() {
        return owner;
    }

    public int[] getPrices() {
        return prices;
    }

    public int computePrice(Gameplate game) {
        if (mortgaged == true || owner == game.bank)
            return 0;

        Vector<IProperty> properties = game.promotor.getColor(color);
        assert properties.size() == 4;
        int n = 0;
        for (IProperty prop : properties) {
            if (prop.getOwner() != owner)
                ++n;
        }
        assert n > 0;
        return prices[n];
    }

    public void mortgage(Gameplate game, Player p) {
        if (p == owner && mortgaged == false) {
            game.bank.transfert(game.bank, p, prices[0] / 2);
            mortgaged = true;
            /** @notify player it has mortgaged. */
        } else {
            /** @notify player it failed to mortaged. */
        }
    }
}



class UtilityProperty implements IProperty {
    protected int id; /*id of a property, no.00 to no.28*/
    protected Player owner; /*owner for a property, bank or player*/
    protected PropertyColor color; /*colour of a property, eg blue, red*/
    protected int[] prices;
    protected boolean mortgaged = false; /*mortgaged price of a property*/

    public UtilityProperty(Player _owner, int _id, int[] _prices) {
        assert _prices.length == 3;

        id = _id;
        owner = _owner;
        color = PropertyColor.UTILITY;
        prices = _prices;
    }

    public void apply(Gameplate game, Player p) {
        if (p == game.bank) {
            /** @rule want buy */
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

    public void setNewOwner(Player p) {
        owner = p;
    }

    public boolean buyHomes(Gameplate game, Player p, int n) {
        /** @notify internal error */
        return false;
    }

    public PropertyColor getColor() {
        return color;
    }

    public int getId() {
        return id;
    }

    public int getNumHomes() {
        /** @notify internal error */
        return -1;
    }

    public Player getOwner() {
        return owner;
    }

    public int[] getPrices() {
        return prices;
    }

    public int computePrice(Gameplate game) {
        if (mortgaged == true || owner == game.bank)
            return 0;

        Vector<IProperty> properties = game.promotor.getColor(color);
        int n = 0;
        for (IProperty prop : properties) {
            if (prop.getOwner() != owner)
                ++n;
        }
        assert n > 0;
        int[] dice = game.dice.roll();
        
        return (dice[0] + dice[1]) * prices[n];
    }

    public void mortgage(Gameplate game, Player p) {
        if (p == owner && mortgaged == false) {
            game.bank.transfert(game.bank, p, prices[0] / 2);
            mortgaged = true;
            /** @notify player it has mortgaged. */
        } else {
            /** @notify player it failed to mortaged. */
        }
    }
}