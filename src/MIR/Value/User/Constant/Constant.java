package MIR.Value.User.Constant;

import MIR.IRType.BaseType;
import MIR.Value.User.*;

abstract public class Constant extends User{
    public BaseType type;

    public Constant(BaseType type1) {
        type = type1;
    }

    abstract public String toString();

    abstract public String unitOut();
}
