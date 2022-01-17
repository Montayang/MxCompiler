package MIR.Value.BasicBlock;

import Backend.IRVisitor;
import MIR.IRfunction;
import MIR.Value.User.Instruction.Instruction;
import MIR.Value.Value;

import java.util.LinkedList;

public class BasicBlock extends Value {
    public String blkName;
    public IRfunction theFunc;
    public LinkedList<Instruction> instList = new LinkedList<>();
    public Instruction tmnInst = null;

    public BasicBlock(String name, IRfunction func) {
        blkName = name;
        theFunc = func;
    }

    public void accept(IRVisitor visitor) { visitor.visit(this); }

    public String toString() { return blkName; }

    public void addInst(Instruction inst) {
        instList.add(inst);
    }
}
