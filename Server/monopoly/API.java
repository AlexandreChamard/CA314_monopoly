
package monopoly;

import java.util.Vector;
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

class ParseInfos {
    public String code;
    public String core;
    public Vector<String> args = new Vector<String>();
}

class ServerAPI implements API {
    private Master server;

    public ServerAPI(Master _server) {
        server = _server;
    }

    public void parse(Player p, String msg) throws ConnectionException {
        System.out.println("receive '"+msg+"' from p");
        ParseInfos infos = null;
        try {
            infos = parseFormat(msg);

            if (infos.code == null) {
                System.out.println("DEBUG from "+p+": "+msg);
                return;
            }

            System.out.println("code: "+infos.code);
            System.out.println("core: "+infos.code);
            System.out.println("args:");
            for (String arg : infos.args)
                System.out.println("'"+arg+"'");


            getConnection(p, infos);
            getDraw(p, infos);
            getMsg(p, infos);

            throw new ErrorState(304, "invalid message.");
        } catch (ErrorState e) {
            if (infos != null) {
                send(p, infos.code, e);
            } else {
                String code = tryRetrieveCode(msg);
                if (code != null)
                    send(p, code, e);
                else
                    send(p, e);

            }
        }
    }

    protected ParseInfos parseFormat(String str) throws ErrorState {
        ParseInfos infos = new ParseInfos();

        int i = str.indexOf(':');
        if (i == -1)
            throw new ErrorState(304, "bad format.");
        infos.core = str.substring(0, i);
        str = str.substring(i+1);

        i = infos.core.indexOf('#');
        if (i != -1) {
            infos.code = infos.core.substring(0, i);
            infos.core = infos.core.substring(i+1);
        }

        String[] args = str.split("&");
        for (String arg : args) {
            infos.args.add(arg);
        }

        return infos;
    }

    protected String tryRetrieveCode(String str) {
        int i = str.indexOf('#');
        if (i == -1)
            return null;
        str = str.substring('#');
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) == false)
                return null;
        }
        return str;
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

    public void send(Player p, String id, ErrorState state) {
        p.send(state.to_string(id));
    }


    public void getConnection(Player p, ParseInfos infos) throws ErrorState, ConnectionException {
        if (!infos.core.equals("connection"))
            return;

        if (infos.args.size() != 1)
            throw new ErrorState(304, "bad connection format.");
        switch (infos.args.get(0)) {
            case "end":
                p.stopConnection();
                throw new ErrorState(200, "connection close");
            default:
                throw new ConnectionException(p, "bad connection format.");
        }
    }

    public void getDraw(Player p, ParseInfos infos) throws ErrorState {
        if (!infos.core.equals("draw"))
            return;

        if (infos.args.size() > 0)
            throw new ErrorState(304, "bad draw format.");
        drawCard(p); // throws
    }

    public void getMsg(Player p, ParseInfos infos) throws ErrorState {
        if (!infos.core.equals("msg"))
            return;

        String s = String.join("&", infos.args);
        System.out.println(s);
        throw new ErrorState(200, "message received.");
    }



    private void drawCard(Player p) throws ErrorState { // it's a test function. do not use in the final result
        Gameplate g = Master.getInstance().getCurrentGame(p);

        throw new ErrorState(304, "do not use it.");
        // Card c = g.getChanceDeck().drawCard();
        // try {
        //     c.doEffect(p);
        // } catch (ErrorState e) {
        //     g.getChanceDeck().addCard(c);
        //     throw e;
        // }
    }

}