package AST;

import Util.position;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public TypeNode newType;
    public int size;

    public NewExprNode(TypeNode type, int size, position pos) {
        super(pos);
        this.newType = type;
        this.size = size;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}