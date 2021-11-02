package AST;

import Util.position;

public class ArrayTypeNode extends TypeNode {
    public int size;
    public ArrayTypeNode(TypeNode baseType, position pos) {
        super(baseType.Typename, pos);
        if (baseType instanceof ArrayTypeNode) {
            this.size=1+ ((ArrayTypeNode) baseType).size;
        } else this.size=1;
    }

    public ArrayTypeNode(String name,int size,position pos) {
        super(name, pos);
        this.size=size;
    }

    @Override
    public void accept(ASTVisitor visitor) {}
}
