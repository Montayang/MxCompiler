package AST;

import Util.position;

import java.util.ArrayList;

public class FuncCallExprNode extends ExprNode {
    public ExprNode func;
    public ArrayList<ExprNode> aryList;
    public FuncCallExprNode(ExprNode func, ArrayList<ExprNode> aryList, position pos) {
        super(pos);
        this.func=func;
        this.aryList=aryList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
