package MIR;

import MIR.IRType.*;
import MIR.Value.BasicBlock.BasicBlock;

import java.util.*;

public class IRfunction {
    public BaseType retType;
    public ArrayList<String> parList;
    public String funcName;
    public BasicBlock entryBlk, retBlk;
    public LinkedList<BasicBlock> blkList;

    public IRfunction(BaseType type, ArrayList<String> list, String name) {
        retType = type;
        parList = list;
        funcName = name;
        entryBlk = new BasicBlock("entrance_block0", this);
        retBlk = new BasicBlock("return_block0", this);
        if (Objects.equals(retType.typeName, "Void")) {
            retBlk.addInst(new ());
        } else {

        }
    }


}
