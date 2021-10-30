package AST;

import Util.position;

public class ArrayTypeNode extends TypeNode {
    public int size;
    public ArrayTypeNode(String name, position pos) {
        super(name, pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
