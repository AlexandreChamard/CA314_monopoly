
package monopoly;

import java.io.*;
import java.net.*;

interface Player {
    void    send(String msg);
    boolean isRunning();
    void    stopConnection();
    void    close();
    void    join();
}

class User implements Player, Runnable {
    Socket s;
    TownHall ref;
    volatile boolean running = true;
    PrintWriter writer;
    BufferedReader reader;
    Thread t;

    User(Socket _s, TownHall _ref) throws IOException {
        s = _s;
        ref = _ref;
        writer = new PrintWriter(s.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        t = new Thread(this);
        t.start();
        System.out.println("User created");
    }

    public boolean isRunning() {
        return running;
    }

    public void stopConnection() {
        running = false;
    }

    public void close() {
        stopConnection();
        Master.getInstance().getAPI().closeConnection(this);
    }

    public void join() {
        try {
            t.join();
        } catch (InterruptedException e) {}
        ref.remove(this);
    }

    public void run() { // thread function
        System.out.println("Thread is running...");
        try {
            while (isRunning()) {
                String l = reader.readLine();
                if (l == null) {
                    // possibly error connection or RQ
                    System.out.println("unexpected null received. End of Connection");
                    break;
                }
                // System.out.println(l);
                Master.getInstance().getAPI().parse(this, l);
            }
        } catch (IOException e) {
            System.out.println("unexpected error: "+e.getMessage());
        }
        close();
    }

    public void send(String msg) {
        writer.println(msg);
    }
}