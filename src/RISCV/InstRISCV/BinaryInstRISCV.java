package RISCV.InstRISCV;

import RISCV.Register;

public class BinaryInstRISCV extends InstRISCV {
    public BinaryInstRISCV(String type_, Register rs1_, Register rs2_, Register rd_, Integer imm_) {
        super(type_, rs1_, rs2_, rd_, imm_);
    }

    @Override
    public String toString() {
        if (imm == null) return type + "\t" + rd + "," + rs1 + "," + rs2;
        return type + "\t" + rd + "," + rs1 + "," + imm;
    }
}
