
package monopoly;

import java.util.Vector;

class Promotor {
    private Gameplate       game;
    private Vector<IProperty> properties;

    public Promotor(Gameplate _game) {
        game = _game;
        properties = new Vector<IProperty>();
        properties.add(new Property(game.bank, 0, PropertyColor.BROWN,   new int[]{60,  2,  10,  30,  90,   160,  250,  50}));
        properties.add(new Property(game.bank, 1, PropertyColor.BROWN,   new int[]{60,  4,  20,  60,  180,  320,  450,  50}));
        properties.add(new Property(game.bank, 2, PropertyColor.LBLUE,   new int[]{100, 6,  30,  90,  270,  400,  550,  50}));
        properties.add(new Property(game.bank, 3, PropertyColor.LBLUE,   new int[]{100, 6,  30,  90,  270,  400,  550,  50}));
        properties.add(new Property(game.bank, 4, PropertyColor.LBLUE,   new int[]{120, 8,  40,  100, 300,  450,  600,  50}));
        properties.add(new Property(game.bank, 5, PropertyColor.PURPLE,  new int[]{140, 10, 50,  150, 450,  625,  750,  100}));
        properties.add(new Property(game.bank, 6, PropertyColor.PURPLE,  new int[]{140, 10, 50,  150, 450,  625,  750,  100}));
        properties.add(new Property(game.bank, 7, PropertyColor.PURPLE,  new int[]{160, 12, 60,  180, 500,  700,  900,  100}));
        properties.add(new Property(game.bank, 8, PropertyColor.ORANGE,  new int[]{180, 14, 70,  200, 550,  750,  950,  100}));
        properties.add(new Property(game.bank, 9, PropertyColor.ORANGE,  new int[]{180, 14, 70,  200, 550,  750,  950,  100}));
        properties.add(new Property(game.bank, 10, PropertyColor.ORANGE, new int[]{200, 16, 80,  220, 600,  800,  1000, 100}));
        properties.add(new Property(game.bank, 11, PropertyColor.RED,    new int[]{220, 18, 90,  250, 700,  875,  1050, 150}));
        properties.add(new Property(game.bank, 12, PropertyColor.RED,    new int[]{220, 18, 90,  250, 700,  875,  1050, 150}));
        properties.add(new Property(game.bank, 13, PropertyColor.RED,    new int[]{240, 20, 100, 300, 750,  925,  1100, 150}));
        properties.add(new Property(game.bank, 14, PropertyColor.YELLOW, new int[]{260, 22, 110, 330, 800,  975,  1150, 150}));
        properties.add(new Property(game.bank, 15, PropertyColor.YELLOW, new int[]{260, 22, 110, 330, 800,  975,  1150, 150}));
        properties.add(new Property(game.bank, 16, PropertyColor.YELLOW, new int[]{280, 24, 120, 360, 850,  1025, 1200, 150}));
        properties.add(new Property(game.bank, 17, PropertyColor.GREEN,  new int[]{300, 26, 130, 390, 900,  1100, 1275, 200}));
        properties.add(new Property(game.bank, 18, PropertyColor.GREEN,  new int[]{300, 26, 130, 390, 900,  1100, 1275, 200}));
        properties.add(new Property(game.bank, 19, PropertyColor.GREEN,  new int[]{320, 28, 150, 450, 1000, 1200, 1400, 200}));
        properties.add(new Property(game.bank, 20, PropertyColor.BLUE,   new int[]{350, 35, 175, 500, 1100, 1300, 1500, 200}));
        properties.add(new Property(game.bank, 21, PropertyColor.BLUE,   new int[]{400, 50, 200, 600, 1400, 1700, 2000, 200}));
        properties.add(new Station(game.bank, 22, new int[]{200, 25, 50, 100, 200}));
        properties.add(new Station(game.bank, 23, new int[]{200, 25, 50, 100, 200}));
        properties.add(new Station(game.bank, 24, new int[]{200, 25, 50, 100, 200}));
        properties.add(new Station(game.bank, 25, new int[]{200, 25, 50, 100, 200}));
        properties.add(new Station(game.bank, 26, new int[]{150, 4, 10}));
        properties.add(new Station(game.bank, 27, new int[]{150, 4, 10}));
    }

    public void mortgage(int id, Player p) {
        IProperty prop = getProperty(id);

        if (prop != null) {
            prop.mortgage(game, p);
        } else {
            System.out.println("Game "+game.getId()+"property "+id+" not found.");
        }
    }

    public void unMortgage(int id, Player p) {
        IProperty prop = getProperty(id);

        if (prop != null) {
            prop.apply(game, p);
        } else {
            System.out.println("Game "+game.getId()+"property "+id+" not found.");
        }
    }

    public int[] getPropertyPrices(int id) {
        IProperty prop = getProperty(id);

        if (prop != null) {
            return prop.getPrices();
        } else {
            System.out.println("Game "+game.getId()+"property "+id+" not found.");
            return null;
        }
    }

    public void addHomes(int id, Player p, int n) {
        IProperty prop = getProperty(id);

        if (prop != null) {
            prop.buyHomes(game, p, n);
        } else {
            System.out.println("Game "+game.getId()+"property "+id+" not found.");
        }
    }

    public IProperty getProperty(int id) {
        for (IProperty p : properties) {
            if (p.getId() == id)
                return p;
        }
        return null;
    }

    public Vector<IProperty> getProperties(Player p) {
        Vector<IProperty> propertiesOwned = new Vector<IProperty>();
        for (IProperty prop : properties) {
            if (prop.getOwner() == p)
                propertiesOwned.add(prop);
        }
        return propertiesOwned;
    }

    public Vector<IProperty> getColor(PropertyColor c) {
        Vector<IProperty> p = new Vector<IProperty>();
        for (IProperty prop : properties) {
            if (prop.getColor() == c)
                p.add(prop);
        }
        return p;
    }

    public void addProperty(IProperty p) { // do not use directly
        properties.add(p);
    }

    public void removeProperty(IProperty p) { // do not use directly
        properties.remove(p);
    }

}