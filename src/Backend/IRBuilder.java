package Backend;

import AST.*;
import MIR.*;
import MIR.IRType.BaseType;
import MIR.IRType.PointerType;
import MIR.IRType.StructType;
import MIR.Value.BasicBlock.BasicBlock;
import MIR.Value.User.Constant.GlobalVar;
import MIR.Value.User.Constant.Parameter;
import MIR.Value.User.Instruction.CallInst;
import Util.*;

import java.util.*;

public class IRBuilder implements ASTVisitor {
    public globalScope semantic_scope;
    public String curClass, curFunc;
    public BasicBlock curBlock;
    public Map<String, IRFunction> funcMap = new HashMap<>();
    public Map<String, IRFunction> exFuncMap = new HashMap<>();
    public Map<String, StructType> structMap = new HashMap<>();
    public Map<String, GlobalVar> glbVarMap = new HashMap<>();
    public Map<String, GlobalVar> stringMap = new HashMap<>();

    public IRBuilder(globalScope scope1) {
        semantic_scope = scope1;
        ArrayList<Parameter> par;
        //void print(string str);
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str_0", false));
        funcMap.put("print", new IRFunction(new BaseType("void"), par, "print"));
        exFuncMap.put("print", new IRFunction(new BaseType("void"), par, "print"));
        //void println(string str);
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str_0", false));
        funcMap.put("println", new IRFunction(new BaseType("void"), par, "println"));
        exFuncMap.put("println", new IRFunction(new BaseType("void"), par, "println"));
        //void printInt(int n);
        par = new ArrayList<>();
        par.add(new Parameter(new BaseType("i32"), "n_0", false));
        funcMap.put("printInt", new IRFunction(new BaseType("void"), par, "printInt"));
        exFuncMap.put("printInt", new IRFunction(new BaseType("void"), par, "printInt"));
        //string getString()
        par = new ArrayList<>();
        funcMap.put("getString", new IRFunction(new PointerType(new BaseType("i8")), par, "getString"));
        exFuncMap.put("getString", new IRFunction(new PointerType(new BaseType("i8")), par, "getString"));
        //int getInt()
        par = new ArrayList<>();
        funcMap.put("getInt", new IRFunction(new BaseType("i32"), par, "getInt"));
        exFuncMap.put("getInt", new IRFunction(new BaseType("i32"), par, "getInt"));
        //string toString(int i)
        par = new ArrayList<>();
        par.add(new Parameter(new BaseType("i32"), "i_0", false));
        funcMap.put("toString", new IRFunction(new PointerType(new BaseType("i8")), par, "toString"));
        exFuncMap.put("toString", new IRFunction(new PointerType(new BaseType("i8")), par, "toString"));
        //int _str_ord(const char* str,int pos)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str", false));
        par.add(new Parameter(new BaseType("i32"), "pos", false));
        funcMap.put("_str_ord", new IRFunction(new BaseType("i32"), par, "_str_ord"));
        exFuncMap.put("_str_ord", new IRFunction(new BaseType("i32"), par, "_str_ord"));
        //bool _str_eq(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_eq", new IRFunction(new BaseType("i1"), par, "_str_eq"));
        exFuncMap.put("_str_eq", new IRFunction(new BaseType("i1"), par, "_str_eq"));
        //bool _str_ne(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_ne", new IRFunction(new BaseType("i1"), par, "_str_ne"));
        exFuncMap.put("_str_ne", new IRFunction(new BaseType("i1"), par, "_str_ne"));
        //bool _str_lt(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_lt", new IRFunction(new BaseType("i1"), par, "_str_lt"));
        exFuncMap.put("_str_lt", new IRFunction(new BaseType("i1"), par, "_str_lt"));
        //bool _str_le(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_le", new IRFunction(new BaseType("i1"), par, "_str_le"));
        exFuncMap.put("_str_le", new IRFunction(new BaseType("i1"), par, "_str_le"));
        //bool _str_gt(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_gt", new IRFunction(new BaseType("i1"), par, "_str_gt"));
        exFuncMap.put("_str_gt", new IRFunction(new BaseType("i1"), par, "_str_gt"));
        //bool _str_ge(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_ge", new IRFunction(new BaseType("i1"), par, "_str_ge"));
        exFuncMap.put("_str_ge", new IRFunction(new BaseType("i1"), par, "_str_ge"));
        //const char* _str_concatenate(const char* lhs,const char* rhs)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "lhs", false));
        par.add(new Parameter(new PointerType(new BaseType("i8")), "rhs", false));
        funcMap.put("_str_concatenate", new IRFunction(new PointerType(new BaseType("i8")), par, "_str_concatenate"));
        exFuncMap.put("_str_concatenate", new IRFunction(new PointerType(new BaseType("i8")), par, "_str_concatenate"));
        //char * _f_malloc(int n)
        par = new ArrayList<>();
        par.add(new Parameter(new BaseType("i32"), "n", false));
        funcMap.put("_f_malloc", new IRFunction(new PointerType(new BaseType("i8")), par, "_f_malloc"));
        exFuncMap.put("_f_malloc", new IRFunction(new PointerType(new BaseType("i8")), par, "_f_malloc"));
        //int _str_length(const char* str)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str", false));
        funcMap.put("_str_length", new IRFunction(new BaseType("i32"), par, "_str_length"));
        exFuncMap.put("_str_length", new IRFunction(new BaseType("i32"), par, "_str_length"));
        //const char* _str_substring(const char* str,int left,int right)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str", false));
        par.add(new Parameter(new BaseType("i32"), "left", false));
        par.add(new Parameter(new BaseType("i32"), "right", false));
        funcMap.put("_str_substring", new IRFunction(new PointerType(new BaseType("i8")), par, "_str_substring"));
        exFuncMap.put("_str_substring", new IRFunction(new PointerType(new BaseType("i8")), par, "_str_substring"));
        //int _str_parseInt(const char* str)
        par = new ArrayList<>();
        par.add(new Parameter(new PointerType(new BaseType("i8")), "str", false));
        funcMap.put("_str_parseInt", new IRFunction(new BaseType("i32"), par, "_str_parseInt"));
        exFuncMap.put("_str_parseInt", new IRFunction(new BaseType("i32"), par, "_str_parseInt"));
    }

    @Override
    public void visit(RootNode rootNode) {
        IRFunction func;
        func = new IRFunction(new BaseType("void"), new ArrayList<>(), "GLOBAL__sub_I_main.mx");
        funcMap.put("GLOBAL__sub_I_main.mx", func);
        curFunc = "GLOBAL__sub_I_main.mx";
        curBlock = func.entryBlk;
        //global var
        for (ASTNode node : rootNode.elements) {
            if (node instanceof VardefStmtNode) {
                node.accept(this);
            }
        }
        //global function
        for (ASTNode node : rootNode.elements) {
            if (node instanceof FuncDefNode) {
                ArrayList<Parameter> parList = new ArrayList<>();
                if (((FuncDefNode) node).parList != null) {
                    for (VarDefNode par : ((FuncDefNode) node).parList) {
                        parList.add(new Parameter(par.varType.toIRType(), par.varName + "_para", false));
                    }
                }
                func = new IRFunction(((FuncDefNode) node).funcType.toIRType(), parList, ((FuncDefNode) node).funcName);
                funcMap.put(((FuncDefNode) node).funcName, func);
            }
        }
        curFunc = null;
        curBlock = null;
        for (ASTNode node : rootNode.elements) {
            if (node instanceof FuncDefNode) node.accept(this);
        }
    }

    @Override
    public void visit(ClassDefNode classDefNode) {

    }

    @Override
    public void visit(FuncDefNode funcDefNode) {

    }

    @Override
    public void visit(VarDefNode varDefNode) {

    }

    @Override
    public void visit(BlockStmtNode blockStmtNode) {

    }

    @Override
    public void visit(VardefStmtNode vardefStmtNode) {

    }

    @Override
    public void visit(IfStmtNode ifStmtNode) {

    }

    @Override
    public void visit(ForStmtNode forStmtNode) {

    }

    @Override
    public void visit(WhileStmtNode whileStmtNode) {

    }

    @Override
    public void visit(ReturnStmtNode returnStmtNode) {

    }

    @Override
    public void visit(ContinueStmtNode continueStmtNode) {

    }

    @Override
    public void visit(BreakStmtNode breakStmtNode) {

    }

    @Override
    public void visit(PureExprStmtNode pureExprStmtNode) {

    }

    @Override
    public void visit(IdExprNode idExprNode) {

    }

    @Override
    public void visit(ThisExprNode thisExprNode) {

    }

    @Override
    public void visit(NullConstExprNode nullConstExprNode) {

    }

    @Override
    public void visit(IntConstExprNode intConstExprNode) {

    }

    @Override
    public void visit(StringConstExprNode stringConstExprNode) {

    }

    @Override
    public void visit(BoolConstExprNode boolConstExprNode) {

    }

    @Override
    public void visit(NewExprNode newExprNode) {

    }

    @Override
    public void visit(MemberAccExprNode memberAccExprNode) {

    }

    @Override
    public void visit(FuncCallExprNode funcCallExprNode) {
        ArrayList<Parameter> aryList = new ArrayList<>();
        if (funcCallExprNode.func instanceof IdExprNode) {
            FuncDefNode funcNode = semantic_scope.fetchFunc(((IdExprNode) funcCallExprNode.func).name);
            IRFunction irFunc = funcMap.get(((IdExprNode) funcCallExprNode.func).name);
            for (ExprNode node : funcCallExprNode.aryList) {
                node.accept(this);
                aryList.add(node.irPar);
            }
            Parameter reg = null;
            if (!Objects.equals(funcNode.funcType.Typename, "void")) {
                reg = new Parameter(irFunc.retType, "call_" + funcNode.funcName, true);
            }
            curBlock.addInst(new CallInst(curBlock,reg,aryList,irFunc));
            funcCallExprNode.irPar = reg;
        } else if (funcCallExprNode.func instanceof MemberAccExprNode) {
            ((MemberAccExprNode) funcCallExprNode.func).object.accept(this);
            if (((MemberAccExprNode) funcCallExprNode.func).object.exprType instanceof ArrayTypeNode) {
                //TODO
            }

        }
    }

    @Override
    public void visit(ArrayAccExprNode arrayAccExprNode) {

    }

    @Override
    public void visit(SelfExprNode selfExprNode) {

    }

    @Override
    public void visit(UnaryExprNode unaryExprNode) {

    }

    @Override
    public void visit(BinaryExprNode binaryExprNode) {

    }

    @Override
    public void visit(AssignExprNode assignExprNode) {

    }

    @Override
    public void visit(LambdaExprNode lambdaExprNode) {}
}
