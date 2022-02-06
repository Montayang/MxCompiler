package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRFunction;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.Parameter;

import java.util.ArrayList;

public class CallInst extends Instruction {
    public Parameter retReg;
    public ArrayList<Constant> aryList;
    public IRFunction callFunc;

    public CallInst(Parameter reg, ArrayList<Constant> list, IRFunction func) {
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
        StringBuilder out = new StringBuilder();
        if (retReg != null) out.append(retReg).append(" = ");
        out.append("call ").append(callFunc.retType.toString()).append(" @").append(callFunc.funcName).append("(");
        if (aryList != null)
            for (int i = 0; i < aryList.size(); i++) {
                out.append(callFunc.parList.get(i).type).append(" ").append(aryList.get(i).toString());
                if (i != aryList.size() - 1) out.append(", ");
            }
        out.append(")");
        return out.toString();
    }
}
