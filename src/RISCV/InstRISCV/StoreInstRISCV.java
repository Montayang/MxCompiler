package RISCV.InstRISCV;

import RISCV.Register;

public class StoreInstRISCV extends InstRISCV {
    String storeName = null;

    public StoreInstRISCV(String type_, Register rs1_, Register rs2_, Integer imm_) {
        super(type_, rs1_, rs2_, null, imm_);
    }

    @Override
    public String toString() {
        if (storeName != null) return type + "\t" + rs1 + "," + storeName + "," + rs2;
        return type + "\t" + rs2 + "," + imm + "(" + rs1 + ")";
    }
}
