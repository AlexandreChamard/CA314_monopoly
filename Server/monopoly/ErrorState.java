
package monopoly;

/**
100 - INFOS
101 - game start (number of player)
102 - new turn (player id)
103 - end of turn
104 - player position (player id, position)
105 - transfert money (player id, amount)
106 - is bankrput (player id)
107 - is in prison (player id)
108 - is out of prison (player id)
109 - property new owner (player id, property id)
110 - property is mortgaged (property id)
111 - property is unmortgaged (property id)
112 - new number of homes in property (property id, actual num homes)
113 - chance card is drawn (card message)
113 - community card is drawn (card message)

200 - SUCESS

300 - CLIENT_ERROR
301 - player already in a game
302 - player is not in party
303 - party does not in exist
304 - bad format
305 - to few players in party


400 - ACCESS_ERROR
401 - fail to connect to the server
402 - not developed
403 - player is not the master
404 - format not recognised

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
connection: connect %s
connection: end

II get information

III do something (roll dice, exchange, buy property, un/mortgage property, send msg)

IV manage party (create, destroy, join, invite)

party: create&%s (party name)
party: destroy
party: join&%s (party name)
party: leave
party: invite&%s (player name) ??
party: start

V send an error to the server (debug)



Client <= Server



 */