package RISCV.InstRISCV;

import RISCV.Register;

public class LiInstRISCV extends InstRISCV {
    public LiInstRISCV(Register rd_, Integer imm_) {
        super(null, null, null, rd_, imm_);
    }

    @Override
    public String toString() {
        return "li\t" + rd + "," + imm;
    }
}
