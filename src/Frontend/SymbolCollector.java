package Frontend;

import AST.*;
import Util.error.semanticError;
import Util.globalScope;
import Util.position;

import java.util.ArrayList;
import java.util.Objects;

public class SymbolCollector implements ASTVisitor {
    public globalScope glbScope;
    public SymbolCollector() {
        glbScope = new globalScope(null);
        position initPos = new position(-1,-1);
        ArrayList<VarDefNode> list=new ArrayList<>();
        list.add(new VarDefNode(new ClassTypeNode("string",initPos),"str",null,initPos));
        glbScope.defFunction("print", new FuncDefNode(new ClassTypeNode("void",initPos),"print",list,null,initPos));
        glbScope.defFunction("println", new FuncDefNode(new ClassTypeNode("void",initPos),"println",list,null,initPos));

        list=new ArrayList<>();
        list.add(new VarDefNode(new ClassTypeNode("int",initPos),"n",null,initPos));
        glbScope.defFunction("printInt", new FuncDefNode(new ClassTypeNode("void",initPos),"printInt",list,null,initPos));
        glbScope.defFunction("printlnInt", new FuncDefNode(new ClassTypeNode("void",initPos),"printlnInt",list,null,initPos));
        glbScope.defFunction("toString", new FuncDefNode(new ClassTypeNode("string",initPos),"toString",list,null,initPos));
        glbScope.defFunction("getString", new FuncDefNode(new ClassTypeNode("string",initPos),"getString",null,null,initPos));
        glbScope.defFunction("getInt", new FuncDefNode(new ClassTypeNode("int",initPos),"getInt",null,null,initPos));

        glbScope.defClass("bool",new globalScope(glbScope));
        glbScope.defClass("int",new globalScope(glbScope));
        globalScope stringScp=new globalScope(glbScope);
        stringScp.defFunction("length",new FuncDefNode(new ClassTypeNode("int",initPos),"length",null,null,initPos));
        list=new ArrayList<>();
        list.add(new VarDefNode(new ClassTypeNode("int",initPos),"left",null,initPos));
        list.add(new VarDefNode(new ClassTypeNode("int",initPos),"right",null,initPos));
        stringScp.defFunction("substring",new FuncDefNode(new ClassTypeNode("string",initPos),"substring",list,null,initPos));
        stringScp.defFunction("parseInt",new FuncDefNode(new ClassTypeNode("int",initPos),"parseInt",null,null,initPos));
        list=new ArrayList<>();
        list.add(new VarDefNode(new ClassTypeNode("int",initPos),"pos",null,initPos));
        stringScp.defFunction("ord",new FuncDefNode(new ClassTypeNode("int",initPos),"ord",list,null,initPos));
        glbScope.defClass("string",stringScp);
    }

    @Override
    public void visit(RootNode rootNode) {
        for (ASTNode node : rootNode.elements) node.accept(this);
        if (!glbScope.funcTable.containsKey("main") || !Objects.equals(glbScope.fetchFunc("main").funcType.Typename, "int"))
            throw new semanticError("No main function", rootNode.pos);
        if (glbScope.fetchFunc("main").parList != null) throw new semanticError("Main function has parameters", rootNode.pos);
    }

    @Override
    public void visit(ClassDefNode classDefNode) {
        if (glbScope.containClass(classDefNode.className) || glbScope.funcTable.containsKey(classDefNode.className))
            throw new semanticError("Duplicate define", classDefNode.pos);
        globalScope classScope = new globalScope(glbScope);
        for (VardefStmtNode ele : classDefNode.varMem) {
            for (VarDefNode var : ele.elements) {
                if (classScope.containVar(var.varName)) throw new semanticError("Duplicate define variable in class", classDefNode.pos);
                classScope.defVar(var.varName, var.varType);
            }
        }
        for (FuncDefNode func : classDefNode.funcMem) {
            if (classScope.containVar(func.funcName)) throw new semanticError("Duplicate define function in class", classDefNode.pos);
            if (func.funcType == null && !Objects.equals(func.funcName, classDefNode.className)) throw new semanticError("Invalid constructor", classDefNode.pos);
            classScope.defFunction(func.funcName, func);
        }
        glbScope.defClass(classDefNode.className, classScope);
    }

    @Override
    public void visit(FuncDefNode funcDefNode) {
        if (glbScope.containClass(funcDefNode.funcName) || glbScope.funcTable.containsKey(funcDefNode.funcName))
            throw new semanticError("Duplicate define", funcDefNode.pos);
        if (funcDefNode.funcType == null) throw new semanticError("Function has no type", funcDefNode.pos);
        glbScope.defFunction(funcDefNode.funcName, funcDefNode);
    }

    @Override public void visit(VarDefNode varDefNode) {}
    @Override public void visit(BlockStmtNode blockStmtNode) {}
    @Override public void visit(VardefStmtNode vardefStmtNode) {}
    @Override public void visit(IfStmtNode ifStmtNode) {}
    @Override public void visit(ForStmtNode forStmtNode) {}
    @Override public void visit(WhileStmtNode whileStmtNode) {}
    @Override public void visit(ReturnStmtNode returnStmtNode) {}
    @Override public void visit(ContinueStmtNode continueStmtNode) {}
    @Override public void visit(BreakStmtNode breakStmtNode) {}
    @Override public void visit(PureExprStmtNode pureExprStmtNode) {}
    @Override public void visit(IdExprNode idExprNode) {}
    @Override public void visit(ThisExprNode thisExprNode) {}
    @Override public void visit(NullConstExprNode nullConstExprNode) {}
    @Override public void visit(IntConstExprNode intConstExprNode) {}
    @Override public void visit(StringConstExprNode stringConstExprNode) {}
    @Override public void visit(BoolConstExprNode boolConstExprNode) {}
    @Override public void visit(NewExprNode newExprNode) {}
    @Override public void visit(MemberAccExprNode memberAccExprNode) {}
    @Override public void visit(FuncCallExprNode funcCallExprNode) {}
    @Override public void visit(ArrayAccExprNode arrayAccExprNode) {}
    @Override public void visit(SelfExprNode selfExprNode) {}
    @Override public void visit(UnaryExprNode unaryExprNode) {}
    @Override public void visit(BinaryExprNode binaryExprNode) {}
    @Override public void visit(AssignExprNode assignExprNode) {}
    @Override public void visit(LambdaExprNode lambdaExprNode) {}
}