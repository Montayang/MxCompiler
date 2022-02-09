package RISCV.InstRISCV;

import RISCV.Register;

import java.util.Objects;

public class CmpInstRISCV extends InstRISCV {
    public CmpInstRISCV(String type_, Register rs1_, Register rs2_, Register rd_) {
        super(type_, rs1_, rs2_, rd_, null);
    }

    @Override
    public String toString() {
        if (Objects.equals(type, "seqz") || Objects.equals(type, "snez")) return type + "\t" + rd +","+rs1;
        return type + "\t" + rd +","+rs1+","+rs2;
    }
}
