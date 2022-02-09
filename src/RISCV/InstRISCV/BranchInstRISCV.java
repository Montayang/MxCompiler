package RISCV.InstRISCV;

import RISCV.Register;

public class BranchInstRISCV extends InstRISCV {
    String dest;

    public BranchInstRISCV(String type_, Register rs1_, String dest_) {
        super(type_, rs1_, null, null, null);
        dest = dest_;
    }

    @Override
    public String toString() {
        return type + "\t" + rs1 + ",\t" + dest;
    }
}
