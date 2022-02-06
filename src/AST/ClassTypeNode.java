package AST;

import MIR.IRType.BaseType;
import MIR.IRType.PointerType;
import MIR.IRType.StructType;
import Util.position;

import java.util.ArrayList;
import java.util.Objects;

public class ClassTypeNode extends TypeNode {
    public ClassTypeNode(String name, position pos) {
        super(name, pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {}

    public boolean equals(Object object) {
        if (!(object instanceof ClassTypeNode)) return false;
        return Objects.equals(Typename, ((TypeNode) object).Typename);
    }

    @Override
    public BaseType toIRType() {
        if (Typename == null || Typename.equals("void")) return new BaseType("void");
        else if (Typename.equals("int")) return new BaseType("i32");
        else if (Typename.equals("bool")) return new BaseType("i1");
        else if (Typename.equals("string")) return new PointerType(new BaseType("i8"));
        else return null;
    }
}
