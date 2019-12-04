
package monopoly;

import java.util.Vector;

class Promotor {
    private Gameplate       game;
    private Vector<Property> properties;

    public Promotor(Gameplate _game) {
        game = _game;
        properties = new Vector<Property>();
    }

    public void mortgage(int id, Player p) {
        Property prop = getProperty(id);
        if (prop != null) {
            prop.mortgage(game, p);
        } else {
            /** @notify internal error */
        }
    }

    public void unMortgage(int id, Player p) {
        Property prop = getProperty(id);
        if (prop != null) {
            prop.apply(game, p);
        } else {
            /** @notify internal error */
        }
    }

    public int[] getPropertyPrices(int id) {
        Property prop = getProperty(id);

        if (prop != null) {
            return prop.getPrices();
        } else {
            return null;
        }
    }

    public void addHomes(int id, Player p, int n) {
        Property prop = getProperty(id);

        if (prop != null) {
            prop.buyHomes(game, p, n);
        }
    }

    public Property getProperty(int p) {
        return properties.get(p);
    }

    public Vector<Property> getColor(PropertyColor c) {
        Vector<Property> p = new Vector<Property>();
        for (Property prop : properties) {
            if (prop.getColor() == c)
                p.add(prop);
        }
        return p;
    }

    public void addProperty(Property p) { // do not use directly
        properties.add(p);
    }

    public void removeProperty(Property p) { // do not use directly
        properties.remove(p);
    }

}