package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.*;

public abstract class Instruction extends User {
    public BasicBlock theBlk;

    abstract public void accept(IRVisitor visitor);

    abstract public String toString();
}
