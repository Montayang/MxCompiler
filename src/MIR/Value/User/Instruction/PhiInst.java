package MIR.Value.User.Instruction;

import Backend.IRVisitor;

public class PhiInst extends Instruction {

    public PhiInst() {

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
