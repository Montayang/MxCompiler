package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.Parameter;

public class CmpInst extends Instruction {
    public Parameter result;
    public String op;
    public Constant obj1, obj2;
    public CmpInst(Parameter result_, String op_, Constant obj1_, Constant obj2_) {
        result = result_;
        op = op_;
        obj1 = obj1_;
        obj2 = obj2_;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return result + " = icmp " + op + " " + obj1.type + " " + obj1 + ", " + obj2;
    }
}
