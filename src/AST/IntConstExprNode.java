package AST;

import Util.position;

public class IntConstExprNode extends ExprNode {
    public long value;

    public IntConstExprNode(long val, position pos) {
        super(pos);
        this.value = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
