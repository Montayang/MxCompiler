package AST;

import Util.position;

public class IntConstExprNode extends ExprNode {
    public int value;

    public IntConstExprNode(int val, position pos) {
        super(pos);
        this.value = val;
        this.exprType=new ClassTypeNode("int", new position(-1,-1));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
