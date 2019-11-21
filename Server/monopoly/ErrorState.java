
package monopoly;

/**
200 - SUCESS

300 - CLIENT_ERROR
301 - player already in a game


400 - ACCESS_ERROR
401 - fail to connect to the server

500 - INTERNAL_ERROR
501 - limit of players is already reached.
502 - limit of games is already reached.
503 - game already exist
504 - game full



_ - unknown error
 */

class ErrorState extends Throwable {
    public int     code;
    public String  message;

    public ErrorState(int _code, String _message) { code = _code; message = _message; }

    String to_string() {
        return code+"#"+message;
    }
}
