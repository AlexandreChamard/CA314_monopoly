
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private int max_connection;
    private int port;
    private ArrayList<Player> players;
    public API api;


    public ChatServer() {
        init(4242, 2);
    }

    public ChatServer(int _port, int _max_connection) {
        init(_port, _max_connection);
    }

    private void init(int _port, int _max_connection) {
        port = _port;
        max_connection = _max_connection;
        players = new ArrayList<Player>();
        api = new ServerAPI(this);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            
            System.out.println("Server is listening on port "+port);

            while (true) {
                if (players.size() == max_connection) {
                    System.out.println("server overflow.");
                    break;
                }
                Socket socket = serverSocket.accept();
                System.out.println("New User accepted");

                players.add(new User(socket, this));

 
                // OutputStream output = socket.getOutputStream();
                // PrintWriter writer = new PrintWriter(output, true);
 
                // writer.println(new Date().toString()+'\n');


                // InputStream input = socket.getInputStream();
                // BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                // String l = reader.readLine();

                // System.out.println("receive: "+l);

                checkRuning(); // to call every loop
            }
        } catch (IOException e) {
            System.out.println("Server exception: "+e.getMessage());
            e.printStackTrace();
        }
        close();
    }

    public boolean checkRuning() {
        for (Player p : players) {
            checkRuning(p);
        }
        return players.size() > 0;
    }

    public boolean checkRuning(Player p) {
        if (p.isRuning() == false) {
            p.join();
            return false;
        }
        return true;
    }

    public void remove(Player p) {
        System.out.println("Player p is disconected.");
        players.remove(p);
    }

    public void close() {
        for (Player p : players) {
            p.close();
        }
        while (players.size() > 0)
            players.get(0).join();
    }

    public static void main(String[] args) {
        ChatServer cs = new ChatServer();

        cs.start();
    }

}