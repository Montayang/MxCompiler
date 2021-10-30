package AST;

import Util.position;

import java.util.Objects;

abstract public class TypeNode extends ASTNode {
    public String Typename;

    public TypeNode(String name,position pos) {
        super(pos);
        this.Typename=name;
    }

    public boolean equals(Object object) {
        if (object instanceof TypeNode) return Objects.equals(Typename, ((TypeNode) object).Typename);
        return false;
    }

}
