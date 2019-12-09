
package monopoly;

import java.util.Map;
import java.util.Hashtable;
import java.util.Vector;

class PlayerInfos {
    public Player p;
    public int pos = 0;
    public int remainingJailTurn = 0; // in jail if remainingJailTurn != 0
}

class Gameplate implements Runnable {
    private static int idIt = 0;
    private int id;

    public static final int Turns_Till_Prison = 3;
    public static final int Prison_Tax = 50;
    public static final int prison = 10;
    public static final int go200Bonus = 200;

    private static final int    maxPlayers = 4;
    public Vector<Player>           players;
    public Map<Player, PlayerInfos> infos;
    private String              name;
    public boolean              running = true;

    private Rule[] slots;
    private byte consecutiveTurns;
    private int turnsPlayed;

    public Thread t = null;

    private static Map<String, Rule> rules;

    public Timer    timer;
    public Dice     dice;
    public Bank     bank;
    public Promotor promotor;
    public Deck     chanceDeck, communityDeck;

    public Gameplate(String _name) {
        id = idIt++;
        name = _name;
        players = new Vector<Player>(maxPlayers);
    }

    /** I Connection & game information */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPlayer(Player p) {
        return players.contains(p);
    }

    public void broadcast(String msg) {
        for (Player p : players)
            p.send(msg);
    }

    public void addPlayer(Player p) throws ErrorState {
        if (isPlayer(p)) {
            throw new ErrorState(301, "The player is already in the game.");
        }
        if (players.size() == maxPlayers) {
            throw new ErrorState(504, "The game you want to join is full.");
        }
        players.add(p);
    }


    /** II game loop */

    public void startGame() {
        assert players.size() > 1;
        initGamePlate();

        t = new Thread(this);
        t.start();
        System.out.println("game "+name+" started.");
        // broadcast game started at all players
    }

    public void run() { // main game loop. thread function.
        while (isEnd() == false) {
            Player currentPlayer = players.get(turnsPlayed % players.size());
            /** @notify all wich player plays */
            if (canPlay(currentPlayer) == true) {
                rules.get("turn").apply(this, currentPlayer);
                /** @notify all turn end */
            }
            ++turnsPlayed;
        }
    }

    public boolean canPlay(Player p) {
        return p.isRunning() == true && bank.bankrupt(p) == false;
    }

    public void advance(Player p, int n) {
        PlayerInfos info = infos.get(p);
        int before = info.pos;

        info.pos = computeIdx(info.pos + n);
        if (info.pos - n != before) {
            applyRule("go", p);
        }
        /** @notify new player pos */
    }

    public void setPosition(Player p, int n) {
        assert n >= 0 && n < slots.length;
        PlayerInfos info = infos.get(p);
        
        if (info.pos > n)
            applyRule("go", p);
        info.pos = n;
        /** @notify new player pos */
    }

    public void moveToPrison(Player p) {
        PlayerInfos info = infos.get(p);
        info.pos = 10;
        info.remainingJailTurn = 3;
        /** @notify new player pos */
    }

    public boolean isEnd() {
        return getWinner() != null || running == false;
    }

    public void applyRule(String str, Player p) {
        if (rules.containsKey(str) == true)
            rules.get(str).apply(this, p);
        else
            System.out.println("rule str doesn't not exist.");
    }

    public void applySlotRule(Player p) {
        slots[infos.get(p).pos].apply(this, p);
    }

    public Player getWinner() {
        Player winner = null;

        for (Player p : players) {
            if (bank.bankrupt(p) == false) {
                if (winner == null) {
                    winner = p;
                } else {
                    return null;
                }
            }
        }
        return winner;
    }

    public PlayerInfos getPlayerInfos(Player p) {
        return infos.get(p);
    }

    private int computeIdx(int i) {
        return Math.floorMod(i, 12);
    }

    /** III init */

    private void initGamePlate() {
        consecutiveTurns = 0;
        turnsPlayed = 0;

        infos = new Hashtable<Player, PlayerInfos>();
        for (Player p : players)
            infos.put(p, new PlayerInfos());
        initRules();
        timer = new Timer();
        dice = new Dice();
        bank = new Bank(this, players);
        promotor = new Promotor(this);
        communityDeck = new CommunityDeck();
        chanceDeck = new ChanceDeck();
    }

    private void initRules() {
        if (rules != null)
            return;
        rules = new Hashtable<String, Rule>();
        rules.put("turn", new TurnRule());
        rules.put("prison", new PrisonRule());
        rules.put("go", new GoRule());
        rules.put("chance", new ChanceDrawRule());
        rules.put("community", new CommunityDrawRule());
        rules.put("visiting", new VisitJailRule());
        rules.put("free_parking", new FreeParkingRule());
        rules.put("income_tax", new IncomeTaxRule());
        rules.put("super_tax", new SuperTaxRule());
        rules.put("property_00", new PropertySlotRule(0));  rules.put("property_01", new PropertySlotRule(1));
        rules.put("property_10", new PropertySlotRule(2));  rules.put("property_11", new PropertySlotRule(3));  rules.put("property_12", new PropertySlotRule(4));
        rules.put("property_20", new PropertySlotRule(5));  rules.put("property_21", new PropertySlotRule(6));  rules.put("property_22", new PropertySlotRule(7));
        rules.put("property_30", new PropertySlotRule(8));  rules.put("property_31", new PropertySlotRule(9));  rules.put("property_32", new PropertySlotRule(10));
        rules.put("property_40", new PropertySlotRule(11)); rules.put("property_41", new PropertySlotRule(12)); rules.put("property_42", new PropertySlotRule(13));
        rules.put("property_50", new PropertySlotRule(14)); rules.put("property_51", new PropertySlotRule(15)); rules.put("property_52", new PropertySlotRule(16));
        rules.put("property_60", new PropertySlotRule(17)); rules.put("property_61", new PropertySlotRule(18)); rules.put("property_62", new PropertySlotRule(19));
        rules.put("property_70", new PropertySlotRule(20)); rules.put("property_71", new PropertySlotRule(21));
        rules.put("station_0", new PropertySlotRule(22));   rules.put("station_1", new PropertySlotRule(23));
        rules.put("station_2", new PropertySlotRule(24));   rules.put("station_3", new PropertySlotRule(25));
        rules.put("electricity", new PropertySlotRule(26)); rules.put("water", new PropertySlotRule(27));
    }

    private void initSlots() {
        slots = new Rule[40];
        slots[0] = rules.get("go");           slots[1] = rules.get("property_00");  slots[2] = rules.get("community");
        slots[3] = rules.get("property_01");  slots[4] = rules.get("income_tax");   slots[5] = rules.get("station_0");
        slots[6] = rules.get("property_10");  slots[7] = rules.get("chance");       slots[8] = rules.get("property_11");
        slots[9] = rules.get("property_12");  slots[10] = rules.get("visiting");    slots[11] = rules.get("property_20");
        slots[12] = rules.get("electricity"); slots[13] = rules.get("property_21"); slots[14] = rules.get("property_22");
        slots[15] = rules.get("station_1");   slots[16] = rules.get("property_30"); slots[17] = rules.get("community");
        slots[18] = rules.get("property_31"); slots[19] = rules.get("property_32"); slots[20] = rules.get("free_parking");
        slots[21] = rules.get("property_40"); slots[22] = rules.get("chance");      slots[23] = rules.get("property_41");
        slots[24] = rules.get("property_42"); slots[25] = rules.get("station_2");   slots[26] = rules.get("property_50");
        slots[27] = rules.get("property_51"); slots[28] = rules.get("water");       slots[29] = rules.get("property_52");
        slots[30] = rules.get("go_to_jail");  slots[31] = rules.get("property_60"); slots[32] = rules.get("property_61");
        slots[33] = rules.get("community");   slots[34] = rules.get("property_62"); slots[35] = rules.get("station_3");
        slots[36] = rules.get("chance");      slots[37] = rules.get("property_70"); slots[38] = rules.get("super_tax");
        slots[39] = rules.get("property_71");
    }

}