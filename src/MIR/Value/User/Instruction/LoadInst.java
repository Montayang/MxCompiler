package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.Parameter;

public class LoadInst extends Instruction {
    public Constant destinationReg, sourcePtr;

    public LoadInst(BasicBlock blk, Constant reg, Constant ptr) {
        super(blk);
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
