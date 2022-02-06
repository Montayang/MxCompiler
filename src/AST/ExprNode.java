package AST;

import MIR.Value.User.Constant.Constant;
import MIR.Value.User.Constant.Parameter;
import Util.position;

abstract public class ExprNode extends ASTNode {
    public TypeNode exprType;
    public boolean isAssignable;
    public Constant irPar;

    public ExprNode(position pos) {
        super(pos);
        this.exprType = null;
        this.isAssignable = false;
    }
}
