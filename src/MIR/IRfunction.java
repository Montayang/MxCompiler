package MIR;

import MIR.IRType.*;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Instruction.*;

import java.util.*;

public class IRFunction {
    public BaseType retType;
    public ArrayList<String> parList;
    public String funcName;
    public BasicBlock entryBlk, retBlk;
    public LinkedList<BasicBlock> blkList;

    public IRFunction(BaseType type, ArrayList<String> list, String name) {
        retType = type;
        parList = list;
        funcName = name;
        entryBlk = new BasicBlock("entrance_block0", this);
        retBlk = new BasicBlock("return_block0", this);
        if (Objects.equals(retType.typeName, "void")) {
            retBlk.addInst(new RetInst(retBlk, "null", null));
        } else {

        }
    }


}
