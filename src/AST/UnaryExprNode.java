package AST;

import MIR.Value.User.Constant.Constant;
import Util.position;

public class UnaryExprNode extends ExprNode {
    public ExprNode object;
    public String op;
    public Constant addr;
    public UnaryExprNode(ExprNode obj, String op, position pos) {
        super(pos);
        this.object=obj;
        this.op=op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
