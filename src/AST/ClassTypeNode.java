package AST;

import Util.position;

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
}
