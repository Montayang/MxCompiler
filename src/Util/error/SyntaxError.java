package Util.error;
import Util.position;

public class syntaxError extends error {

    public syntaxError(String msg, position pos) {
        super("SyntaxError: " + msg, pos);
    }

    @Override
    public String getMessage(){
        return message + " position " +
                pos.toString();
    }

}