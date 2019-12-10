
package monopoly;

import java.util.Vector;
import java.lang.Throwable;

interface API {
    // input
    void parse(Player p, String msg);

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

    public void parse(Player p, String msg) {
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


    public void getConnection(Player p, ParseInfos infos) throws ErrorState {
        if (!infos.core.equals("connection"))
            return;

        if (infos.args.size() != 1)
            throw new ErrorState(304, "bad connection format.");
        switch (infos.args.get(0)) {
            case "end":
                p.stopConnection();
                throw new ErrorState(200, "connection close");
            default:
                throw new ErrorState(404, "bad connection format.");
        }
    }

    public void getParty(Player p, ParseInfos infos) throws ErrorState {
        if (!infos.core.equals("party"))
            return;

        if (infos.args.size() == 0)
            throw new ErrorState(304, "bad party format.");
        switch (infos.args.get(0)) {
            case "create": {
                if (infos.args.size() != 2)
                    throw new ErrorState(304, "bad party create format.");
                server.createGame(infos.args.get(1));
                try {
                    server.getCurrentGame(infos.args.get(1)).addPlayer(p);
                } catch (ErrorState e) {
                    server.deleteGame(infos.args.get(1));
                    throw e;
                }
                throw new ErrorState(200, "game successfuly created.");
            }

            case "destroy": {
                if (infos.args.size() != 1)
                    throw new ErrorState(304, "bad party destroy format.");
                Gameplate game = server.getCurrentGame(p);
                if (game.isMasterPlayer(p) == false)
                    throw new ErrorState(403, "fail to destroy game.");
                server.deleteGame(game);
                throw new ErrorState(200, "game successfuly destroyed.");
            }

            case "join": {
                if (infos.args.size() != 2)
                    throw new ErrorState(304, "bad party join format.");
                server.getCurrentGame(infos.args.get(1)).addPlayer(p);
                throw new ErrorState(200, "player join game "+infos.args.get(1));
            }

            case "leave": {
                if (infos.args.size() != 1)
                    throw new ErrorState(304, "bad party leave format.");
                server.getCurrentGame(p).removePlayer(p);
                throw new ErrorState(200, "leave game successful.");
            }

            case "invite": {
                throw new ErrorState(402, "");
            }

            case "start": {
                if (infos.args.size() != 1)
                    throw new ErrorState(304, "bad party start format.");
                Gameplate game = server.getCurrentGame(p);
                if (game.isMasterPlayer(p) == false)
                    throw new ErrorState(403, "fail to start game.");
                game.startGame();
                throw new ErrorState(200, "game started");
            }

            default:
                throw new ErrorState(404, "bad party format.");
        }
    }

    public void getMsg(Player p, ParseInfos infos) throws ErrorState {
        if (!infos.core.equals("msg"))
            return;

        String s = String.join("&", infos.args);
        System.out.println(s);
        throw new ErrorState(200, "message received.");
    }

}