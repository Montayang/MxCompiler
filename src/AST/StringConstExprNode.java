package AST;

import Util.position;

public class StringConstExprNode extends ExprNode {
    public String value;

    public StringConstExprNode(String val, position pos) {
        super(pos);
        this.value = val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
