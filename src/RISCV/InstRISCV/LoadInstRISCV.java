package RISCV.InstRISCV;

import RISCV.Register;

public class LoadInstRISCV extends InstRISCV {
    public String data = null;

    public LoadInstRISCV(String type_, Register rs1_, Register rd_, Integer imm_) {
        super(type_, rs1_, null, rd_, imm_);
    }

    @Override
    public String toString() {
        if (!(data == null)) return type + "\t" + rd + "," + data;
        return type + "\t" + rd + "," + imm + "(" + rs1 + ")";
    }
}
