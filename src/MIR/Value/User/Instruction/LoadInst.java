package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.User.Constant.Constant;

public class LoadInst extends Instruction {
    public Constant destinationReg, sourcePtr;

    public LoadInst(Constant reg, Constant ptr) {
        destinationReg = reg;
        sourcePtr = ptr;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return destinationReg.toString() + " = load " + destinationReg.type.toString()
                + ", " + sourcePtr.unitOut();
    }
}
