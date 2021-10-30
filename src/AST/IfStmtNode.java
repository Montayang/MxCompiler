package AST;

import Util.position;

public class IfStmtNode extends StmtNode {
    public ExprNode ifExpr;
    public StmtNode thenStmt;
    public StmtNode elseStmt;
    public IfStmtNode(ExprNode expr,StmtNode thenStmt,StmtNode elseStmt,position pos) {
        super(pos);
        this.ifExpr=expr;
        this.thenStmt=thenStmt;
        this.elseStmt=elseStmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
