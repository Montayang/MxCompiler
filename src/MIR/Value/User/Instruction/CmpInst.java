package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;

public class CmpInst extends Instruction {

    public CmpInst(BasicBlock blk) {
        super(blk);
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return null;
    }
}