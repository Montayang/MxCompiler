package Backend;

import MIR.Value.User.Constant.GlobalVar;
import RISCV.ASMBlock;
import RISCV.ASMFunc;
import RISCV.InstRISCV.InstRISCV;
import RISCV.Register;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;

public class ASMPrinter {
    public PrintWriter printer;
    public boolean is_std = false;

    Register[] reg;

    public ASMPrinter(String path) throws FileNotFoundException {
        printer = new PrintWriter(new FileOutputStream(path));
    }

    public void init(InstSelect instSelect) {
        reg = instSelect.reg;
        Print("    .text");
        Print("");
        for (Map.Entry<String, ASMFunc> entry : instSelect.funcMapASM.entrySet()) entry.getValue().accept(this);
        Print("");
        Print("\t.section\t.sdata");
        for (Map.Entry<String, GlobalVar> entry : instSelect.glbVarMap.entrySet()) {
            if (entry.getValue().init != null && entry.getValue().init.type.equal("string")) continue;
            Print("\t.p2align\t2");
            Print("\t.globl\t" + entry.getKey());
            Print(entry.getKey() + ":");
            Print("\t.word\t0");
            Print("");
        }
        Print("");
        for (Map.Entry<String, GlobalVar> entry : instSelect.stringMap.entrySet()) {
            Print("\t.section\t.rodata\n" +
                    "\t.p2align\t2");
            Print(entry.getValue().varName + ":");
            String str =  entry.getKey().replace("\\", "\\\\").replace("\n", "\\n")
                    .replace("\0", "").replace("\t", "\\t").replace("\"", "\\\"");
            Print("\t.string\t" + "\"" + str + "\"");
            Print("");
        }
        printer.close();
    }

    public void visit(ASMBlock it) {
        Print(it.label + ":");
        for (InstRISCV inst :  it.instList) Print("    " + inst);
    }

    public void visit(ASMFunc it) {
        Print("    .globl " + it.funcName + "\t\t\t\t\t# start function : " + it.funcName);
        Print("    .p2align\t2");
        Print(it.funcName + ":");
        for (ASMBlock blk : it.blkList) blk.accept(this);
        Print("# end function : " + it.funcName);
        Print("");
    }

    void Print(String str) {
        if (is_std) System.out.println(str);
        else printer.println(str);
    }
}
