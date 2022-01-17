package Backend;
import MIR.*;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Instruction.*;

public interface IRVisitor {
    void visit(BinaryInst it);

    void visit(BrInst it);

    void visit(CallInst it);

    void visit(CmpInst it);

    void visit(LoadInst it);

    void visit(PhiInst it);

    void visit(RetInst it);

    void visit(StoreInst it);

    void visit(AllocateInst it);

    void visit(BasicBlock it);

    void visit(IRfunction it);
}
