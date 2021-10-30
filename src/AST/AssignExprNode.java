package AST;

import Util.position;

public class AssignExprNode extends ExprNode {
    public ExprNode exprL,exprR;
    public AssignExprNode(ExprNode exprL,ExprNode exprR,position pos) {
        super(pos);
        this.exprL=exprL;
        this.exprR=exprR;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
