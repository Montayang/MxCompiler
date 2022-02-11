package Backend;

import AST.*;
import MIR.*;
import MIR.IRType.ArrayType;
import MIR.IRType.BaseType;
import MIR.IRType.PointerType;
import MIR.IRType.StructType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.ConstantValue;
import MIR.Value.User.Constant.GlobalVar;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.*;
import Util.*;

import java.util.*;

public class IRBuilder implements ASTVisitor {
    public globalScope semantic_scope;
    public IRFunction curFunc;
    public BasicBlock curBlock;
    public ClassDefNode curClass;
    public IRScope curScope;
    public Map<String, IRFunction> funcMap = new HashMap<>(), exFuncMap = new HashMap<>();
    public Map<String, StructType> structMap = new HashMap<>();
    public Map<String, GlobalVar> glbVarMap = new HashMap<>(), stringMap = new HashMap<>();
    public Stack<BasicBlock> breakStack = new Stack<>(), continueStack = new Stack<>();
    public Map<String, ArrayList<Integer>> prefixByteMap = new HashMap<>();

    public IRBuilder(globalScope scope1) {
        semantic_scope = scope1;
        curScope = new IRScope(null);
        ArrayList<Parameter> par;
        //void print(string str);
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str_0"));
        funcMap.put("print", new IRFunction(new BaseType("void"), par, "print"));
        exFuncMap.put("print", new IRFunction(new BaseType("void"), par, "print"));
        //void println(string str);
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str_0"));
        funcMap.put("println", new IRFunction(new BaseType("void"), par, "println"));
        exFuncMap.put("println", new IRFunction(new BaseType("void"), par, "println"));
        //void printlnInt(int n);
        par = new ArrayList<>();
        par.add(new Parameter(transType("int"), "n_0"));
        funcMap.put("printlnInt", new IRFunction(new BaseType("void"), par, "printlnInt"));
        exFuncMap.put("printlnInt", new IRFunction(new BaseType("void"), par, "printlnInt"));
        //void printInt(int n);
        par = new ArrayList<>();
        par.add(new Parameter(transType("int"), "n_0"));
        funcMap.put("printInt", new IRFunction(new BaseType("void"), par, "printInt"));
        exFuncMap.put("printInt", new IRFunction(new BaseType("void"), par, "printInt"));
        //string getString()
        par = new ArrayList<>();
        funcMap.put("getString", new IRFunction(transType("string"), par, "getString"));
        exFuncMap.put("getString", new IRFunction(transType("string"), par, "getString"));
        //int getInt()
        par = new ArrayList<>();
        funcMap.put("getInt", new IRFunction(transType("int"), par, "getInt"));
        exFuncMap.put("getInt", new IRFunction(transType("int"), par, "getInt"));
        //string toString(int i)
        par = new ArrayList<>();
        par.add(new Parameter(transType("int"), "i_0"));
        funcMap.put("toString", new IRFunction(transType("string"), par, "toString"));
        exFuncMap.put("toString", new IRFunction(transType("string"), par, "toString"));
        //int _str_ord(const char* str,int pos)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str"));
        par.add(new Parameter(transType("int"), "pos"));
        funcMap.put("_str_ord", new IRFunction(transType("int"), par, "_str_ord"));
        exFuncMap.put("_str_ord", new IRFunction(transType("int"), par, "_str_ord"));
        //bool _str_eq(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_eq", new IRFunction(transType("bool"), par, "_str_eq"));
        exFuncMap.put("_str_eq", new IRFunction(transType("bool"), par, "_str_eq"));
        //bool _str_ne(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_ne", new IRFunction(transType("bool"), par, "_str_ne"));
        exFuncMap.put("_str_ne", new IRFunction(transType("bool"), par, "_str_ne"));
        //bool _str_lt(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_lt", new IRFunction(transType("bool"), par, "_str_lt"));
        exFuncMap.put("_str_lt", new IRFunction(transType("bool"), par, "_str_lt"));
        //bool _str_le(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_le", new IRFunction(transType("bool"), par, "_str_le"));
        exFuncMap.put("_str_le", new IRFunction(transType("bool"), par, "_str_le"));
        //bool _str_gt(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_gt", new IRFunction(transType("bool"), par, "_str_gt"));
        exFuncMap.put("_str_gt", new IRFunction(transType("bool"), par, "_str_gt"));
        //bool _str_ge(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_ge", new IRFunction(transType("bool"), par, "_str_ge"));
        exFuncMap.put("_str_ge", new IRFunction(transType("bool"), par, "_str_ge"));
        //const char* _str_concatenate(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "lhs"));
        par.add(new Parameter(transType("string"), "rhs"));
        funcMap.put("_str_concatenate", new IRFunction(transType("string"), par, "_str_concatenate"));
        exFuncMap.put("_str_concatenate", new IRFunction(transType("string"), par, "_str_concatenate"));
        //char * _f_malloc(int n)
        par = new ArrayList<>();
        par.add(new Parameter(transType("int"), "n"));
        funcMap.put("_f_malloc", new IRFunction(transType("string"), par, "_f_malloc"));
        exFuncMap.put("_f_malloc", new IRFunction(transType("string"), par, "_f_malloc"));
        //int _str_length(const char* str)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str"));
        funcMap.put("_str_length", new IRFunction(transType("int"), par, "_str_length"));
        exFuncMap.put("_str_length", new IRFunction(transType("int"), par, "_str_length"));
        //const char* _str_substring(const char* str,int left,int right)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str"));
        par.add(new Parameter(transType("int"), "left"));
        par.add(new Parameter(transType("int"), "right"));
        funcMap.put("_str_substring", new IRFunction(transType("string"), par, "_str_substring"));
        exFuncMap.put("_str_substring", new IRFunction(transType("string"), par, "_str_substring"));
        //int _str_parseInt(const char* str)
        par = new ArrayList<>();
        par.add(new Parameter(transType("string"), "str"));
        funcMap.put("_str_parseInt", new IRFunction(transType("int"), par, "_str_parseInt"));
        exFuncMap.put("_str_parseInt", new IRFunction(transType("int"), par, "_str_parseInt"));
    }

    @Override
    public void visit(RootNode rootNode) {
        for (ASTNode node : rootNode.elements) {
            if (node instanceof ClassDefNode) {
                StructType structType = new StructType("class." + ((ClassDefNode) node).className, new ArrayList<>());
                structMap.put(((ClassDefNode) node).className, structType);
            }
        }
        //class
        for (ASTNode node : rootNode.elements) {
            if (node instanceof ClassDefNode) {
                StructType structType = structMap.get(((ClassDefNode) node).className);
                int cnt = 0;
                for (VardefStmtNode varDefStmt : ((ClassDefNode) node).varMem) {
                    for (VarDefNode varDef : varDefStmt.elements) {
                        structType.addVar(transType(varDef.varType));
                        if (cnt == 0) structType.byteList.add(0);
                        else structType.byteList.add(structType.byteList.get(cnt - 1) + structType.varList.get(cnt).byteNum);
                        cnt++;
                    }
                }
                prefixByteMap.put(((ClassDefNode) node).className, structType.byteList);
                for (FuncDefNode funcDef : ((ClassDefNode) node).funcMem) {
                    ArrayList<Parameter> parList = new ArrayList<>();
                    parList.add(new Parameter(new PointerType(structType), "this"));
                    if (funcDef.parList != null)
                        for (VarDefNode varDef : funcDef.parList) {
                            parList.add(new Parameter(transType(varDef.varType), varDef.varName + "_para"));
                        }
                    String name = ((ClassDefNode) node).className + "." + funcDef.funcName;
                    BaseType type = funcDef.funcType == null ? new BaseType("void") : transType(funcDef.funcType);
                    funcMap.put(name, new IRFunction(type, parList, name));
                }
            }
        }//global function
        for (ASTNode node : rootNode.elements) {
            if (node instanceof FuncDefNode) {
                ArrayList<Parameter> parList = new ArrayList<>();
                if (((FuncDefNode) node).parList != null) {
                    for (VarDefNode par : ((FuncDefNode) node).parList) {
                        parList.add(new Parameter(transType(par.varType), par.varName + "_para"));
                    }
                }
                IRFunction func = new IRFunction(transType(((FuncDefNode)node).funcType), parList, ((FuncDefNode) node).funcName);
                funcMap.put(((FuncDefNode) node).funcName, func);
            }
        }
        IRFunction func;
        func = new IRFunction(new BaseType("void"), new ArrayList<>(), "GLOBAL__sub_I_main_mx");
        funcMap.put("GLOBAL__sub_I_main_mx", func);
        //global var
        curFunc = func;
        curBlock = func.entryBlk;
        for (ASTNode node : rootNode.elements) {
            if (node instanceof VardefStmtNode) {
                node.accept(this);
            }
        }
        curBlock.addInst(new BrInst(null, curFunc.retBlk, null));
        curFunc.blkList.add(curFunc.retBlk);

        //visit class and func
        curFunc = null;
        curBlock = null;
        curClass = null;
        for (ASTNode node : rootNode.elements) {
            if (node instanceof ClassDefNode || node instanceof FuncDefNode) node.accept(this);
        }
    }

    @Override
    public void visit(ClassDefNode classDefNode) {
        curClass = classDefNode;
        curScope = new IRScope(curScope);
        for (FuncDefNode func : classDefNode.funcMem) func.accept(this);
        curScope = curScope.parScope;
        curClass = null;
    }

    @Override
    public void visit(FuncDefNode funcDefNode) {
        curScope = new IRScope(curScope);
        curFunc = curClass == null ? funcMap.get(funcDefNode.funcName) : funcMap.get(curClass.className + "." + funcDefNode.funcName);
        curBlock = curFunc.entryBlk;
        if (funcDefNode.parList != null)
            for (VarDefNode par : funcDefNode.parList) {
                Parameter reg = Register(transType(par.varType), par.varName + "_para");
                curScope.addID(par.varName + "_para", reg);
                curFunc.parMap.put(par.varName + "_para", reg);
                par.accept(this);
                curBlock.addInst(new StoreInst(curScope.get(par.varName + "_para"), curScope.get(par.varName)));
            }
        if (curClass != null) {
            //add this
            Parameter thisAddr = Register(new PointerType(transType(curClass.className)), "this_addr");
            if (curBlock.instList.isEmpty() || !(curBlock.instList.getLast() instanceof BrInst))
                curBlock.instList.addFirst(new AllocateInst(((PointerType)thisAddr.type).getObjType(), thisAddr));
            curScope.addID("this_addr", thisAddr);
            Parameter thisPara = Register(curFunc.parList.get(0).type, "this");
            curBlock.addInst(new StoreInst(thisPara, thisAddr));
            curFunc.parMap.put("this", thisPara);
        }
        if (funcDefNode.funcBody != null) funcDefNode.funcBody.accept(this);
        if (curBlock.instList.isEmpty() || !(curBlock.instList.getLast() instanceof BrInst))
            curBlock.addInst(new BrInst(null, curFunc.retBlk, null));
        curFunc.blkList.add(curFunc.retBlk);
        if (Objects.equals(curFunc.funcName, "main"))
            curFunc.entryBlk.instList.addFirst(new CallInst(null, null, funcMap.get("GLOBAL__sub_I_main_mx")));
        curScope = curScope.parScope;
    }

    @Override
    public void visit(VarDefNode varDefNode) {
        if (varDefNode.initValue != null) varDefNode.initValue.accept(this);
        Constant var;
        if (curScope.parScope == null) {
            var = new GlobalVar(new PointerType(transType(varDefNode.varType)), varDefNode.varName);
            glbVarMap.put(varDefNode.varName, (GlobalVar) var);
            curFunc.renameAdd(var);
        } else {
            var = Register(new PointerType(transType(varDefNode.varType)), varDefNode.varName + "_addr");
            if (curBlock.instList.isEmpty() || !(curBlock.instList.getLast() instanceof BrInst))
                curFunc.entryBlk.instList.addFirst(new AllocateInst(transType(varDefNode.varType), (Parameter) var));
        }
        curScope.addID(varDefNode.varName, var);
        if (varDefNode.initValue != null) curBlock.addInst(new StoreInst(varDefNode.initValue.irPar, var));
    }

    @Override
    public void visit(BlockStmtNode blockStmtNode) {
        curScope = new IRScope(curScope);
        for (StmtNode node : blockStmtNode.stmtList) node.accept(this);
        curScope = curScope.parScope;
    }

    @Override
    public void visit(VardefStmtNode vardefStmtNode) {
        for (VarDefNode node : vardefStmtNode.elements) node.accept(this);
    }

    @Override
    public void visit(IfStmtNode ifStmtNode) {
        ifStmtNode.ifExpr.accept(this);
        curScope = new IRScope(curScope);
        if (ifStmtNode.elseStmt == null) {
            BasicBlock thenBlk = Block("single_then_basicblock");
            BasicBlock endBlk = Block("if_withoutelse_end_basicblock");
            curBlock.nxtBlk.add(thenBlk); curBlock.nxtBlk.add(endBlk);
            thenBlk.preBlk.add(curBlock); thenBlk.nxtBlk.add(endBlk);
            endBlk.preBlk.add(thenBlk); endBlk.preBlk.add(curBlock);
            curBlock.addInst(new BrInst(ifStmtNode.ifExpr.irPar, thenBlk, endBlk));
            curBlock = thenBlk;
            if (ifStmtNode.thenStmt != null) ifStmtNode.thenStmt.accept(this);
            curBlock.addInst(new BrInst(null, endBlk, null));
            curBlock = endBlk;
            curScope = curScope.parScope;
            return;
        }
        BasicBlock thenBlk = Block("then_basicblock");
        BasicBlock elseBlk = Block("else_basicblock");
        BasicBlock endBlk = Block("if_end_basicblock");
        curBlock.nxtBlk.add(thenBlk); curBlock.nxtBlk.add(elseBlk);
        thenBlk.preBlk.add(curBlock); thenBlk.nxtBlk.add(endBlk);
        endBlk.preBlk.add(thenBlk); endBlk.preBlk.add(elseBlk);
        elseBlk.preBlk.add(curBlock); elseBlk.nxtBlk.add(endBlk);
        curBlock.addInst(new BrInst(ifStmtNode.ifExpr.irPar, thenBlk, elseBlk));
        curBlock = thenBlk;
        ifStmtNode.thenStmt.accept(this);
        curBlock.addInst(new BrInst(null, endBlk, null));
        curBlock = elseBlk;
        ifStmtNode.elseStmt.accept(this);
        curBlock.addInst(new BrInst(null, endBlk, null));
        curBlock = endBlk;
        curScope = curScope.parScope;
    }

    @Override
    public void visit(ForStmtNode forStmtNode) {
        curScope = new IRScope(curScope);
        if (forStmtNode.initExpr!= null) forStmtNode.initExpr.accept(this);
        BasicBlock cond = null, step = null, body = Block("for_body"), end = Block("for_end_merge");
        if (forStmtNode.condExpr != null) {
            cond = Block("for_condition");
            curBlock.nxtBlk.add(cond); cond.preBlk.add(curBlock);
            cond.nxtBlk.add(end); end.preBlk.add(cond);
            cond.nxtBlk.add(body); body.preBlk.add(cond);
        }
        if (forStmtNode.stepExpr != null) {
            step = Block("for_step");
            body.nxtBlk.add(step); step.preBlk.add(body);
        }
        if (cond != null && step != null) {
            step.nxtBlk.add(cond); cond.preBlk.add(step);
            breakStack.push(end); continueStack.push(step);
            curBlock.addInst(new BrInst(null, cond, null));
            curBlock = cond;
            forStmtNode.condExpr.accept(this);
            curBlock.addInst(new BrInst(forStmtNode.condExpr.irPar, body, end));
            curBlock = body;
            if (forStmtNode.forBody != null) forStmtNode.forBody.accept(this);
            curBlock.addInst(new BrInst(null, step, null));
            continueStack.pop(); breakStack.pop();
            curBlock = step;
            forStmtNode.stepExpr.accept(this);
            curBlock.addInst(new BrInst(null, cond, null));
        } else if (step == null && cond != null) {
            body.nxtBlk.add(cond); cond.preBlk.add(body);
            breakStack.push(end); continueStack.push(cond);
            curBlock.addInst(new BrInst(null, cond, null));
            curBlock = cond;
            forStmtNode.condExpr.accept(this);
            curBlock.addInst(new BrInst(forStmtNode.condExpr.irPar, body, end));
            curBlock = body;
            if (forStmtNode.forBody != null) forStmtNode.forBody.accept(this);
            curBlock.addInst(new BrInst(null, cond, null));
            continueStack.pop(); breakStack.pop();
        } else if (step != null) {
            step.nxtBlk.add(body); body.preBlk.add(step);
            continueStack.push(step); breakStack.push(end);
            curBlock.addInst(new BrInst(null, body, null));
            curBlock = body;
            if (forStmtNode.forBody != null) forStmtNode.forBody.accept(this);
            curBlock.addInst(new BrInst(null, step, null));
            continueStack.pop(); breakStack.pop();
            curBlock = step;
            forStmtNode.stepExpr.accept(this);
            curBlock.addInst(new BrInst(null, body, null));
        } else {
            body.nxtBlk.add(body); body.preBlk.add(body);
            continueStack.push(body); breakStack.push(end);
            curBlock.addInst(new BrInst( null, body, null));
            curBlock = body;
            if (forStmtNode.forBody != null) forStmtNode.forBody.accept(this);
            curBlock.addInst(new BrInst(null, body, null));
            continueStack.pop(); breakStack.pop();
        }
        curBlock = end;
        curScope = curScope.parScope;
    }

    @Override
    public void visit(WhileStmtNode whileStmtNode) {
        BasicBlock cond = Block("while_condition");
        BasicBlock body = Block("while_body");
        BasicBlock end = Block("while_end_merge");
        curBlock.nxtBlk.add(cond); cond.preBlk.add(curBlock);
        cond.nxtBlk.add(body); body.preBlk.add(cond);
        cond.nxtBlk.add(end); end.preBlk.add(curBlock);
        body.nxtBlk.add(cond); cond.preBlk.add(body);
        continueStack.push(cond); breakStack.push(end);
        curBlock.addInst(new BrInst(null, cond, null));
        curBlock = cond;
        whileStmtNode.whileExpr.accept(this);
        curBlock.addInst(new BrInst(whileStmtNode.whileExpr.irPar, body, end));
        curBlock = body;
        if (whileStmtNode.whileBody != null) whileStmtNode.whileBody.accept(this);
        continueStack.pop(); breakStack.pop();
        curBlock.addInst(new BrInst(null, cond, null));
        curBlock = end;
    }

    @Override
    public void visit(ReturnStmtNode returnStmtNode) {
        if (returnStmtNode.returnExpr != null) {
            returnStmtNode.returnExpr.accept(this);
            curBlock.addInst(new StoreInst(returnStmtNode.returnExpr.irPar, curFunc.retReg));
        }
        curBlock.addInst(new BrInst(null, curFunc.retBlk, null));
    }

    @Override
    public void visit(ContinueStmtNode continueStmtNode) {
        curBlock.addInst(new BrInst(null, continueStack.peek(), null));
    }

    @Override
    public void visit(BreakStmtNode breakStmtNode) {
        curBlock.addInst(new BrInst(null, breakStack.peek(), null));
    }

    @Override
    public void visit(PureExprStmtNode pureExprStmtNode) {
        pureExprStmtNode.expr.accept(this);
    }

    @Override
    public void visit(IdExprNode idExprNode) {
        Constant id = curScope.get(idExprNode.name);
        if (id != null) {
            Parameter reg = Register(transType(idExprNode.exprType), idExprNode.name);
            curBlock.addInst(new LoadInst(reg, id));
            idExprNode.irPar = reg;
            return;
        }
        Parameter reg = Register(transType(curClass.className), "this_addr");
        curBlock.addInst(new LoadInst(reg, curScope.get("this_addr")));
        int cnt = 0, f = 0;
        BaseType type = null;
        for (VardefStmtNode stmt : curClass.varMem) {
            for (VarDefNode var : stmt.elements) {
                if (Objects.equals(idExprNode.name, var.varName)) {
                    f = 1;
                    type = transType(var.varType);
                    break;
                }
                cnt++;
            }
            if (f == 1) break;
        }
        ArrayList<Constant> list = new ArrayList<>();
        list.add(new ConstantValue(0));
        list.add(new ConstantValue(cnt));
        assert type != null;
        Parameter gep_in_id = Register(new PointerType(type), curClass.className + "." + idExprNode.name + "_gep_in_id");
        GetElementPtrInst inst = new GetElementPtrInst(gep_in_id, reg, list);
        inst.prefixByte = prefixByteMap.get(curClass.className);
        curBlock.addInst(inst);
        curScope.addID(idExprNode.name, gep_in_id);
        Parameter load_reg = Register(type, curClass.className + "." + idExprNode.name + "_load_reg");
        curBlock.addInst(new LoadInst(load_reg, gep_in_id));
        idExprNode.irPar = load_reg;
    }

    @Override
    public void visit(ThisExprNode thisExprNode) {
        Parameter Reg = Register(new PointerType(structMap.get(curClass.className)), "thisexpr_reg");
        curBlock.addInst(new LoadInst(Reg, curScope.get("this_addr")));
        thisExprNode.irPar = Reg;
    }

    @Override
    public void visit(NullConstExprNode nullConstExprNode) {
        nullConstExprNode.irPar = new ConstantValue(new BaseType("void"));
    }

    @Override
    public void visit(IntConstExprNode intConstExprNode) {
        intConstExprNode.irPar = new ConstantValue(intConstExprNode.value);
    }

    @Override
    public void visit(StringConstExprNode stringConstExprNode) {
        GlobalVar str;
        String strName = stringConstExprNode.value;
        if (stringMap.containsKey(strName)) str = stringMap.get(strName);
        else {
            //add in stringMap
            str = new GlobalVar(new PointerType(new ArrayType(new BaseType("i8"), strName.length())), "const_string" + stringMap.size());
            str.init = new ConstantValue(strName);
            stringMap.put(strName, str);
            glbVarMap.put(strName,str);
        }
        Parameter strReg = Register(transType("string"), "const_string_pointer");
        ArrayList<Constant> list = new ArrayList<>();
        list.add(new ConstantValue(0));
        list.add(new ConstantValue(0));
        curBlock.addInst(new GetElementPtrInst(strReg, str, list));
        stringConstExprNode.irPar = strReg;
    }

    @Override
    public void visit(BoolConstExprNode boolConstExprNode) {
        boolConstExprNode.irPar = new ConstantValue(boolConstExprNode.value);
    }

    @Override
    public void visit(NewExprNode newExprNode) {
        if (newExprNode.size == 0) {
            //for class
            StructType type = structMap.get(newExprNode.newType.Typename);
            Parameter class_malloc = Register(new PointerType(new BaseType("i8")), "class_malloc");
            ArrayList<Constant> list = new ArrayList<>();
            list.add(new ConstantValue(type.byteNum));
            curBlock.addInst(new CallInst(class_malloc, list, funcMap.get("_f_malloc")));
            Parameter class_ptr = Register(new PointerType(type), "class_ptr");
            curBlock.addInst(new BitCastInst(class_ptr, class_malloc, new PointerType(type)));
            newExprNode.irPar = class_ptr;
            IRFunction construct = funcMap.get(newExprNode.newType.Typename + "." + newExprNode.newType.Typename);
            if (construct != null) {
                ArrayList<Constant> par = new ArrayList<>();
                par.add(class_ptr);
                curBlock.addInst(new CallInst(null, par, construct));
            }
            return;
        }
        ArrayList<Constant> list = new ArrayList<>();
        for (ExprNode node : newExprNode.sizeList) {
            node.accept(this);
            list.add(node.irPar);
        }
        BaseType type = transType(newExprNode.newType);
        for (int i = 0; i < newExprNode.size; i++) type = new PointerType(type);
        newExprNode.irPar = mlcArray(0, list, type);
    }

    @Override
    public void visit(MemberAccExprNode memberAccExprNode) {
        memberAccExprNode.object.accept(this);
        int cnt = 0;
        BaseType type = null;
        for (Map.Entry<String, TypeNode> var : semantic_scope.classTable.get(memberAccExprNode.object.exprType.Typename).varTable.entrySet()) {
            if (Objects.equals(memberAccExprNode.name, var.getKey())) {
                type = transType(var.getValue());
                break;
            }
            cnt++;
        }
        assert type != null;
        Parameter class_mem_gep_reg = Register(new PointerType(type), "class_mem_gep_reg");
        ArrayList<Constant> par = new ArrayList<>();
        par.add(new ConstantValue(0));
        par.add(new ConstantValue(cnt));
        GetElementPtrInst inst = new GetElementPtrInst(class_mem_gep_reg, memberAccExprNode.object.irPar, par);
        inst.prefixByte = prefixByteMap.get(memberAccExprNode.object.exprType.Typename);
        curBlock.addInst(inst);
        Parameter load_member = Register(type, "load_member");
        curBlock.addInst(new LoadInst(load_member, class_mem_gep_reg));
        memberAccExprNode.irPar = load_member;
        memberAccExprNode.addr = class_mem_gep_reg;
    }

    @Override
    public void visit(FuncCallExprNode funcCallExprNode) {
        ArrayList<Constant> aryList = new ArrayList<>();
        if (funcCallExprNode.func instanceof IdExprNode) {
            FuncDefNode funcNode;
            IRFunction irFunc;
            if (curClass != null && semantic_scope.classTable.get(curClass.className).funcTable.containsKey(((IdExprNode) funcCallExprNode.func).name)) {
                funcNode = semantic_scope.classTable.get(curClass.className).fetchFunc(((IdExprNode) funcCallExprNode.func).name);
                irFunc = funcMap.get(curClass.className + "." + ((IdExprNode)funcCallExprNode.func).name);
                if (funcNode != null || irFunc != null) {
                    Parameter Ici = Register(transType(curClass.className), "Implicit_call_inclass");
                    curBlock.addInst(new LoadInst(Ici, curScope.get("this_addr")));
                    aryList.add(Ici);
                }
            } else {
                funcNode = semantic_scope.fetchFunc(((IdExprNode)funcCallExprNode.func).name);
                irFunc = funcMap.get(funcNode.funcName);
            }
            if (funcCallExprNode.aryList != null)
                for (ExprNode node : funcCallExprNode.aryList) {
                    node.accept(this);
                    aryList.add(node.irPar);
                }
            Parameter reg = null;
            assert funcNode != null;
            if (irFunc.retType != null && !irFunc.retType.equal("void")) reg = Register(irFunc.retType, "call_" + funcNode.funcName);
            curBlock.addInst(new CallInst(reg,aryList,irFunc));
            funcCallExprNode.irPar = reg;
        } else if (funcCallExprNode.func instanceof MemberAccExprNode) {
            ExprNode Class = ((MemberAccExprNode) funcCallExprNode.func).object;
            Class.accept(this);
            if (Class.exprType instanceof ArrayTypeNode) {
                Parameter bitcast_i32 = Register(new PointerType(new BaseType("i32")), "bitcast_i32");
                curBlock.addInst(new BitCastInst(bitcast_i32, (Parameter) Class.irPar, new PointerType(new BaseType("i32"))));
                Parameter gep_size = Register(new PointerType(new BaseType("i32")), "gep_size");
                ArrayList<Constant> par = new ArrayList<>();
                par.add(new ConstantValue(-1));
                curBlock.addInst(new GetElementPtrInst(gep_size, bitcast_i32, par));
                Parameter size_load = Register(new BaseType("i32"), "size_load");
                curBlock.addInst(new LoadInst(size_load, gep_size));
                funcCallExprNode.irPar = size_load;
                return;
            }
            IRFunction func = Objects.equals(Class.exprType.Typename, "string") ?
                    funcMap.get("_str_" + ((MemberAccExprNode) funcCallExprNode.func).name) : funcMap.get(Class.exprType.Typename + "." + ((MemberAccExprNode) funcCallExprNode.func).name);
            aryList.add(Class.irPar);
            if (funcCallExprNode.aryList != null)
                for (ExprNode ary : funcCallExprNode.aryList) {
                    ary.accept(this);
                    aryList.add(ary.irPar);
                }
            Parameter reg = func.retType.equal("void") ? null : Register(func.retType, "call_" + Class.exprType.Typename + "_" + ((MemberAccExprNode) funcCallExprNode.func).name);
            curBlock.addInst(new CallInst(reg, aryList, func));
            funcCallExprNode.irPar = reg;
        }
    }

    @Override
    public void visit(ArrayAccExprNode arrayAccExprNode) {
        arrayAccExprNode.array.accept(this);
        arrayAccExprNode.index.accept(this);
        Parameter getelementptr_reg = Register(arrayAccExprNode.array.irPar.type, "getelementptr_reg");
        ArrayList<Constant> offset = new ArrayList<>();
        offset.add(arrayAccExprNode.index.irPar);
        curBlock.addInst(new GetElementPtrInst(getelementptr_reg, arrayAccExprNode.array.irPar, offset));
        Parameter load_result = Register(((PointerType) arrayAccExprNode.array.irPar.type).getObjType(), "load_result");
        curBlock.addInst(new LoadInst(load_result, getelementptr_reg));
        arrayAccExprNode.addr = getelementptr_reg;
        arrayAccExprNode.irPar = load_result;
    }

    @Override
    public void visit(SelfExprNode selfExprNode) {
        selfExprNode.object.accept(this);
        selfExprNode.irPar = selfExprNode.object.irPar;
        String op = Objects.equals(selfExprNode.op, "++") ? "add" : "sub";
        Parameter reg = Register(transType("int"), op);
        curBlock.addInst(new BinaryInst(reg, selfExprNode.object.irPar, new ConstantValue(1), op));
        curBlock.addInst(new StoreInst(reg, lAddr(selfExprNode.object)));
    }

    @Override
    public void visit(UnaryExprNode unaryExprNode) {
        unaryExprNode.object.accept(this);
        switch (unaryExprNode.op) {
            case "++", "--" -> {
                String op = unaryExprNode.op.equals("++") ? "add" : "sub";
                Parameter reg = Register(transType("int"), op);
                unaryExprNode.addr = lAddr(unaryExprNode.object);
                curBlock.addInst(new BinaryInst(reg, unaryExprNode.object.irPar, new ConstantValue(1), op));
                curBlock.addInst(new StoreInst(reg, unaryExprNode.addr));
                unaryExprNode.irPar = reg;
            }
            case "-" -> {
                Parameter reg = Register(transType("int"), "SUB_single_front");
                curBlock.addInst(new BinaryInst(reg, unaryExprNode.object.irPar, new ConstantValue(-1), "mul"));
                unaryExprNode.irPar = reg;
            }
            case "+" -> unaryExprNode.irPar = unaryExprNode.object.irPar;
            case "!" -> {
                Parameter reg = Register(transType("bool"), "NOT");
                curBlock.addInst(new BinaryInst(reg, unaryExprNode.object.irPar, new ConstantValue(true), "xor"));
                unaryExprNode.irPar = reg;
            }
            case "~" -> {
                Parameter reg = Register(transType("int"), "TILDE");
                curBlock.addInst(new BinaryInst(reg, unaryExprNode.object.irPar, new ConstantValue(-1), "xor"));
                unaryExprNode.irPar = reg;
            }
        }
    }

    @Override
    public void visit(BinaryExprNode binaryExprNode) {
        binaryExprNode.exprL.accept(this);
        if (Objects.equals(binaryExprNode.op, "&&") || Objects.equals(binaryExprNode.op, "||")) {
            String op = Objects.equals(binaryExprNode.op, "&&") ? "AND" : "OR";
            BasicBlock end = Block("short_circuit_" + op + "_end_" + op),
                    branch = Block("short_circuit_" + op + "_branch_" + op);
            curBlock.nxtBlk.add(end); curBlock.nxtBlk.add(branch);
            end.preBlk.add(curBlock); end.preBlk.add(branch);
            branch.preBlk.add(curBlock); branch.nxtBlk.add(end);
            Parameter reg = Register(new PointerType(transType("bool")), op + "_addr");
            if (curBlock.instList.isEmpty() || !(curBlock.instList.getLast() instanceof BrInst))
                curFunc.entryBlk.instList.addFirst(new AllocateInst(transType("bool"), reg));
            curBlock.addInst(new StoreInst(binaryExprNode.exprL.irPar, reg));
            if (op.equals("AND")) curBlock.addInst(new BrInst(binaryExprNode.exprL.irPar,branch,end));
            else curBlock.addInst(new BrInst(binaryExprNode.exprL.irPar,end,branch));
            curBlock = branch;
            binaryExprNode.exprR.accept(this);
            curBlock.addInst(new StoreInst(binaryExprNode.exprR.irPar, reg));
            curBlock.addInst(new BrInst(null, end, null));
            curBlock = end;
            Parameter loadReg = Register(transType("bool"), op + "_short_circuit");
            curBlock.addInst(new LoadInst(loadReg, reg));
            binaryExprNode.irPar = loadReg;
            return;
        }
        binaryExprNode.exprR.accept(this);
        if (transType(binaryExprNode.exprL.exprType).equal("string") && transType(binaryExprNode.exprR.exprType).equal("string")) {
            switch (binaryExprNode.op) {
                case "+" -> {
                    ArrayList<Constant> parList = new ArrayList<>();
                    parList.add(binaryExprNode.exprL.irPar);
                    parList.add(binaryExprNode.exprR.irPar);
                    Parameter reg = Register(transType("string"), "string_add");
                    curBlock.addInst(new CallInst(reg, parList, funcMap.get("_str_concatenate")));
                    binaryExprNode.irPar = reg;
                }
                case "==", "!=", "<", ">", "<=",  ">=" -> {
                    ArrayList<Constant> parList = new ArrayList<>();
                    parList.add(binaryExprNode.exprL.irPar);
                    parList.add(binaryExprNode.exprR.irPar);
                    String name = Objects.equals(binaryExprNode.op,"==") ? "_str_eq" : Objects.equals(binaryExprNode.op,"!=") ? "_str_ne" :
                            Objects.equals(binaryExprNode.op,"<") ? "_str_lt" : Objects.equals(binaryExprNode.op,">") ? "_str_gt" :
                                    Objects.equals(binaryExprNode.op,"<=") ? "_str_le" : "_str_ge";
                    Parameter reg = Register(transType("bool"), "cmp_result" + name);
                    curBlock.addInst(new CallInst(reg, parList, funcMap.get(name)));
                    binaryExprNode.irPar = reg;
                }
            }
            return;
        }
        switch (binaryExprNode.op) {
            case "+", "-", "%", "/", "*", ">>", "<<", "&", "^", "|" -> {
                String op = Objects.equals(binaryExprNode.op, "+") ? "add" : Objects.equals(binaryExprNode.op, "-") ? "sub" :
                        Objects.equals(binaryExprNode.op, "%") ? "srem" : Objects.equals(binaryExprNode.op, "/") ? "sdiv" :
                                Objects.equals(binaryExprNode.op, "*") ? "mul" : Objects.equals(binaryExprNode.op, ">>") ? "ashr" :
                                        Objects.equals(binaryExprNode.op, "<<") ? "shl" : Objects.equals(binaryExprNode.op, "&") ? "and" :
                                                Objects.equals(binaryExprNode.op, "^") ? "xor" : "or";
                Parameter reg = Register(transType("int"), op);
                curBlock.addInst(new BinaryInst(reg, binaryExprNode.exprL.irPar, binaryExprNode.exprR.irPar, op));
                binaryExprNode.irPar = reg;
            }
            case "==", "!=", "<", ">", "<=", ">=" -> {
                String op = Objects.equals(binaryExprNode.op, "==") ? "eq" : Objects.equals(binaryExprNode.op, "!=") ? "ne" :
                        Objects.equals(binaryExprNode.op, "<") ? "slt" : Objects.equals(binaryExprNode.op, ">") ? "sgt" :
                                Objects.equals(binaryExprNode.op, "<=") ? "sle" : "sge";
                Parameter reg = Register(transType("bool"), op);
                curBlock.addInst(new CmpInst(reg, op, binaryExprNode.exprL.irPar, binaryExprNode.exprR.irPar));
                binaryExprNode.irPar = reg;
            }
        }
    }

    @Override
    public void visit(AssignExprNode assignExprNode) {
        assignExprNode.exprL.accept(this);
        assignExprNode.exprR.accept(this);
        curBlock.addInst(new StoreInst(assignExprNode.exprR.irPar, lAddr(assignExprNode.exprL)));
        assignExprNode.irPar = assignExprNode.exprR.irPar;
    }

    @Override
    public void visit(LambdaExprNode lambdaExprNode) {
        lambdaExprNode.irPar = new ConstantValue(1);
    }

    public BaseType transType(TypeNode type){
        if (type instanceof ArrayTypeNode) return new PointerType(transType(((ArrayTypeNode) type).type));
        if (type.toIRType() != null) return type.toIRType();
        return new PointerType(structMap.get(type.Typename));
    }

    public BaseType transType(String type) {
        if (Objects.equals(type, "bool")) return new BaseType("i1");
        if (Objects.equals(type, "int")) return new BaseType("i32");
        if (Objects.equals(type, "string")) return new PointerType(new BaseType("i8"));
        return new PointerType(structMap.get(type));
    }

    public Constant lAddr(ExprNode expr) {
        if (expr instanceof IdExprNode) return curScope.get(((IdExprNode) expr).name);
        if (expr instanceof MemberAccExprNode) return ((MemberAccExprNode) expr).addr;
        if (expr instanceof ArrayAccExprNode) return ((ArrayAccExprNode) expr).addr;
        if (expr instanceof UnaryExprNode) return ((UnaryExprNode) expr).addr;
        return null;
    }

    public BasicBlock Block(String name) {
        BasicBlock blk = new BasicBlock(name, curFunc);
        curFunc.blkList.add(blk);
        curFunc.renameAdd(blk);
        return blk;
    }

    public Parameter Register(BaseType type, String name) {
        Parameter reg = new Parameter(type, name, true);
        if (curFunc != null) curFunc.renameAdd(reg);
        return reg;
    }

    public Parameter mlcArray(int dim, ArrayList<Constant> list, BaseType type) {
        Parameter mul_bytes = Register(new BaseType("i32"), "mul_bytes");
        curBlock.addInst(new BinaryInst(mul_bytes, list.get(dim), new ConstantValue(((PointerType)type).getObjType().byteNum), "mul"));
        Parameter sum_bytes = Register(new BaseType("i32"), "sum_bytes");
        curBlock.addInst(new BinaryInst(sum_bytes, mul_bytes, new ConstantValue(4), "add"));
        ArrayList<Constant> par = new ArrayList<>();
        par.add(sum_bytes);
        Parameter malloca = Register(new PointerType(new BaseType("i8")), "malloca");
        curBlock.addInst(new CallInst(malloca, par, funcMap.get("_f_malloc")));
        Parameter array_cast_i8_to_i32 = Register(new PointerType(new BaseType("i32")), "array_cast_i8_to_i32");
        curBlock.addInst(new BitCastInst(array_cast_i8_to_i32, malloca, new PointerType(new BaseType("i32"))));
        curBlock.addInst(new StoreInst(list.get(dim), array_cast_i8_to_i32));
        ArrayList<Constant> offset = new ArrayList<>();
        offset.add(new ConstantValue(1));
        Parameter array_tmp_begin_i32 = Register(new PointerType(new BaseType("i32")), "array_tmp_begin_i32");
        curBlock.addInst(new GetElementPtrInst(array_tmp_begin_i32, array_cast_i8_to_i32, offset));
        Parameter array_addr = Register(type, "array_addr");
        curBlock.addInst(new BitCastInst(array_addr, array_tmp_begin_i32, type));
        if (dim < list.size() - 1) {
            ArrayList<Constant> tail_offset = new ArrayList<>();
            tail_offset.add(list.get(dim));
            Parameter array_tail_addr = Register(new PointerType(type), "array_tail_addr");
            curBlock.addInst(new GetElementPtrInst(array_tail_addr, array_addr, tail_offset));
            Parameter current_array_ptr_addr = Register(new PointerType(type), "current_array_ptr_addr");
            if (curBlock.instList.isEmpty() || !(curBlock.instList.getLast() instanceof BrInst)) curBlock.instList.addFirst(new AllocateInst(type, current_array_ptr_addr));
            curBlock.addInst(new StoreInst(array_addr, current_array_ptr_addr));

            BasicBlock cond = Block("new_condition"), body = Block("new_loop_body"), end = Block("new_end");
            curBlock.nxtBlk.add(cond); cond.preBlk.add(curBlock);
            cond.nxtBlk.add(body); body.preBlk.add(cond);
            cond.nxtBlk.add(end); end.preBlk.add(cond);
            body.nxtBlk.add(cond); cond.preBlk.add(body);
            curBlock.addInst(new BrInst(null, cond, null));
            curBlock = cond;
            Parameter load_tmp_current_pointer = Register(type, "load_tmp_current_pointer");
            curBlock.addInst(new LoadInst(load_tmp_current_pointer, current_array_ptr_addr));
            Parameter addr_cmp_result = Register(new BaseType("i1"), "addr_cmp_result");
            curBlock.addInst(new CmpInst(addr_cmp_result, "slt", load_tmp_current_pointer, array_tail_addr));
            curBlock.addInst(new BrInst(addr_cmp_result, body, end));
            curBlock = body;
            Parameter array_addr_head = mlcArray(dim + 1, list, ((PointerType) type).getObjType());
            curFunc.renameAdd(array_addr_head);
            curBlock.addInst(new StoreInst(array_addr_head, load_tmp_current_pointer));
            ArrayList<Constant> nxt_offset = new ArrayList<>();
            nxt_offset.add(new ConstantValue(1));
            Parameter nxt_pointer = Register(type, "nxt_pointer");
            curBlock.addInst(new GetElementPtrInst(nxt_pointer, load_tmp_current_pointer, nxt_offset));
            curBlock.addInst(new StoreInst(nxt_pointer, current_array_ptr_addr));
            curBlock.addInst(new BrInst(null, cond, null));
            curBlock = end;
        }
        return array_addr;
    }
}
