
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
    private Vector<Player>           players;
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
            applyRule("slot0", p);
        }
    }

    public void moveToPrison(Player p) {
        PlayerInfos info = infos.get(p);
        info.pos = 10;
        info.remainingJailTurn = 3;
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
        rules.put("slot0", new GoRule());
    }

    private void initSlots() {
        slots = new Rule[40];
        slots[0] = rules.get("slot0");   slots[1] = rules.get("slot1");   slots[2] = rules.get("slot2");
        slots[3] = rules.get("slot3");   slots[4] = rules.get("slot4");   slots[5] = rules.get("slot5");
        slots[6] = rules.get("slot6");   slots[7] = rules.get("slot7");   slots[8] = rules.get("slot8");
        slots[9] = rules.get("slot9");   slots[10] = rules.get("slot10"); slots[11] = rules.get("slot11");
        slots[12] = rules.get("slot12"); slots[13] = rules.get("slot13"); slots[14] = rules.get("slot14");
        slots[15] = rules.get("slot15"); slots[16] = rules.get("slot16"); slots[17] = rules.get("slot17");
        slots[18] = rules.get("slot18"); slots[19] = rules.get("slot19"); slots[20] = rules.get("slot20");
        slots[20] = rules.get("slot20"); slots[21] = rules.get("slot21"); slots[22] = rules.get("slot22");
        slots[23] = rules.get("slot23"); slots[24] = rules.get("slot24"); slots[25] = rules.get("slot25");
        slots[26] = rules.get("slot26"); slots[27] = rules.get("slot27"); slots[28] = rules.get("slot28");
        slots[29] = rules.get("slot29"); slots[30] = rules.get("slot30"); slots[31] = rules.get("slot31");
        slots[32] = rules.get("slot32"); slots[33] = rules.get("slot33"); slots[34] = rules.get("slot34");
        slots[35] = rules.get("slot35"); slots[36] = rules.get("slot36"); slots[37] = rules.get("slot37");
        slots[38] = rules.get("slot38"); slots[39] = rules.get("slot39");
    }

}