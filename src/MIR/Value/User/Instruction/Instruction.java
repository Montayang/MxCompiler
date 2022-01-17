package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.*;

public abstract class Instruction extends User {
    public BasicBlock theBlk;

    public Instruction(BasicBlock blk) {
        theBlk = blk;
    }

    abstract public void accept(IRVisitor visitor);
}
