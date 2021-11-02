package AST;

import Util.position;

import java.util.ArrayList;

public class NewExprNode extends ExprNode {
    public TypeNode newType;
    public int size;
    public ArrayList<ExprNode> sizeList;
    public NewExprNode(TypeNode type, int size,ArrayList<ExprNode> sizeList, position pos) {
        super(pos);
        this.newType = type;
        this.size = size;
        this.sizeList=sizeList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}