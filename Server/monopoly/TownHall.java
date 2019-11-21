
package monopoly;

import java.io.*;
import java.net.*;
import java.util.*;

public class TownHall {
    private int max_connection;
    private int port;
    private ArrayList<Player> players = null;

    public TownHall(int _port, int _max_connection) {
        init(_port, _max_connection);
    }

    private void init(int _port, int _max_connection) {
        port = _port;
        max_connection = _max_connection;
        if (players == null)
            players = new ArrayList<Player>(max_connection);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            
            System.out.println("Server is listening on port "+port);

            while (true) {
                // if (players.size() == max_connection) {
                //     System.out.println("server overflow.");
                //     break;
                // }
                Socket socket = serverSocket.accept();
                System.out.println("New User accepted");

                checkrunning(); // to call every loop

                try {
                    Player p = new User(socket, this);
                    if (players.size() < max_connection) {
                        players.add(p);
                    } else { // Maybe not the most optimized if many people want to connect at the same time.
                        // socket.send("501#Fail to connect: The server is full.");
                        Master.getInstance().getAPI().send(p, new ErrorState(401, "Fail to connect: The server is full."));
                        p.close();
                        p.join();
                    }
                } catch (IOException e) {
                    System.out.println("Server exception: "+e.getMessage());
                }
                dumpNbConnections();
            }
        } catch (IOException e) {
            System.out.println("Server exception: "+e.getMessage());
            e.printStackTrace();
        }
        close();
    }

    public boolean checkrunning() {
        ArrayList<Player> toRemove = new ArrayList<Player>();
        for (Player p : players) {
            if (p.isRunning() == false)
                toRemove.add(p);
        }
        for (Player p : toRemove) {
            p.join();
        }
        return toRemove.size() > 0;
    }

    public boolean checkrunning(Player p) {
        if (p.isRunning() == false) {
            p.join();
            return true;
        }
        return false;
    }

    public void remove(Player p) {
        players.remove(p);
    }

    public void close() {
        for (Player p : players) {
            p.close();
        }
        while (players.size() > 0)
            players.get(0).join();
    }

    private void dumpNbConnections() {
        System.out.println("nb players: "+players.size()+"/"+max_connection+"."); // debug print
    }

}