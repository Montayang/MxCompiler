package AST;

import Util.position;

public class NullConstExprNode extends ExprNode {
    public NullConstExprNode(position pos) {
        super(pos);
        exprType =new ClassTypeNode(null,new position(-1,-1));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
