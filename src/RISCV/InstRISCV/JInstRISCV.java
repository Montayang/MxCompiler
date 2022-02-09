package RISCV.InstRISCV;

import RISCV.Register;

public class JInstRISCV extends InstRISCV {
    public String blk;
    public JInstRISCV(String blk_) {
        super(null, null, null, null, null);
        blk = blk_;
    }

    @Override
    public String toString() {
        return "j\t" + blk;
    }
}
