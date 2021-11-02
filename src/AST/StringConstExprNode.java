package AST;

import Util.position;

public class StringConstExprNode extends ExprNode {
    public String value;

    public StringConstExprNode(String val, position pos) {
        super(pos);
        this.value = val;
        this.exprType=new ClassTypeNode("string", new position(-1,-1));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
