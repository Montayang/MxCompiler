package AST;

import Util.position;
import MIR.IRType.*;

abstract public class TypeNode extends ASTNode {
    public String Typename;

    public TypeNode(String name,position pos) {
        super(pos);
        this.Typename=name;
    }

    abstract public boolean equals(Object object);

    public BaseType toIRType() {
        if (Typename == null) return new BaseType("void");
        else if (Typename.equals("int")) return new BaseType("i32");
        else if (Typename.equals("bool")) return new BaseType("i1");
        else if (Typename.equals("string")) return new PointerType(new BaseType("i8"));
        else return new BaseType(Typename);
    }
}
