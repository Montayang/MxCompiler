package AST;

import MIR.Value.User.Constant.Constant;
import Util.position;

public class MemberAccExprNode extends ExprNode {
    public ExprNode object;
    public String name;
    public boolean ifFunc;
    public FuncDefNode theFunc;
    public Constant addr = null;
    public MemberAccExprNode(ExprNode obj, String name ,position pos) {
        super(pos);
        this.object=obj;
        this.name=name;
        ifFunc=false;
        theFunc=new FuncDefNode(new ClassTypeNode("int",new position(-1,-1)),"size",null,null,new position(-1,-1));
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
