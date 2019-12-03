
package monopoly;

/**
100 - INFOS

200 - SUCESS

300 - CLIENT_ERROR
301 - player already in a game
302 - player is not in party
303 - party does not in exist
304 - bad format


400 - ACCESS_ERROR
401 - fail to connect to the server

500 - INTERNAL_ERROR
501 - limit of players is already reached.
502 - limit of games is already reached.
503 - game already exist
504 - game full
505 - game not found



_ - unknown error
 */

class ErrorState extends Throwable {
    public int     code;
    public String  message;

    public ErrorState(int _code, String _message) { code = _code; message = _message; }

    String to_string() {
        return code+"#"+message;
    }
    String to_string(String id) {
        return code+"@"+id+"#"+message;
    }
}


/**
>: Client => Server
<: Client <= Server

> connect to account (name / password??)
< success #2 (unique_id)
< failure #4


Client => Server

I Connection (end, get name)
connection: connect %s%%%s
connection: end

II get information

III do something (roll dice, exchange, draw card, buy property, un/mortgage property, send msg)

IV manage party (create, destroy, join, invite)

V send an error to the server (debug)



Client <= Server



 */