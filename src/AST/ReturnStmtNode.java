package AST;

import Util.position;

public class ReturnStmtNode extends StmtNode {
    public ExprNode returnExpr;
    public ReturnStmtNode(ExprNode expr,position pos) {
        super(pos);
        this.returnExpr=expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
