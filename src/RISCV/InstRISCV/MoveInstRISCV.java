package RISCV.InstRISCV;

import RISCV.Register;

public class MoveInstRISCV extends InstRISCV {
    public MoveInstRISCV(Register rs1_, Register rd_) {
        super(null, rs1_, null, rd_, null);
    }

    @Override
    public String toString() {
        return "mv\t" + rd + "," + rs1;
    }
}
