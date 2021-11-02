package AST;

import Util.position;

public class BoolConstExprNode extends ExprNode {
    public boolean value;

    public BoolConstExprNode(boolean val, position pos) {
        super(pos);
        this.value = val;
        this.exprType=new ClassTypeNode("bool", new position(-1,-1));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
