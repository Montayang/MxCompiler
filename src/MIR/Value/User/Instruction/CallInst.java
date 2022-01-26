package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRFunction;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Parameter;

import java.util.ArrayList;

public class CallInst extends Instruction {
    public Parameter retReg;
    public ArrayList<Parameter> aryList;
    public IRFunction callFunc;
    public CallInst(BasicBlock blk, Parameter reg, ArrayList<Parameter> list, IRFunction func) {
        super(blk);
        retReg = reg;
        aryList = list;
        callFunc = func;
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
