package AST;

import MIR.Value.User.Constant.Parameter;
import Util.position;

abstract public class ExprNode extends ASTNode {
    public TypeNode exprType;
    public boolean isAssignable;
    public Parameter irPar;

    public ExprNode(position pos) {
        super(pos);
        this.exprType = null;
        this.isAssignable = false;
    }
}
