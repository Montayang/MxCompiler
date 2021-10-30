package AST;

import Util.position;

public class BoolConstExprNode extends ExprNode {
    public boolean value;

    public BoolConstExprNode(boolean val, position pos) {
        super(pos);
        this.value = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
