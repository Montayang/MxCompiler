package AST;

import Util.position;

public class BinaryExprNode extends ExprNode {
    public ExprNode exprL,exprR;
    public String op;
    public BinaryExprNode(ExprNode exprL, ExprNode exprR, String op,position pos) {
        super(pos);
        this.exprL=exprL;
        this.exprR=exprR;
        this.op=op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
