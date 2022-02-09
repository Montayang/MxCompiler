package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.BaseType;
import MIR.Value.User.Constant.Parameter;

public class BitCastInst extends Instruction {
    public Parameter result;
    public Parameter source;
    BaseType type;

    public BitCastInst(Parameter result_, Parameter source_, BaseType type_) {
        result = result_;
        source = source_;
        type = type_;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return result + " = bitcast " + source.unitOut() + " to " + type;
    }
}
