package AST;

import Util.position;

import java.util.Objects;

abstract public class TypeNode extends ASTNode {
    public String Typename;

    public TypeNode(String name,position pos) {
        super(pos);
        this.Typename=name;
    }

    abstract public boolean equals(Object object);
}
