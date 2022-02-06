package MIR;

import Backend.IRVisitor;
import MIR.IRType.*;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.*;
import MIR.Value.Value;

import java.util.*;

public class IRFunction {
    public BaseType retType;
    public ArrayList<Parameter> parList;
    public String funcName;
    public BasicBlock entryBlk, retBlk;
    public LinkedList<BasicBlock> blkList = new LinkedList<>();
    public Parameter retReg;
    //for rename
    public Map<String, Integer> regMap = new HashMap<>();
    public Map<String, Integer> blkMap = new HashMap<>();

    public IRFunction(BaseType type, ArrayList<Parameter> list, String name) {
        retType = type;
        parList = list;
        funcName = name;
        entryBlk = new BasicBlock("entrance_block0", this);
        retBlk = new BasicBlock("return_block0", this);
        if (retType.equal("void")) {
            retBlk.addInst(new RetInst(null, new BaseType("void")));
        } else {
            retReg = new Parameter(new PointerType(retType),"return_register_infunction_addr", true);
            entryBlk.addInst(new AllocateInst(retType, retReg));
            retBlk.addInst(new LoadInst(new Parameter(retType, "returnval", true), retReg));
            retBlk.addInst(new RetInst(new Parameter(retType, "returnval", true), retType));
        }
        blkList.add(entryBlk);
    }

    public String toString() {
        return null;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public void renameAdd(Value obj) {
        if (obj instanceof Parameter && ((Parameter) obj).isReg) {
            if (regMap.containsKey(((Parameter) obj).parName)) {
                int cnt = regMap.get(((Parameter) obj).parName);
                regMap.replace(((Parameter) obj).parName, cnt + 1);
                ((Parameter) obj).parName = ((Parameter) obj).parName + "_" + cnt;
            } else regMap.put(((Parameter) obj).parName, 0);
        } else if (obj instanceof BasicBlock) {
            if (blkMap.containsKey(((BasicBlock) obj).blkName)) {
                int cnt = blkMap.get(((BasicBlock) obj).blkName);
                blkMap.replace(((BasicBlock) obj).blkName, cnt + 1);
                ((BasicBlock) obj).blkName = ((BasicBlock) obj).blkName + cnt;
            } else blkMap.put(((BasicBlock) obj).blkName, 0);
        }
    }
}
