package AST;

import Util.position;

abstract public class TypeNode extends ASTNode {
    public String Typename;

    public TypeNode(String name,position pos) {
        super(pos);
        this.Typename=name;
    }

    abstract public boolean equals(Object object);
}
