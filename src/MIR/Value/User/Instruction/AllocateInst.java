package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.BaseType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Parameter;

public class AllocateInst extends Instruction {
    public BaseType alcType;
    public Parameter alcReg;

    public AllocateInst(BaseType type, Parameter reg) {
        alcType = type;
        alcReg = reg;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return alcReg.toString() + " = alloca " + alcType.toString();
    }
}
