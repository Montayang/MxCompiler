package MIR.Value.User.Constant;

import MIR.IRType.BaseType;

public class Parameter extends Constant{
    public String parName;
    public boolean isReg = false;

    public Parameter(BaseType type1, String name, boolean flag) {
        super(type1);
        parName = name;
        isReg = flag;
    }

    public Parameter(BaseType type1, String name) {
        super(type1);
        parName = name;
    }

    @Override
    public String toString() {
        if (isReg) return "%" + parName;
        else return parName;
    }

    @Override
    public String unitOut() {
        return type.toString() + " %" + parName;
    }
}
