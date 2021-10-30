package AST;

import Util.position;

import java.util.ArrayList;

public class FuncDefNode extends ASTNode {
    public TypeNode funcType;
    public String funcName;
    public ArrayList<VarDefNode> parList;
    public BlockStmtNode funcBody;

    public FuncDefNode(TypeNode type,String name,ArrayList<VarDefNode> par,BlockStmtNode body,position pos) {
        super(pos);
        this.funcType=type;
        this.funcName=name;
        this.parList=par;
        this.funcBody=body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
