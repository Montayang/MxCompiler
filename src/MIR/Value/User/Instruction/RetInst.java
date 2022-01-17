package MIR.Value.User.Instruction;

import Backend.IRVisitor;
import MIR.IRType.BaseType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;

public class RetInst extends Instruction {
    public BaseType retType;
    public Constant retValue;

    public RetInst(BasicBlock blk, String typeName, Constant value) {
        super(blk);
        retType = new BaseType(typeName);
        retValue = value;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
