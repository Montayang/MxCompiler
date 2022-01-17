package Backend;
import AST.*;
import MIR.*;
import MIR.Value.BasicBlock.BasicBlock;
import Util.*;
import Frontend.*;

import java.util.*;

public class IRBuilder implements ASTVisitor{
    public globalScope semantic_scope;
    public Scope curScp;
    public String curClass, curFunc;
    public BasicBlock curBlock;
    public Map<String, IRFunction> funcMap = new HashMap<>();

    public IRBuilder(globalScope scope1) {
        semantic_scope = scope1;
    }

    @Override
    public void visit(RootNode rootNode) {

    }

    @Override
    public void visit(ClassDefNode classDefNode) {

    }

    @Override
    public void visit(FuncDefNode funcDefNode) {

    }

    @Override
    public void visit(VarDefNode varDefNode) {

    }

    @Override
    public void visit(BlockStmtNode blockStmtNode) {

    }

    @Override
    public void visit(VardefStmtNode vardefStmtNode) {

    }

    @Override
    public void visit(IfStmtNode ifStmtNode) {

    }

    @Override
    public void visit(ForStmtNode forStmtNode) {

    }

    @Override
    public void visit(WhileStmtNode whileStmtNode) {

    }

    @Override
    public void visit(ReturnStmtNode returnStmtNode) {

    }

    @Override
    public void visit(ContinueStmtNode continueStmtNode) {

    }

    @Override
    public void visit(BreakStmtNode breakStmtNode) {

    }

    @Override
    public void visit(PureExprStmtNode pureExprStmtNode) {

    }

    @Override
    public void visit(IdExprNode idExprNode) {

    }

    @Override
    public void visit(ThisExprNode thisExprNode) {

    }

    @Override
    public void visit(NullConstExprNode nullConstExprNode) {

    }

    @Override
    public void visit(IntConstExprNode intConstExprNode) {

    }

    @Override
    public void visit(StringConstExprNode stringConstExprNode) {

    }

    @Override
    public void visit(BoolConstExprNode boolConstExprNode) {

    }

    @Override
    public void visit(NewExprNode newExprNode) {

    }

    @Override
    public void visit(MemberAccExprNode memberAccExprNode) {

    }

    @Override
    public void visit(FuncCallExprNode funcCallExprNode) {

    }

    @Override
    public void visit(ArrayAccExprNode arrayAccExprNode) {

    }

    @Override
    public void visit(SelfExprNode selfExprNode) {

    }

    @Override
    public void visit(UnaryExprNode unaryExprNode) {

    }

    @Override
    public void visit(BinaryExprNode binaryExprNode) {

    }

    @Override
    public void visit(AssignExprNode assignExprNode) {

    }

    @Override
    public void visit(LambdaExprNode lambdaExprNode) {

    }
}
