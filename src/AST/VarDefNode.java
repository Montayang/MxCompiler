package AST;

import Util.position;

public class VarDefNode extends ASTNode{
    public TypeNode varType;
    public String varName;
    public ExprNode initValue;

    public VarDefNode(TypeNode type,String name,ExprNode init,position pos) {
        super(pos);
        this.varType=type;
        this.varName=name;
        this.initValue=init;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
