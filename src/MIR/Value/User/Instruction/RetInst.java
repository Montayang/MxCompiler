package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.BaseType;
import MIR.Value.User.Constant.Constant;

import java.util.Objects;

public class RetInst extends Instruction {
    public BaseType retType;
    public Constant retValue;

    public RetInst(Constant value, BaseType type) {
        retType = type;
        retValue = value;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (Objects.equals(retType.typeName, "void")) return "ret void";
        else return "ret " + retValue.unitOut();
    }
}
