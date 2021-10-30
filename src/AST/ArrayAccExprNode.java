package AST;

import Util.position;

public class ArrayAccExprNode extends ExprNode {
    public ExprNode array;
    public ExprNode index;
    public ArrayAccExprNode(ExprNode array, ExprNode index, position pos) {
        super(pos);
        this.array=array;
        this.index=index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
