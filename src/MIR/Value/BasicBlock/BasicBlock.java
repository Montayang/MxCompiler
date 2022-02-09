package MIR.Value.BasicBlock;

import Backend.IRVisitor;
import MIR.IRFunction;
import MIR.Value.User.Instruction.BrInst;
import MIR.Value.User.Instruction.Instruction;
import MIR.Value.Value;

import java.util.HashSet;
import java.util.LinkedList;

public class BasicBlock extends Value {
    public String blkName;
    public IRFunction theFunc;
    public LinkedList<Instruction> instList = new LinkedList<>();
    public HashSet<BasicBlock> preBlk = new HashSet<>(), nxtBlk = new HashSet<>();

    public BasicBlock(String name, IRFunction func) {
        blkName = name;
        theFunc = func;
    }

    public void accept(IRVisitor visitor) { visitor.visit(this); }

    public String toString() { return blkName; }

    public void addInst(Instruction inst) {
        instList.add(inst);
        inst.theBlk = this;
        if (inst instanceof BrInst) {
            if (((BrInst) inst).cond != null) {
                inst.theBlk.nxtBlk.add(((BrInst) inst).falseBlk);
                ((BrInst) inst).falseBlk.preBlk.add(inst.theBlk);
            }
            inst.theBlk.nxtBlk.add(((BrInst) inst).trueBlk);
            ((BrInst) inst).trueBlk.preBlk.add(inst.theBlk);
        }
    }
}
