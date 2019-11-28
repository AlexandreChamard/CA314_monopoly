
package monopoly;

import java.util.ArrayList;

public class Master {
    public static final int     port = 4242;

    private static Master       master = null;
    private int                 maxGames;
    private TownHall            townHall;
    private API                 api;
    private ArrayList<Gameplate> games;

    public static Master getInstance() {
        if (master == null)
            master = new Master();
        return master;
    }

    private Master() {
        maxGames = 1; // to change. currently just debug 
        townHall = new TownHall(port, 2); // maxPlayers is currently debug. It must be something like (maxGames * 10)
        games = new ArrayList<Gameplate>(maxGames);
        api = new ServerAPI(this);
    }


    public TownHall getTownHall() { return townHall; }

    public API getAPI() { return api; }


    public void setGameMax(int n) throws ErrorState { /* only call by the API */
        maxGames = n; /* Do not destroy the overflowed games which have already be created. */
        /* Permite to reduce or increase the memory and Internet usage. */
        /* Usefull if the server is a deamon. */
        throw new ErrorState(200, "games limit has been change to "+n);
    }

    public void createGame(String name) throws ErrorState {
        if (games.size() >= maxGames)
            throw new ErrorState(502, "Cannot create the game.");

        if (getCurrentGame(name) != null)
            throw new ErrorState(503, "There is already a game named \""+name+"\".");

        games.add(new Gameplate(name));
        throw new ErrorState(200, "Game \""+name+"\" created.");
    }

    public Gameplate getCurrentGame(String name) throws ErrorState {
        /** Find a game by its name. */
        for (Gameplate game : games) {
            if (game.getName() == name)
                return game;
        }
        throw new ErrorState(303, "game "+name+" not found");
    }

    public Gameplate getCurrentGame(int id) throws ErrorState {
        /** Find a game by a player. */
        for (Gameplate game : games) {
            if (game.getId() == id)
                return game;
        }
        throw new ErrorState(505, "game "+String.valueOf(id)+" not found");
    }

    public Gameplate getCurrentGame(Player p) throws ErrorState {
        /** Find a game by a player. */
        for (Gameplate game : games) {
            if (game.isPlayer(p) == true)
                return game;
        }
        throw new ErrorState(302, "player is not in party");
    }

    public void run() {
        townHall.start();
    }

    public static void main(String[] args) {
        Master.getInstance().run();
    }

}
