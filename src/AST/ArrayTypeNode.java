package AST;

import MIR.IRType.BaseType;
import MIR.IRType.PointerType;
import Util.position;

import java.util.Objects;

public class ArrayTypeNode extends TypeNode {
    public TypeNode type;
    public int size;
    public ArrayTypeNode(TypeNode baseType, position pos) {
        super(baseType.Typename, pos);
        if (baseType instanceof ArrayTypeNode) {
            this.size=1+ ((ArrayTypeNode) baseType).size;
        } else this.size=1;
        type = baseType;
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

    @Override
    public BaseType toIRType() {
        return new PointerType(type.toIRType());
    }
}
