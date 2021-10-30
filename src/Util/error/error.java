package Util.error;
import Util.position;

abstract public class error extends RuntimeException {
    private final position pos;
    private final String message;

    public error(String msg, position pos) {
        this.pos = pos;
        this.message = msg;
    }

    public String toString() {
        return message + ": " + pos.toString();
    }
}