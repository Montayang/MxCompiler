package RISCV.InstRISCV;

import RISCV.Register;

public class CallInstRISCV extends InstRISCV {
    String func;

    public CallInstRISCV(String func_) {
        super(null, null, null, null, null);
        func = func_;
    }

    @Override
    public String toString() {
        return "call\t" + func;
    }
}
