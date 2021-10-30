package AST;

import Util.position;

public class PureExprStmtNode extends StmtNode {
    public ExprNode expr;
    public PureExprStmtNode(ExprNode expr, position pos) {
        super(pos);
        this.expr=expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
