package AST;

import Util.position;

public class NullConstExprNode extends ExprNode {
    public NullConstExprNode(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
