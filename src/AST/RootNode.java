package AST;

import Util.position;
import java.util.ArrayList;

public class RootNode extends ASTNode {
    public ArrayList<ASTNode> elements;

    public RootNode(ArrayList<ASTNode> ele, position pos) {
        super(pos);
        this.elements = ele;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
