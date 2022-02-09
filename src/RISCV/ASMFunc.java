package RISCV;

import Backend.ASMPrinter;
import MIR.IRFunction;
import MIR.Value.BasicBlock.BasicBlock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ASMFunc {
    public String funcName;
    public LinkedList<ASMBlock> blkList = new LinkedList<>();
    public ASMBlock entryBlk;
    public int regOffset;
    public Map<Register, Integer> offsetMap = new HashMap<>();

    public ASMFunc(IRFunction func) {
        regOffset = 8;
        funcName = func.funcName;
        for (BasicBlock blk : func.blkList) {
            ASMBlock asmBlk = new ASMBlock(".LBB_" + funcName + "_" + blk);
            blkList.add(asmBlk);
        }
        entryBlk = blkList.getFirst();
    }

    public void accept(ASMPrinter visitor) {
        visitor.visit(this);
    }
}
