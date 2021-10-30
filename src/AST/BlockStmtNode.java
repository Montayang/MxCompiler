package AST;

import Util.position;

import java.util.ArrayList;

public class BlockStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmtList;
    public BlockStmtNode(ArrayList<StmtNode> stmt,position pos) {
        super(pos);
        this.stmtList=stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}