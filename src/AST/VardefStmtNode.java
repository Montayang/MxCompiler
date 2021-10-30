package AST;

import Util.position;

import java.util.ArrayList;

public class VardefStmtNode extends StmtNode {
    public ArrayList<VarDefNode> elements;

    public VardefStmtNode(ArrayList<VarDefNode> ele, position pos) {
        super(pos);
        this.elements = ele;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
