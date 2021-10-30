package Frontend;

import AST.*;
import Parser.MxStarBaseVisitor;
import Parser.MxStarParser;
import Util.position;

import java.util.ArrayList;

public class ASTBuilder extends MxStarBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(MxStarParser.ProgramContext ctx) {
        ArrayList<ASTNode> elements = new ArrayList<>();
        for (MxStarParser.SubProgramContext ele : ctx.subProgram()) {
            if (ele.varDef() != null) elements.add(visit(ele.varDef()));
            if (ele.classDef() != null) elements.add(visit(ele.classDef()));
            if (ele.funcDef() != null) elements.add(visit(ele.funcDef()));
        }
        return new RootNode(elements, new position(ctx));
    }

    @Override
    public ASTNode visitSuite(MxStarParser.SuiteContext ctx) {
        ArrayList<StmtNode> stmtList=new ArrayList<>();
        if (ctx.statement() !=null) {
            for (MxStarParser.StatementContext ele : ctx.statement()) {
                if (ele instanceof MxStarParser.EmptyStmtContext) continue;
                stmtList.add((StmtNode)visit(ele));
            }
        } else stmtList=null;
        return new BlockStmtNode(stmtList, new position(ctx));
    }

    @Override
    public ASTNode visitVarDef(MxStarParser.VarDefContext ctx) {
        TypeNode varType= (TypeNode) visit(ctx.varType());
        ArrayList<VarDefNode> list = new ArrayList<>();
        for (MxStarParser.SingleInitContext ele : ctx.singleInit()) {
            String id = ele.IDENTIFIER().getText();
            ExprNode expr = null;
            if (ele.expression() != null) expr=(ExprNode) visit(ele.expression());
            list.add(new VarDefNode(varType,id,expr,new position(ele)));
        }
        return new VardefStmtNode(list, new position(ctx));
    }

    @Override
    public ASTNode visitClassDef(MxStarParser.ClassDefContext ctx) {
        String name=ctx.CLASS().getText();
        ArrayList<VardefStmtNode> varMem=new ArrayList<>();
        ArrayList<FuncDefNode> funcMem=new ArrayList<>();
        if (ctx.varDef() != null) {
            for (MxStarParser.VarDefContext ele : ctx.varDef()) {
                varMem.add((VardefStmtNode) visit(ele));
            }
        } else varMem=null;
        if (ctx.funcDef() != null) {
            for (MxStarParser.FuncDefContext ele : ctx.funcDef()) {
                funcMem.add((FuncDefNode) visit(ele));
            }
        } else funcMem=null;
        return new ClassDefNode(name,varMem,funcMem, new position(ctx));
    }

    @Override
    public ASTNode visitFuncDef(MxStarParser.FuncDefContext ctx) {
        TypeNode type=(TypeNode) visit(ctx.funcType());
        String name=ctx.IDENTIFIER().getText();
        ArrayList<VarDefNode> parList= new ArrayList<>();
        BlockStmtNode funcBody=(BlockStmtNode) visit(ctx.suite());
        if (ctx.parList() != null) {
            for (MxStarParser.SingleVarDefContext ele : ctx.parList().singleVarDef()) {
                parList.add((VarDefNode) visit(ele));
            }
        } else parList=null;
        return new FuncDefNode(type,name,parList,funcBody,new position(ctx));
    }

    @Override
    public ASTNode visitFuncType(MxStarParser.FuncTypeContext ctx) {

    }

    @Override
    public ASTNode visitVarType(MxStarParser.VarTypeContext ctx) {

    }

    @Override
    public ASTNode visitBaseType(MxStarParser.BaseTypeContext ctx) {

    }

    @Override
    public ASTNode visitParList(MxStarParser.ParListContext ctx) {

    }

    @Override
    public ASTNode visitBlock(MxStarParser.BlockContext ctx) {

    }

    @Override
    public ASTNode visitVardefStmt(MxStarParser.VardefStmtContext ctx) {

    }

    @Override
    public ASTNode visitIfStmt(MxStarParser.IfStmtContext ctx) {

    }

    @Override
    public ASTNode visitForStmt(MxStarParser.ForStmtContext ctx){

    }

    @Override
    public ASTNode visitWhileStmt(MxStarParser.WhileStmtContext ctx) {

    }

    @Override
    public ASTNode visitReturnStmt(MxStarParser.ReturnStmtContext ctx) {

    }

    @Override
    public ASTNode visitContinueStmt(MxStarParser.ContinueStmtContext ctx) {

    }

    @Override
    public ASTNode visitBreakStmt(MxStarParser.BreakStmtContext ctx) {

    }

    @Override
    public ASTNode visitPureExprStmt(MxStarParser.PureExprStmtContext ctx) {

    }

    @Override
    public ASTNode visitEmptyStmt(MxStarParser.EmptyStmtContext ctx) {

    }

    @Override
    public ASTNode visitNewExpr(MxStarParser.NewExprContext ctx) {

    }

    @Override
    public ASTNode visitMemberAccExpr(MxStarParser.MemberAccExprContext ctx) {

    }

    @Override
    public ASTNode visitThisExpr(MxStarParser.ThisExprContext ctx) {

    }

    @Override
    public ASTNode visitSelfExpr(MxStarParser.SelfExprContext ctx) {

    }

    @Override
    public ASTNode visitBinaryExpr(MxStarParser.BinaryExprContext ctx) {
        
    }

    @Override
    public ASTNode visitFuncCallExpr(MxStarParser.FuncCallExprContext ctx) {

    }

    @Override
    public ASTNode visitSubExpr(MxStarParser.SubExprContext ctx) {

    }

    @Override
    public ASTNode visitArrayAccExpr(MxStarParser.ArrayAccExprContext ctx) {

    }

    @Override
    public ASTNode visitUnaryExpr(MxStarParser.UnaryExprContext ctx) {

    }

    @Override
    public ASTNode visitLambdaExpr(MxStarParser.LambdaExprContext ctx) {

    }

    @Override
    public ASTNode visitAssignExpr(MxStarParser.AssignExprContext ctx) {

    }

    @Override
    public ASTNode visitIdExpr(MxStarParser.IdExprContext ctx) {

    }

    @Override
    public ASTNode visitConstExpr(MxStarParser.ConstExprContext ctx) {

    }

    @Override
    public ASTNode visitNewErrorType(MxStarParser.NewErrorTypeContext ctx) {

    }

    @Override
    public ASTNode visitNewArrayType(MxStarParser.NewArrayTypeContext ctx) {

    }

    @Override
    public ASTNode visitNewBaseType(MxStarParser.NewBaseTypeContext ctx) {

    }

    @Override
    public ASTNode visitExprList(MxStarParser.ExprListContext ctx) {

    }

    @Override
    public ASTNode visitLiteral(MxStarParser.LiteralContext ctx) {

    }
}
