package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.PointerType;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.Parameter;

import java.util.ArrayList;

public class GetElementPtrInst extends Instruction {
    public Parameter retReg;
    public Constant sourcePtr;
    public ArrayList<Constant> indexOffset;
    public ArrayList<Integer> prefixByte = new ArrayList<>();

    public GetElementPtrInst(Parameter reg, Constant ptr, ArrayList<Constant> list) {
        retReg = reg;
        sourcePtr = ptr;
        indexOffset = list;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder(retReg.toString() + " = getelementptr inbounds " +
                ((PointerType) sourcePtr.type).getObjType() + ", " + sourcePtr.unitOut() + ", ");
        for (int i = 0; i < indexOffset.size(); i++) {
            out.append(indexOffset.get(i).unitOut());
            if (i != indexOffset.size() - 1) out.append(", ");
        }
        return out.toString();
    }
}
