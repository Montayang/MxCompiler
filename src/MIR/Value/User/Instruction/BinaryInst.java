package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;

public class BinaryInst extends Instruction {
    public Constant result, obj1, obj2;
    public String op;

    public BinaryInst(Constant result_, Constant obj1_, Constant obj2_, String op_) {
        result = result_;
        obj1 = obj1_;
        obj2 = obj2_;
        op = op_;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return result.toString() + " = " + op + " " + obj1.type.toString() + " " + obj1.toString() + ", " + obj2.toString();
    }
}
