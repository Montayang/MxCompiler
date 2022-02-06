package AST;

import MIR.Value.User.Constant.Constant;
import Util.position;

public class ArrayAccExprNode extends ExprNode {
    public ExprNode array;
    public ExprNode index;
    public Constant addr;
    public ArrayAccExprNode(ExprNode array, ExprNode index, position pos) {
        super(pos);
        this.array=array;
        this.index=index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
