package AST;

import Util.position;

import java.util.ArrayList;

public class LambdaExprNode extends ExprNode {
    public TypeNode returnType;
    public ArrayList<VarDefNode> parList;
    public BlockStmtNode body;
    public ArrayList<ExprNode> aryList;
    public LambdaExprNode(ArrayList<VarDefNode> parList, BlockStmtNode body, ArrayList<ExprNode> aryList, position pos) {
        super(pos);
        this.returnType=null;
        this.parList=parList;
        this.body=body;
        this.aryList=aryList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
