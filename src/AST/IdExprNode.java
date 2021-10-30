package AST;

import Util.position;

public class IdExprNode extends ExprNode {
    public String name;
    public IdExprNode(String name,position pos) {
        super(pos);
        this.name=name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
