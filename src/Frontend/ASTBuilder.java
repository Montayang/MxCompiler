package Frontend;

import AST.*;
import Parser.MxStarBaseVisitor;
import Parser.MxStarParser;
import Util.error.syntaxError;
import Util.position;

import java.util.ArrayList;
import java.util.Objects;

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
        String name=ctx.IDENTIFIER().getText();
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
        TypeNode type=ctx.funcType()==null ? null : (TypeNode) visit(ctx.funcType());
        String name=ctx.IDENTIFIER().getText();
        ArrayList<VarDefNode> parList= new ArrayList<>();
        if (ctx.parList() != null) {
            for (MxStarParser.SingleVarDefContext ele : ctx.parList().singleVarDef()) {
                String id = ele.IDENTIFIER().getText();
                TypeNode tmpType = (TypeNode) visit(ele.varType());
                parList.add(new VarDefNode(tmpType,id, null,new position(ele)));
            }
        } else parList=null;
        BlockStmtNode funcBody=(BlockStmtNode) visit(ctx.suite());
        return new FuncDefNode(type,name,parList,funcBody,new position(ctx));
    }

    @Override
    public ASTNode visitFuncType(MxStarParser.FuncTypeContext ctx) {
        if (ctx.VOID() == null) {
            return visit(ctx.varType());
        }
        return new ClassTypeNode("void",new position(ctx));
    }

    @Override
    public ASTNode visitArrayType(MxStarParser.ArrayTypeContext ctx) {
        TypeNode varType=(TypeNode) visit(ctx.varType());
        return new ArrayTypeNode(varType,new position(ctx));
    }

    @Override
    public ASTNode visitBaseVarType(MxStarParser.BaseVarTypeContext ctx) {
        return visit(ctx.baseType());
    }

    @Override
    public ASTNode visitBaseType(MxStarParser.BaseTypeContext ctx) {
        return new ClassTypeNode(ctx.getText(),new position(ctx));
    }

    @Override
    public ASTNode visitBlock(MxStarParser.BlockContext ctx) {
        return visit(ctx.suite());
    }

    @Override
    public ASTNode visitVardefStmt(MxStarParser.VardefStmtContext ctx) {
        return visit(ctx.varDef());
    }

    @Override
    public ASTNode visitIfStmt(MxStarParser.IfStmtContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt=ctx.thenStmt==null? null:(StmtNode) visit(ctx.thenStmt);
        StmtNode elseStmt=ctx.elseStmt==null? null:(StmtNode) visit(ctx.elseStmt);
        return new IfStmtNode(expr,thenStmt,elseStmt,new position(ctx));
    }

    @Override
    public ASTNode visitForStmt(MxStarParser.ForStmtContext ctx){
        StmtNode init =null;
        if (ctx.initDef != null) init=(VardefStmtNode) visit(ctx.initDef);
        else if (ctx.initExpr != null) init = new PureExprStmtNode((ExprNode) visit(ctx.initExpr),new position(ctx.initExpr));
        ExprNode cond = ctx.condExpr==null? null : (ExprNode) visit(ctx.condExpr);
        ExprNode step = ctx.stepExpr==null? null : (ExprNode) visit(ctx.stepExpr);
        StmtNode body=(StmtNode) visit(ctx.statement());
        return new ForStmtNode(init,cond,step,body,new position(ctx));
    }

    @Override
    public ASTNode visitWhileStmt(MxStarParser.WhileStmtContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        StmtNode body=(StmtNode) visit(ctx.statement());
        return new WhileStmtNode(expr,body,new position(ctx));
    }

    @Override
    public ASTNode visitReturnStmt(MxStarParser.ReturnStmtContext ctx) {
        if (ctx.expression() ==  null) return new ReturnStmtNode(null, new position(ctx));
        return new ReturnStmtNode((ExprNode) visit(ctx.expression()),new position(ctx));
    }

    @Override
    public ASTNode visitContinueStmt(MxStarParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(new position(ctx));
    }

    @Override
    public ASTNode visitBreakStmt(MxStarParser.BreakStmtContext ctx) {
        return new BreakStmtNode(new position(ctx));
    }

    @Override
    public ASTNode visitPureExprStmt(MxStarParser.PureExprStmtContext ctx) {
        return new PureExprStmtNode((ExprNode) visit(ctx.expression()),new position(ctx));
    }

    @Override
    public ASTNode visitNewExpr(MxStarParser.NewExprContext ctx) {
        return visit(ctx.newType());
    }

    @Override
    public ASTNode visitMemberAccExpr(MxStarParser.MemberAccExprContext ctx) {
        ExprNode obj = (ExprNode) visit(ctx.expression());
        String mem=ctx.IDENTIFIER().getText();
        return new MemberAccExprNode(obj,mem,new position(ctx));
    }

    @Override
    public ASTNode visitThisExpr(MxStarParser.ThisExprContext ctx) {
        return new ThisExprNode(new position(ctx));
    }

    @Override
    public ASTNode visitSelfExpr(MxStarParser.SelfExprContext ctx) {
        ExprNode obj = (ExprNode) visit(ctx.expression());
        String op=ctx.op.getText();
        return new SelfExprNode(obj,op,new position(ctx));
    }

    @Override
    public ASTNode visitBinaryExpr(MxStarParser.BinaryExprContext ctx) {
        ExprNode exprL=(ExprNode) visit(ctx.exprL), exprR=(ExprNode) visit(ctx.exprR);
        String op=ctx.op.getText();
        return new BinaryExprNode(exprL,exprR,op,new position(ctx));
    }

    @Override
    public ASTNode visitFuncCallExpr(MxStarParser.FuncCallExprContext ctx) {
        ExprNode expr=(ExprNode) visit(ctx.expression());
        ArrayList<ExprNode> list= new ArrayList<>();
        if (ctx.exprList()!=null) {
            for (MxStarParser.ExpressionContext ele : ctx.exprList().expression()) {
                list.add((ExprNode)visit(ele));
            }
        } else list=null;
        return new FuncCallExprNode(expr,list,new position(ctx));
    }

    @Override
    public ASTNode visitSubExpr(MxStarParser.SubExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitArrayAccExpr(MxStarParser.ArrayAccExprContext ctx) {
        ExprNode array=(ExprNode) visit(ctx.array), index=(ExprNode) visit(ctx.index);
        return new ArrayAccExprNode(array,index,new position(ctx));
    }

    @Override
    public ASTNode visitUnaryExpr(MxStarParser.UnaryExprContext ctx) {
        ExprNode obj=(ExprNode) visit(ctx.expression());
        String op=ctx.op.getText();
        return new UnaryExprNode(obj,op,new position(ctx));
    }

    @Override
    public ASTNode visitLambdaExpr(MxStarParser.LambdaExprContext ctx) {
        ArrayList<VarDefNode> parList=new ArrayList<>();
        ArrayList<ExprNode> aryList=new ArrayList<>();
        if (ctx.parList()!=null) {
            for (MxStarParser.SingleVarDefContext ele : ctx.parList().singleVarDef()) {
                parList.add((VarDefNode)visit(ele));
            }
        } else parList=null;
        if (ctx.expression()!=null) {
            for (MxStarParser.ExpressionContext ele : ctx.expression()) {
                aryList.add((ExprNode)visit(ele));
            }
        } else aryList=null;
        return new LambdaExprNode(parList,(BlockStmtNode) visit(ctx.suite()),aryList,new position(ctx));
    }

    @Override
    public ASTNode visitAssignExpr(MxStarParser.AssignExprContext ctx) {
        ExprNode exprL=(ExprNode) visit(ctx.exprL), exprR=(ExprNode) visit(ctx.exprR);
        return new AssignExprNode(exprL,exprR,new position(ctx));
    }

    @Override
    public ASTNode visitIdExpr(MxStarParser.IdExprContext ctx) {
        return new IdExprNode(ctx.getText(),new position(ctx));
    }

    @Override
    public ASTNode visitConstExpr(MxStarParser.ConstExprContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public ASTNode visitNewErrorType(MxStarParser.NewErrorTypeContext ctx) {
        throw new syntaxError("Wrong Syntax for alloc",new position(ctx));
    }

    @Override
    public ASTNode visitNewArrayType(MxStarParser.NewArrayTypeContext ctx) {
        TypeNode baseType=(TypeNode) visit(ctx.baseType());
        ArrayList<ExprNode> size = new ArrayList<>();
        for (MxStarParser.ExpressionContext ele : ctx.expression()) {
            size.add((ExprNode)visit(ele));
        }
        return new NewExprNode(baseType,(ctx.getChildCount()-size.size()-1)/2,size,new position(ctx));
    }

    @Override
    public ASTNode visitNewBaseType(MxStarParser.NewBaseTypeContext ctx) {
        TypeNode baseType=(TypeNode) visit(ctx.baseType());
        return new NewExprNode(baseType,0,null,new position(ctx));
    }

    @Override
    public ASTNode visitLiteral(MxStarParser.LiteralContext ctx) {
        if (ctx.NULL_CONST() != null) {
            return new NullConstExprNode(new position(ctx));
        } else if (ctx.INTERGER_CONST() != null) {
            return new IntConstExprNode(Integer.parseInt(ctx.getText()),new position(ctx));
        } else if (ctx.STRING_CONST() != null) {
            return new StringConstExprNode(ctx.getText(),new position(ctx));
        } else if (ctx.BOOL_CONST() != null) {
            boolean value= Objects.equals(ctx.getText(), "true");
            return new BoolConstExprNode(value,new position(ctx));
        }
        throw new syntaxError("Invalid constant",new position(ctx));
    }
}
