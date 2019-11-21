
interface API {
  // input
  void parse(String msg) throws RuntimeException;

  // output
  void closeConnection();
  void send(String str);
}

class ServerAPI implements API {
  private ChatServer server;

  public ServerAPI(ChatServer _server) {
    server = _server;
  }

  public void parse(Player p, String msg) throws RuntimeException {
    if (msg.startWith("connection: ")) {

      String s = msg.substr(String("connection: ").size());
      switch (s) {
        case "end":
          p.close();
          break;
        default:
          throw RuntimeException("invalide connection block parse: \"+"+s+"\"");
      }

    } else if (msg.startWith("msg: ")) { // is message

      String s = msg.substr(String("msg: ").size());
      System.out.println(s);
      send(p, "message received.");

    } else {
      throw RuntimeException("invalide parse");
    }
  }

  public void closeConnection(Player p) {
    p.send("connection: end");
  }

  public void send(Player p, String str) {
    p.send(str);
  }

}