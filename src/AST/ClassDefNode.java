package AST;

import Util.position;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String className;
    public ArrayList<VardefStmtNode> varMem;
    public ArrayList<FuncDefNode> funcMem;
    public ClassDefNode(String name,ArrayList<VardefStmtNode> var,ArrayList<FuncDefNode> func,position pos) {
        super(pos);
        this.className = name;
        this.varMem = var;
        this.funcMem = func;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}