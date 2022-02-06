package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;

public class BrInst extends Instruction {
    public Constant cond;
    public BasicBlock trueBlk, falseBlk;

    public BrInst(Constant cond_, BasicBlock trueBlk_, BasicBlock falseBlk_) {
        cond = cond_;
        trueBlk = trueBlk_;
        falseBlk = falseBlk_;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (cond==null) return "br label %" + trueBlk.toString();
        else return "br " + cond.unitOut() + ", label %" + trueBlk.toString() + ", label %" + falseBlk.toString();
    }
}
