package AST;

import Util.position;

public class MemberAccExprNode extends ExprNode {
    public ExprNode object;
    public String name;
    public MemberAccExprNode(ExprNode obj, String name ,position pos) {
        super(pos);
        this.object=obj;
        this.name=name;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
