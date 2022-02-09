package RISCV.InstRISCV;

import RISCV.Register;

public class RetInstRISCV extends InstRISCV {
    public RetInstRISCV() {
        super(null, null,null,null,null);
    }

    @Override
    public String toString() {
        return "ret";
    }
}
