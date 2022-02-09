package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.PointerType;
import MIR.Value.User.Constant.Constant;

public class StoreInst extends Instruction {
    public Constant source;
    public Constant dest;

    public StoreInst(Constant source_, Constant dest_) {
        source = source_;
        dest = dest_;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        if (source.type == null || source.type.equal("void"))
            return "store " + ((PointerType)dest.type).getObjType() + " null" + ", " + dest.unitOut();
        return "store " + source.unitOut() + ", "+ dest.unitOut();
    }
}
