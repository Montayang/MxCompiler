package MIR;

import Backend.IRVisitor;
import MIR.IRType.*;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.*;

import java.util.*;

public class IRFunction {
    public BaseType retType;
    public ArrayList<Parameter> parList;
    public String funcName;
    public BasicBlock entryBlk, retBlk;
    public LinkedList<BasicBlock> blkList = new LinkedList<>();
    public Parameter retReg;

    public IRFunction(BaseType type, ArrayList<Parameter> list, String name) {
        retType = type;
        parList = list;
        funcName = name;
        entryBlk = new BasicBlock("entrance_block0", this);
        retBlk = new BasicBlock("return_block0", this);
        if (retType.equal("void")) {
            retBlk.addInst(new RetInst(retBlk, null, new BaseType("void")));
        } else {
            retReg = new Parameter(new PointerType(retType),"return_register_infunction_addr", true);
            entryBlk.addInst(new AllocateInst(entryBlk, retType, retReg));
            retBlk.addInst(new LoadInst(retBlk, new Parameter(retType, "returnval", true), retReg));
            retBlk.addInst(new RetInst(retBlk, new Parameter(retType, "returnval", true), retType));
        }
        blkList.add(entryBlk);
        blkList.add(retBlk);
    }

    public String toString() {
        return null;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
