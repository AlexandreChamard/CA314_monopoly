
package monopoly;

import java.lang.Throwable;

class ConnectionException extends Throwable {
    public Player p;
    public String message;

    public ConnectionException(Player _p, String _m) { p = _p; message = _m; }
}

interface API {
    // input
    void parse(Player p, String msg) throws ConnectionException;

    // output
    void closeConnection(Player p);
    void send(Player p, ErrorState state);
}

class ServerAPI implements API {
    private Master server;

    public ServerAPI(Master _server) {
        server = _server;
    }

    public void parse(Player p, String msg) throws ConnectionException {
        try {
            if (msg.startsWith("connection: ")) {

                String s = msg.substring("connection: ".length());
                switch (s) {
                    case "end":
                        p.stopConnection();
                        break;
                    default:
                        throw new ConnectionException(p, "invalide connection block parse: \""+s+"\"");
                }

            } else if (msg.startsWith("msg: ")) { // is message

                String s = msg.substring("msg: ".length());
                System.out.println(s);
                send(p, new ErrorState(200, "message received."));
            } else if (msg.equals("draw")) {
                drawCard(p);
            } else {
                throw new ConnectionException(p, "invalide parse");
            }
        } catch (ErrorState e) {
            send(p, e);
        }
    }

    public void closeConnection(Player p) {
        if (p.isRunning()) {
            p.close();
        }
        send(p, new ErrorState(401, "end of connection."));
    }

    public void send(Player p, ErrorState state) {
        p.send(state.to_string());
    }

    public void drawCard(Player p) throws ErrorState {
        Gameplate g = Master.getInstance().getCurrentGame(p);

        Card c = g.getChanceDeck() .drawCard();
        try {
            c.doEffect(p);
        } catch (ErrorState e) {
            g.getChanceDeck().addCard(c);
            throw e;
        }
    }

}