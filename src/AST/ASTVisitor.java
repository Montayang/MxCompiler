package AST;

public interface ASTVisitor {

    void visit(RootNode rootNode);
    void visit(ClassDefNode classDefNode);
    void visit(FuncDefNode funcDefNode);
    void visit(VarDefNode varDefNode);

    void visit(BlockStmtNode blockStmtNode);
    void visit(VardefStmtNode vardefStmtNode);
    void visit(IfStmtNode ifStmtNode);
    void visit(ForStmtNode forStmtNode);
    void visit(WhileStmtNode whileStmtNode);
    void visit(ReturnStmtNode returnStmtNode);
    void visit(ContinueStmtNode continueStmtNode);
    void visit(BreakStmtNode breakStmtNode);
    void visit(PureExprStmtNode pureExprStmtNode);

    void visit(IdExprNode idExprNode);
    void visit(ThisExprNode thisExprNode);
    void visit(NullConstExprNode nullConstExprNode);
    void visit(IntConstExprNode intConstExprNode);
    void visit(StringConstExprNode stringConstExprNode);
    void visit(BoolConstExprNode boolConstExprNode);
    void visit(NewExprNode newExprNode);
    void visit(MemberAccExprNode memberAccExprNode);
    void visit(FuncCallExprNode funcCallExprNode);
    void visit(ArrayAccExprNode arrayAccExprNode);
    void visit(SelfExprNode selfExprNode);
    void visit(UnaryExprNode unaryExprNode);
    void visit(BinaryExprNode binaryExprNode);
    void visit(AssignExprNode assignExprNode);
    void visit(LambdaExprNode lambdaExprNode);
}