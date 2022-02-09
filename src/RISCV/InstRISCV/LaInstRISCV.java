package RISCV.InstRISCV;

import RISCV.Register;

public class LaInstRISCV extends InstRISCV {
    public String addr;
    public LaInstRISCV(String addr_, Register rd_) {
        super(null, null, null, rd_, null);
        addr = addr_;
    }

    @Override
    public String toString() {
        return "la\t" + rd + "," + addr;
    }
}
