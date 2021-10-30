package AST;

import Util.position;

public class ForStmtNode extends StmtNode {
    public ExprNode initExpr,condExpr,stepExpr;
    public StmtNode forBody;
    public ForStmtNode(ExprNode initExpr,ExprNode condExpr,ExprNode stepExpr,StmtNode body,position pos) {
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
