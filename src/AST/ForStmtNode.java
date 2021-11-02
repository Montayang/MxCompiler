package AST;

import Util.position;

public class ForStmtNode extends StmtNode {
    public StmtNode initExpr;
    public ExprNode condExpr,stepExpr;
    public StmtNode forBody;
    public ForStmtNode(StmtNode initExpr,ExprNode condExpr,ExprNode stepExpr,StmtNode body,position pos) {
        super(pos);
        this.initExpr=initExpr;
        this.condExpr=condExpr;
        this.stepExpr=stepExpr;
        this.forBody=body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
