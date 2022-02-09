package RISCV.InstRISCV;

import RISCV.Register;

public abstract class InstRISCV {
    public String type;
    public Register rs1, rs2, rd;
    public Integer imm;

    public InstRISCV(String type_, Register rs1_, Register rs2_, Register rd_, Integer imm_) {
        type = type_;
        rs1 = rs1_;
        rs2 = rs2_;
        rd = rd_;
        imm = imm_;
    }

    public abstract String toString();
}
