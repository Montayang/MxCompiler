package AST;

import MIR.IRType.BaseType;
import Util.position;

import java.util.Objects;

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

    public boolean equals(Object object) {
        if (!(object instanceof ArrayTypeNode)) return false;
        return Objects.equals(Typename, ((TypeNode) object).Typename) && (this.size==((ArrayTypeNode) object).size);
    }
}
