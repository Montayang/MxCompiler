package Backend;

import MIR.IRFunction;
import MIR.IRType.StructType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.ConstantValue;
import MIR.Value.User.Constant.GlobalVar;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.*;
import RISCV.ASMBlock;
import RISCV.ASMFunc;
import RISCV.InstRISCV.*;
import RISCV.Register;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class InstSelect implements IRVisitor {
    public ASMFunc curFunc;
    public ASMBlock curBlk;
    public Map<String, IRFunction> funcMap, exFuncMap;
    public Map<String, StructType> structMap;
    public Map<String, GlobalVar> glbVarMap, stringMap;
    public HashMap<String, ASMFunc> funcMapASM = new HashMap<>();
    public Register[] reg = new Register[]{new Register("zero"), new Register("ra"), new Register("sp"), new Register("gp"), new Register("tp"),
            new Register("t0"), new Register("t1"), new Register("t2"), new Register("s0"), new Register("s1"), new Register("a0"), new Register("a1"),
            new Register("a2"), new Register("a3"), new Register("a4"), new Register("a5"), new Register("a6"), new Register("a7"), new Register("s2"),
            new Register("s3"), new Register("s4"), new Register("s5"), new Register("s6"), new Register("s7"), new Register("s8"), new Register("s9"),
            new Register("s10"), new Register("s11"), new Register("t3"), new Register("t4"), new Register("t5"), new Register("t6")};
    public Map<Constant, Register> regMap = new HashMap<>();
    public Map<BasicBlock, String> labelMap = new HashMap<>();
    public ListIterator<InstRISCV> iterator;

    public InstSelect(IRBuilder builder) {
        funcMap = builder.funcMap;
        exFuncMap = builder.exFuncMap;
        stringMap = builder.stringMap;
        glbVarMap = builder.glbVarMap;
        structMap = builder.structMap;
        for (Map.Entry<String, IRFunction> entry : builder.funcMap.entrySet()) {
            if (builder.exFuncMap.containsKey(entry.getKey())) continue;
            entry.getValue().accept(this);
        }
        //Register allocate
        for (Map.Entry<String, ASMFunc> entry : funcMapASM.entrySet()) {
            curFunc = entry.getValue();
            for (ASMBlock blk : curFunc.blkList) {
                curBlk = blk;
                iterator = curBlk.instList.listIterator(0);
                while (iterator.hasNext()) {
                    InstRISCV inst = iterator.next();
                    if (inst.rs1 != null && inst.rs1.byteNum != 0) inst.rs1 = alcReg(inst.rs1, reg[28], true);
                    if (inst.rs2 != null && inst.rs2.byteNum != 0) inst.rs2 = alcReg(inst.rs2, reg[29], true);
                    if (inst.rd != null && inst.rd.byteNum != 0) inst.rd = alcReg(inst.rd, reg[30], false);
                }
            }
            int offset = curFunc.regOffset;
            ASMBlock first = curFunc.blkList.get(0), last = curFunc.blkList.size() > 1 ? curFunc.blkList.getLast() : null;
            if (offset >= -2030 && offset < 2030) {
                first.instList.addFirst(new BinaryInstRISCV("addi", reg[2], null, reg[8], offset + 12));
                first.instList.addFirst(new StoreInstRISCV("sw", reg[2], reg[8], offset + 4));
                first.instList.addFirst(new StoreInstRISCV("sw", reg[2], reg[1], offset + 8));
                first.instList.addFirst(new BinaryInstRISCV("addi", reg[2], null, reg[2], -(offset + 12)));
                if (last != null) {
                    last.addInst(new LoadInstRISCV("lw", reg[2], reg[8], offset + 4));
                    last.addInst(new LoadInstRISCV("lw", reg[2], reg[1], offset + 8));
                    last.addInst(new BinaryInstRISCV("addi", reg[2], null, reg[2], offset + 12));
                    last.addInst(new RetInstRISCV());
                }
            } else {
                first.instList.addFirst(new BinaryInstRISCV("add", reg[18], reg[2], reg[8], null));
                first.instList.addFirst(new LiInstRISCV(reg[18], offset + 12));
                first.instList.addFirst(new StoreInstRISCV("sw", reg[18], reg[8], 0));
                first.instList.addFirst(new BinaryInstRISCV("add", reg[18], reg[2], reg[18], null));
                first.instList.addFirst(new LiInstRISCV(reg[18], offset + 4));
                first.instList.addFirst(new StoreInstRISCV("sw", reg[18], reg[1], 0));
                first.instList.addFirst(new BinaryInstRISCV("add", reg[18], reg[2], reg[18], null));
                first.instList.addFirst(new LiInstRISCV(reg[18], offset + 8));
                first.instList.addFirst(new BinaryInstRISCV("add", reg[18], reg[2], reg[2], null));
                first.instList.addFirst(new LiInstRISCV(reg[18], -(offset + 12)));
                if (last != null) {
                    last.addInst(new LiInstRISCV(reg[18], offset + 4));
                    last.addInst(new BinaryInstRISCV("add", reg[18], reg[2], reg[18], null));
                    last.addInst(new LoadInstRISCV("lw", reg[18], reg[8], 0));
                    last.addInst(new LiInstRISCV(reg[18], offset + 8));
                    last.addInst(new BinaryInstRISCV("add", reg[18], reg[2], reg[18], null));
                    last.addInst(new LoadInstRISCV("lw", reg[18], reg[1], 0));
                    last.addInst(new LiInstRISCV(reg[18], offset + 12));
                    last.addInst(new BinaryInstRISCV("add", reg[18], reg[2], reg[2], null));
                    last.addInst(new RetInstRISCV());
                }
            }
        }
    }

    @Override
    public void visit(BinaryInst it) {
        Register rs1 = transReg(it.obj1), rs2 = transReg(it.obj2), rd = transReg(it.result);
        String op = it.op;
        switch (op) {
            case "sdiv" -> op = "div";
            case "srem" -> op = "rem";
            case "shl" -> op = "sll";
            case "ashr" -> op = "sra";
        }
        curBlk.addInst(new BinaryInstRISCV(op, rs1, rs2, rd, null));
    }

    @Override
    public void visit(BitCastInst it) {
        regMap.put(it.result, transReg(it.source));
    }

    @Override
    public void visit(BrInst it) {
        if (it.cond != null) curBlk.addInst(new BranchInstRISCV("beqz", transReg(it.cond), labelMap.get(it.falseBlk)));
        curBlk.addInst(new JInstRISCV(labelMap.get(it.trueBlk)));
    }

    @Override
    public void visit(CallInst it) {
        if (it.aryList != null) {
            for (int i = 0; i < Math.min(8, it.aryList.size()); i++)
                curBlk.addInst(new MoveInstRISCV(transReg(it.aryList.get(i)), reg[i + 10]));
            for (int i = 8; i < it.aryList.size(); i++) {
                String op = it.aryList.get(i).type.byteNum == 1 ? "sb" : it.aryList.get(i).type.byteNum == 2 ? "sh" : "sw";
                curBlk.addInst(new StoreInstRISCV(op, reg[2], transReg(it.aryList.get(i)), 4 * (i - 8)));
            }
        }
        curBlk.addInst(new CallInstRISCV(it.callFunc.funcName));
        if (!it.callFunc.retType.equal("void")) curBlk.addInst(new MoveInstRISCV(reg[10], transReg(it.retReg)));
    }

    @Override
    public void visit(CmpInst it) {
        Register rs1 = transReg(it.obj1), rs2 = transReg(it.obj2), rd = transReg(it.result);
        String op = it.op;
        switch (it.op) {
            case "slt", "sgt" -> curBlk.addInst(new CmpInstRISCV(op, rs1, rs2, rd));
            case "sle", "sge" -> {
                op = op.equals("sle") ? "sgt" : "slt";
                curBlk.addInst(new CmpInstRISCV(op, rs1, rs2, rd));
                curBlk.addInst(new BinaryInstRISCV("xori", rd, null, rd, 1));
            }
            case "eq", "ne" -> {
                Register Reg = new Register("sub_virtual_reg", it.obj1.type.byteNum);
                op = "s" + op + "z";
                curBlk.addInst(new BinaryInstRISCV("xor", rs1, rs2, Reg, null));
                curBlk.addInst(new CmpInstRISCV(op, Reg, null, rd));
            }
        }
    }

    @Override
    public void visit(GetElementPtrInst it) {
        if (it.sourcePtr instanceof GlobalVar) regMap.put(it.retReg, transReg(it.sourcePtr));
        else {
            Register rd = transReg(it.retReg);
            if (it.prefixByte.isEmpty()) {
                int offset = it.indexOffset.get(0).type.byteNum;
                curBlk.addInst(new LiInstRISCV(new Register("gep_byte", offset), offset));
                curBlk.addInst(new BinaryInstRISCV("mul", new Register("gep_byte", offset),
                        transReg(it.indexOffset.get(0)), new Register("change_offset", offset), null));
                curBlk.addInst(new BinaryInstRISCV("add", transReg(it.sourcePtr), new Register("change_offset", offset), rd, null));
            } else if (it.indexOffset.size() != 1) {
                int classOffset = ((ConstantValue) it.indexOffset.get(1)).intValue;
                curBlk.addInst(new BinaryInstRISCV("addi", transReg(it.sourcePtr), null, rd, it.prefixByte.get(classOffset)));
            }
        }
    }

    @Override
    public void visit(LoadInst it) {
        Register rd = new Register(it.destinationReg.toString(), it.destinationReg.type.byteNum);
        String op = rd.byteNum == 1 ? "lb" : rd.byteNum == 2 ? "lh" : "lw";
        regMap.put(it.destinationReg, rd);
        if (it.sourcePtr instanceof GlobalVar) {
            LoadInstRISCV inst = new LoadInstRISCV(op, null, transReg(it.destinationReg), null);
            inst.data = ((GlobalVar) it.sourcePtr).varName;
            curBlk.addInst(inst);
        } else {
            Register rs1 = transReg(it.sourcePtr);
            if (curFunc.offsetMap.containsKey(rs1)) {
                int offset = -curFunc.offsetMap.get(rs1);
                if (offset >= -2048 && offset < 2048) curBlk.addInst(new LoadInstRISCV(op, reg[8], rd, offset));
                else {
                    Register immReg = new Register("immreg", 4);
                    curBlk.addInst(new LiInstRISCV(immReg, offset));
                    curBlk.addInst(new BinaryInstRISCV("add", reg[8], immReg, immReg, null));
                    curBlk.addInst(new StoreInstRISCV("sw", immReg, transReg(it.destinationReg), 0));
                }
            } else curBlk.addInst(new LoadInstRISCV(op, rs1, rd, 0));
        }
    }

    @Override
    public void visit(PhiInst it) {

    }

    @Override
    public void visit(RetInst it) {
        if (it.retType.equal("void")) curBlk.addInst(new MoveInstRISCV(reg[0], reg[10]));
        else curBlk.addInst(new MoveInstRISCV(regMap.get(it.retValue), reg[10]));
    }

    @Override
    public void visit(StoreInst it) {
        String op = it.source.type.byteNum == 1 ? "sb" : it.source.type.byteNum == 2 ? "sh" : "sw";
        if (it.dest instanceof GlobalVar) {
            Register la = new Register("la", 4);
            curBlk.addInst(new LaInstRISCV(((GlobalVar) it.dest).varName, la));
            curBlk.addInst(new StoreInstRISCV("sw", la, transReg(it.source), 0));
        } else {
            Register rs1 = transReg(it.dest), rs2 = transReg(it.source);
            if (curFunc.offsetMap.containsKey(rs1)) {
                int offset = -curFunc.offsetMap.get(rs1);
                if (offset >= -2048 && offset < 2048) curBlk.addInst(new StoreInstRISCV(op, reg[8], rs2, offset));
                else {
                    Register immReg = new Register("immreg", 4);
                    curBlk.addInst(new LiInstRISCV(immReg, offset));
                    curBlk.addInst(new BinaryInstRISCV("add", reg[8], immReg, immReg, null));
                    curBlk.addInst(new StoreInstRISCV("sw", immReg, transReg(it.dest), 0));
                }
            } else curBlk.addInst(new StoreInstRISCV(op, rs1, rs2, 0));
        }
    }

    @Override
    public void visit(AllocateInst it) {
        Register Reg = new Register(it.alcReg.toString(), it.alcType.byteNum);
        regMap.put(it.alcReg, Reg);
        curFunc.regOffset += it.alcType.byteNum;
        curFunc.offsetMap.put(Reg, curFunc.regOffset);
    }

    @Override
    public void visit(BasicBlock it) {
        for (Instruction inst : it.instList) inst.accept(this);
    }

    @Override
    public void visit(IRFunction it) {
        curFunc = new ASMFunc(it);
        for (BasicBlock blk : it.blkList) labelMap.put(blk, ".LBB_" + it.funcName + "_" + blk.toString());
        funcMapASM.put(it.funcName, curFunc);
        curBlk = curFunc.entryBlk;
        for (int i = 0; i < Math.min(it.parList.size(), 8); i++)
            curBlk.addInst(new MoveInstRISCV(reg[10 + i], transReg(it.parMap.get(it.parList.get(i).parName))));
        for (int i = 8; i < it.parList.size(); i++) {
            Constant par = it.parMap.get(it.parList.get(i).parName);
            String op = par.type.byteNum == 1 ? "lb" : par.type.byteNum == 2 ? "lh" : "lw";
            curBlk.addInst(new LoadInstRISCV(op, reg[8], transReg(par), (i - 8) * 4));
        }
        for (int i = 0; i < it.blkList.size(); i++) {
            curBlk = curFunc.blkList.get(i);
            it.blkList.get(i).accept(this);
        }
    }

    public Register transReg(Constant obj) {
        if (obj instanceof ConstantValue) {
            int byteNum = obj.type.equal("bool") ? 1 : 4;
            int value = obj.type.equal("bool") ? (((ConstantValue) obj).boolValue?1:0) : ((ConstantValue) obj).intValue;
            if (value == 0) return reg[0];
            Register liReg = new Register("virtual_reg_const_li", byteNum);
            curBlk.addInst(new LiInstRISCV(liReg, value));
            return liReg;
        } else if (obj instanceof Parameter) {
            if (regMap.containsKey(obj)) return regMap.get(obj);
            Register Reg = new Register(((Parameter) obj).parName, obj.type.byteNum);
            regMap.put((Parameter) obj, Reg);
            return Reg;
        } else {
            Register Reg = new Register("tmp_str_addrreg", 4);
            curBlk.addInst(new LaInstRISCV(stringMap.get(((ConstantValue) ((GlobalVar) obj).init).stringValue).varName, Reg));
            return Reg;
        }
    }

    public Register alcReg(Register source, Register result, boolean f) {
        if (!curFunc.offsetMap.containsKey(source)) {
            curFunc.regOffset += source.byteNum;
            curFunc.offsetMap.put(source, curFunc.regOffset);
        }
        int imm = -curFunc.offsetMap.get(source);
        if (imm >= -2048 && imm < 2048) {
            if (f) {
                iterator.previous();
                iterator.add(new LoadInstRISCV(source.byteNum == 1 ? "lb" : source.byteNum == 2 ? "lh" : "lw", reg[8], result, imm));
                iterator.next();
            }
            else iterator.add(new StoreInstRISCV(source.byteNum == 1 ? "sb" : source.byteNum == 2 ? "sh" : "sw", reg[8], result, imm));
        } else {
            if (f) {
                iterator.previous();
                iterator.add(new LiInstRISCV(reg[31], imm));
                iterator.add(new BinaryInstRISCV("add", reg[8], reg[31], reg[31], null));
                iterator.add(new LoadInstRISCV(source.byteNum == 1 ? "lb" : source.byteNum == 2 ? "lh" : "lw", reg[31], result, 0));
                iterator.next();
            } else {
                iterator.add(new LiInstRISCV(reg[31], imm));
                iterator.add(new BinaryInstRISCV("add", reg[8], reg[31], reg[31], null));
                iterator.add(new StoreInstRISCV(source.byteNum == 1 ? "sb" : source.byteNum == 2 ? "sh" : "sw", reg[31], result, 0));
            }
        }
        return result;
    }
}
