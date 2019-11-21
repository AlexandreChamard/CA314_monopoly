
package monopoly;

import java.util.ArrayList;

class Gameplate {
    private static final int    maxPlayers = 4;
    private ArrayList<Player>   players;
    private String              name;

    public Gameplate(String _name) {
        name = _name;
        players = new ArrayList<Player>(maxPlayers);
    }

    public String getName() {
        return name;
    }

    public boolean isPlayer(Player p) {
        return players.contains(p);
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

    public void run() {
        /** launch the game. Instanciate all the private variables and the running thread. */
    }

}