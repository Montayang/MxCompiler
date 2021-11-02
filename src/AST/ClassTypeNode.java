package AST;

import Util.position;

public class ClassTypeNode extends TypeNode {
    public ClassTypeNode(String name, position pos) {
        super(name, pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {}
}
