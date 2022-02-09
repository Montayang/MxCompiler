package RISCV;

import Backend.ASMPrinter;
import RISCV.InstRISCV.InstRISCV;

import java.util.LinkedList;

public class ASMBlock {
    public String label;
    public LinkedList<InstRISCV> instList = new LinkedList<>();

    public ASMBlock(String label_) {
        label = label_;
    }

    public void addInst(InstRISCV inst) {
        instList.add(inst);
    }

    public void accept(ASMPrinter visitor) {
        visitor.visit(this);
    }
}
