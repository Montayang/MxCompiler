package AST;

import Util.position;

public class WhileStmtNode extends StmtNode {
    public ExprNode whileExpr;
    public StmtNode whileBody;
    public WhileStmtNode(ExprNode expr, StmtNode body, position pos) {
        super(pos);
        this.whileExpr=expr;
        this.whileBody=body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
