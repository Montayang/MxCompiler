package MIR.Value.User.Constant;

import MIR.IRType.BaseType;

public class GlobalVar extends Constant {
    public Constant init;
    public String varName;

    public GlobalVar(BaseType type1, String name) {
        super(type1);
        varName = name;
    }

    @Override
    public String toString() {
        return "@" + varName;
    }

    @Override
    public String unitOut() {
        return type.toString() + " @" + varName;
    }
}
