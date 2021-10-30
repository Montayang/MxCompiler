package AST;

import Util.position;

abstract public class ExprNode extends ASTNode {
    public TypeNode exprType;
    public boolean isAssignable;

    public ExprNode(position pos) {
        super(pos);
        this.exprType = null;
        this.isAssignable = false;
    }
}
