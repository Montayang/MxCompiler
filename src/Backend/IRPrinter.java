package Backend;

import MIR.IRFunction;
import MIR.IRType.ArrayType;
import MIR.IRType.PointerType;
import MIR.IRType.StructType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.GlobalVar;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Objects;

public class IRPrinter implements IRVisitor {
    public PrintWriter printer;
    public String fileName;
    public boolean is_std = false;

    public IRPrinter(String path, String file) throws FileNotFoundException {
        printer = new PrintWriter(new FileOutputStream(path));
        fileName = file;
    }

    public void init(IRBuilder builder) {
        Print("; ModuleID = '" + fileName + "'");
        Print("source_filename = \"" + fileName + "\"");
        Print("""
                target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
                target triple = "x86_64-pc-linux-gnu"
                """);
        for (Map.Entry<String, StructType> entry : builder.structMap.entrySet()) {
            StructType struct = entry.getValue();
            StringBuilder out = new StringBuilder();
            out.append("%");
            out.append(struct.typeName);
            out.append(" = type {");
            for (int i = 0; i < struct.varList.size(); i++) {
                out.append(" ");
                out.append(struct.varList.get(i).toString());
                if (i != struct.varList.size() - 1) out.append(",");
            }
            out.append(" }");
            Print(out.toString());
        }
        for (Map.Entry<String, GlobalVar> entry : builder.glbVarMap.entrySet()) {
            if (entry.getValue().init != null && entry.getValue().init.type.equal("string")) continue;
            StringBuilder out = new StringBuilder("0");
            if (((PointerType) (entry.getValue().type)).dim > 1) out = new StringBuilder("null");
            Print(entry.getValue().toString() + " = dso_local global " + ((PointerType) entry.getValue().type).getObjType() + " " + out);
        }
        Print("");
        for (Map.Entry<String, GlobalVar> entry : builder.stringMap.entrySet()) {
            String cntSize = entry.getKey().replace("\\0", "1").replace("\\n", "1").replace("\\t", "1").replace("\\\"", "1").replace("\\\\", "1");
            String out = entry.getKey().replace("\\0", "\\00").replace("\\n", "\\0A").replace("\\t", "\\09").replace("\\\"", "\\22").replace("\\\\", "\\5C");
            ((ArrayType) ((PointerType) entry.getValue().type).base).len = cntSize.length();
            Print(entry.getValue().toString() + " = private unnamed_addr constant [" + cntSize.length() + " x i8] c\"" + out + "\", align 1");
        }
        Print("");
        for (Map.Entry<String, IRFunction> entry : builder.exFuncMap.entrySet()) {
            StringBuilder out = new StringBuilder();
            out.append(entry.getValue().funcName);
            out.append("(");
            for (int i = 0; i < entry.getValue().parList.size(); i++) {
                Parameter par = entry.getValue().parList.get(i);
                out.append(par.type.toString()).append(" %").append(par.parName);
                if (i != entry.getValue().parList.size() - 1) out.append(",");
            }
            out.append(")");
            Print("declare " + entry.getValue().retType.toString() + " @" + out);
        }
        Print("");
        for (Map.Entry<String, IRFunction> entry : builder.funcMap.entrySet()) {
            if (builder.exFuncMap.containsKey(entry.getKey())) continue;
            StringBuilder out = new StringBuilder();
            out.append(entry.getValue().funcName);
            out.append("(");
            for (int i = 0; i < entry.getValue().parList.size(); i++) {
                Parameter par = entry.getValue().parList.get(i);
                out.append(par.type.toString()).append(" %").append(par.parName);
                if (i != entry.getValue().parList.size() - 1) out.append(",");
            }
            out.append(")");
            Print("define dso_local " + entry.getValue().retType + " @" + out + " {");
            for (int i = 0; i < entry.getValue().blkList.size(); i++) {
                entry.getValue().blkList.get(i).accept(this);
                if (i != entry.getValue().blkList.size() - 1) Print("");
            }
            Print("}");
        }
        printer.close();
    }

    @Override
    public void visit(BinaryInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(BitCastInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(BrInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(CallInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(CmpInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(GetElementPtrInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(LoadInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(PhiInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(RetInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(StoreInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(AllocateInst it) {
        Print("    " + it.toString());
    }

    @Override
    public void visit(BasicBlock it) {
        StringBuilder out = new StringBuilder(it.blkName + ":");
        out.append(" ".repeat(60 - it.blkName.length()));
        if (!it.preBlk.isEmpty()) {
            out.append("; preds = ");
            for (BasicBlock blk : it.preBlk) out.append(blk).append(" ");
        }
        Print(out.toString());
        for (Instruction inst : it.instList) inst.accept(this);
    }

    @Override
    public void visit(IRFunction it) {
        Print("    " + it.toString());
    }

    void Print(String str) {
        if (is_std) System.out.println(str);
        else printer.println(str);
    }
}
