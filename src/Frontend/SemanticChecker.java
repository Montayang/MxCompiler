package Frontend;

import AST.*;
import Util.*;
import Util.error.*;

import javax.lang.model.type.NullType;
import javax.swing.undo.UndoableEdit;
import java.util.Objects;
import java.util.Stack;

public class SemanticChecker implements ASTVisitor {
    public globalScope globalScp;
    public Scope curScp;
    public String curClass;
    public Stack<ASTNode> funcInDef;
    public int inLoop;

    public SemanticChecker(globalScope glbScp) {
        this.globalScp = glbScp;
        this.curScp = glbScp;
        this.curClass = null;
        funcInDef = new Stack<>();
        this.inLoop = 0;
    }

    @Override
    public void visit(RootNode rootNode) {
        for (ASTNode node : rootNode.elements) {
            node.accept(this);
        }
    }

    @Override
    public void visit(ClassDefNode classDefNode) {
        curClass = classDefNode.className;
        curScp = globalScp.classTable.get(curClass);
        for (VardefStmtNode line : classDefNode.varMem) line.accept(this);
        for (FuncDefNode funcDef : classDefNode.funcMem) funcDef.accept(this);
        curClass = null;
        curScp = curScp.parent;
    }

    @Override
    public void visit(FuncDefNode funcDefNode) {
        curScp = new Scope(curScp);
        funcInDef.push(funcDefNode);
        if (funcDefNode.funcType != null && !Objects.equals(funcDefNode.funcType.Typename, "void") && !globalScp.containClass(funcDefNode.funcType.Typename))
            throw new semanticError("Undefined function return-type " + funcDefNode.funcType.Typename, funcDefNode.pos);
        if (funcDefNode.parList != null && !funcDefNode.parList.isEmpty()) {
            for (VarDefNode ele : funcDefNode.parList) ele.accept(this);
        }
        funcDefNode.funcBody.accept(this);
        if (funcDefNode.funcType != null && !Objects.equals(funcDefNode.funcType.Typename, "void") && !funcDefNode.funcName.equals("main") && !funcDefNode.hasReturn)
            throw new semanticError("Lack of return statement in " + funcDefNode.funcName, funcDefNode.pos);
        curScp = curScp.parent;
        funcInDef.pop();
    }

    @Override
    public void visit(VarDefNode varDefNode) {
        if (!(curClass != null && funcInDef.empty()) && curScp.containVar(varDefNode.varName))
            throw new semanticError("Duplicate variable declaration " + varDefNode.varName, varDefNode.pos);
        if (!globalScp.containClass(varDefNode.varType.Typename))
            throw new semanticError("Undefined class  " + varDefNode.varType.Typename, varDefNode.pos);
        if (varDefNode.initValue != null) {
            varDefNode.initValue.accept(this);
            if (varDefNode.initValue.exprType.Typename != null && !varDefNode.initValue.exprType.equals(varDefNode.varType))
                throw new semanticError("Mismatched class type", varDefNode.pos);
        }
        curScp.defVar(varDefNode.varName, varDefNode.varType);
    }

    @Override
    public void visit(BlockStmtNode blockStmtNode) {
        curScp = new Scope(curScp);
        if (blockStmtNode.stmtList != null) {
            for (StmtNode ele : blockStmtNode.stmtList) ele.accept(this);
        }
        curScp = curScp.parent;
    }

    @Override
    public void visit(VardefStmtNode vardefStmtNode) {
        for (VarDefNode ele : vardefStmtNode.elements) ele.accept(this);
    }

    @Override
    public void visit(IfStmtNode ifStmtNode) {
        ifStmtNode.ifExpr.accept(this);
        if (ifStmtNode.ifExpr == null) throw new semanticError("Condition of IF is null", ifStmtNode.pos);
        if (!Objects.equals(ifStmtNode.ifExpr.exprType.Typename, "bool"))
            throw new semanticError("Condition of IF is not bool-type", ifStmtNode.pos);
        if (ifStmtNode.thenStmt != null) {
            curScp = new Scope(curScp);
            ifStmtNode.thenStmt.accept(this);
            curScp = curScp.parent;
        }
        if (ifStmtNode.elseStmt != null) {
            curScp = new Scope(curScp);
            ifStmtNode.elseStmt.accept(this);
            curScp = curScp.parent;
        }
    }

    @Override
    public void visit(ForStmtNode forStmtNode) {
        if (forStmtNode.initExpr != null) {
            forStmtNode.initExpr.accept(this);
            if (!(forStmtNode.initExpr instanceof VardefStmtNode) && !(forStmtNode.initExpr instanceof PureExprStmtNode))
                throw new semanticError("Statement in for-loop is invalid", forStmtNode.pos);
        }
        if (forStmtNode.condExpr != null) {
            forStmtNode.condExpr.accept(this);
            if (!Objects.equals(forStmtNode.condExpr.exprType.Typename, "bool"))
                throw new semanticError("Condition in for-loop is not bool-type", forStmtNode.pos);
        }
        if (forStmtNode.stepExpr != null) forStmtNode.stepExpr.accept(this);
        this.inLoop++;
        curScp = new Scope(curScp);
        if (forStmtNode.forBody != null) forStmtNode.forBody.accept(this);
        this.inLoop--;
        curScp = curScp.parent;
    }

    @Override
    public void visit(WhileStmtNode whileStmtNode) {
        if (whileStmtNode.whileExpr == null)
            throw new semanticError("Condition of while-loop is null", whileStmtNode.pos);
        whileStmtNode.whileExpr.accept(this);
        if (!Objects.equals(whileStmtNode.whileExpr.exprType.Typename, "bool"))
            throw new semanticError("Condition in while-loop is not bool-type", whileStmtNode.pos);
        if (whileStmtNode.whileBody != null) {
            curScp = new Scope(curScp);
            this.inLoop++;
            whileStmtNode.whileBody.accept(this);
            curScp = curScp.parent;
            this.inLoop--;
        }
    }

    @Override
    public void visit(ReturnStmtNode returnStmtNode) {
        if (funcInDef.empty()) throw new semanticError("Return statement is not in function",returnStmtNode.pos);
        if (funcInDef.peek() instanceof FuncDefNode) {
            FuncDefNode curFunc = (FuncDefNode) funcInDef.peek();
            if (returnStmtNode.returnExpr!=null && !(returnStmtNode.returnExpr instanceof NullConstExprNode)) {
                returnStmtNode.returnExpr.accept(this);
                if (!returnStmtNode.returnExpr.exprType.equals(curFunc.funcType))
                    throw new semanticError("Wrong return-type in "+curFunc.funcName,returnStmtNode.pos);
            } else if (curFunc.funcType != null && (Objects.equals(curFunc.funcType.Typename, "int") || Objects.equals(curFunc.funcType.Typename, "bool") || Objects.equals(curFunc.funcType.Typename, "string")))
                throw new semanticError("Wrong return-type in "+curFunc.funcName,returnStmtNode.pos);
            curFunc.hasReturn=true;
        } else {
            LambdaExprNode curFunc = (LambdaExprNode) funcInDef.peek();
            if (returnStmtNode.returnExpr == null) throw new semanticError("No return statement in lambda", returnStmtNode.pos);
            returnStmtNode.returnExpr.accept(this);
            if (curFunc.returnType == null) curFunc.returnType=returnStmtNode.returnExpr.exprType;
            else if (!curFunc.returnType.equals(returnStmtNode.returnExpr.exprType)) throw new semanticError("Different return-type in lambda",returnStmtNode.pos);
        }
    }

    @Override
    public void visit(ContinueStmtNode continueStmtNode) {
        if (this.inLoop==0) throw new semanticError("Continue statement is not in loop", continueStmtNode.pos);
    }

    @Override
    public void visit(BreakStmtNode breakStmtNode) {
        if (this.inLoop==0) throw new semanticError("Break statement is not in loop", breakStmtNode.pos);
    }

    @Override
    public void visit(PureExprStmtNode pureExprStmtNode) {
        pureExprStmtNode.expr.accept(this);
    }

    @Override
    public void visit(IdExprNode idExprNode) {
        if (curScp.fetchVarType(idExprNode.name) == null)
            throw new semanticError("Undefined variable " + idExprNode.name, idExprNode.pos);
        idExprNode.exprType = curScp.fetchVarType(idExprNode.name);
        idExprNode.isAssignable = true;
    }

    @Override
    public void visit(ThisExprNode thisExprNode) {
        if (this.curClass == null) throw new semanticError("Pointer does not has object", thisExprNode.pos);
        thisExprNode.exprType = new ClassTypeNode(curClass, thisExprNode.pos);
    }

    @Override
    public void visit(NullConstExprNode nullConstExprNode) {}

    @Override
    public void visit(IntConstExprNode intConstExprNode) {}

    @Override
    public void visit(StringConstExprNode stringConstExprNode) {}

    @Override
    public void visit(BoolConstExprNode boolConstExprNode) {}

    @Override
    public void visit(NewExprNode newExprNode) {
        if (!this.globalScp.containClass(newExprNode.newType.Typename))
            throw new semanticError("Undefined type"+newExprNode.newType.Typename, newExprNode.pos);
        if (newExprNode.size!=0) {
            if (newExprNode.sizeList != null) {
                for (ExprNode ele : newExprNode.sizeList) {
                    ele.accept(this);
                    if (!Objects.equals(ele.exprType.Typename, "int"))
                        throw new semanticError("Wrong array size", newExprNode.pos);
                }
            }
            newExprNode.exprType=new ArrayTypeNode(newExprNode.newType.Typename,newExprNode.size,newExprNode.pos);
        } else newExprNode.exprType=new ClassTypeNode(newExprNode.newType.Typename, newExprNode.pos);

    }

    @Override
    public void visit(MemberAccExprNode memberAccExprNode) {
        memberAccExprNode.object.accept(this);
        if (memberAccExprNode.object.exprType instanceof ArrayTypeNode) {
            if (!memberAccExprNode.ifFunc || !Objects.equals(memberAccExprNode.name, "size"))
                throw new semanticError("Wrong member access", memberAccExprNode.pos);
        } else {
            if (!memberAccExprNode.ifFunc) {
                if (!globalScp.classTable.get(memberAccExprNode.object.exprType.Typename).varTable.containsKey(memberAccExprNode.name))
                    throw new semanticError("Class has no variable called", memberAccExprNode.pos);
                memberAccExprNode.exprType=globalScp.classTable.get(memberAccExprNode.object.exprType.Typename).varTable.get(memberAccExprNode.name);
                memberAccExprNode.isAssignable=true;
            } else {
                if (!globalScp.classTable.get(memberAccExprNode.object.exprType.Typename).funcTable.containsKey(memberAccExprNode.name))
                    throw new semanticError("Class has no function called", memberAccExprNode.pos);
                memberAccExprNode.theFunc=globalScp.classTable.get(memberAccExprNode.object.exprType.Typename).fetchFunc(memberAccExprNode.name);
            }
        }
    }

    @Override
    public void visit(FuncCallExprNode funcCallExprNode) {
        FuncDefNode base = null;
        if (funcCallExprNode.func instanceof MemberAccExprNode) {
            ((MemberAccExprNode) funcCallExprNode.func).ifFunc = true;
            funcCallExprNode.func.accept(this);
            base = ((MemberAccExprNode) funcCallExprNode.func).theFunc;
        } else {
            String name = ((IdExprNode)funcCallExprNode.func).name;
            if (curClass == null) {
                if (!globalScp.funcTable.containsKey(name))
                    throw new semanticError("Undefined function", funcCallExprNode.pos);
                base=globalScp.fetchFunc(name);
            } else {
                if (globalScp.classTable.get(curClass).funcTable.containsKey(name))
                    base=globalScp.classTable.get(curClass).fetchFunc(name);
                else {
                    if (!globalScp.funcTable.containsKey(name))
                        throw new semanticError("Undefined function", funcCallExprNode.pos);
                    base=globalScp.fetchFunc(name);
                }
            }
        }
        if (funcCallExprNode.aryList!= null) {
            for (ExprNode ele : funcCallExprNode.aryList) ele.accept(this);
        }
        if ((funcCallExprNode.aryList==null) != (base.parList == null)) {
            throw new semanticError("Undefined function", funcCallExprNode.pos);
        }
        if (funcCallExprNode.aryList != null) {
            if (funcCallExprNode.aryList.size() != base.parList.size()) throw new semanticError("Undefined function", funcCallExprNode.pos);
            for (int i = 0;i<funcCallExprNode.aryList.size();i++) {
                if (!base.parList.get(i).varType.equals(funcCallExprNode.aryList.get(i).exprType) && funcCallExprNode.aryList.get(i).exprType.Typename != null)
                    throw new semanticError("Undefined function", funcCallExprNode.pos);
            }
        }
        funcCallExprNode.exprType = base.funcType;
    }

    @Override
    public void visit(ArrayAccExprNode arrayAccExprNode) {
        arrayAccExprNode.array.accept(this);
        if (!(arrayAccExprNode.array.exprType instanceof ArrayTypeNode)) throw new semanticError("It is not array", arrayAccExprNode.pos);
        arrayAccExprNode.index.accept(this);
        if (!(arrayAccExprNode.index.exprType instanceof ClassTypeNode) || !Objects.equals(arrayAccExprNode.index.exprType.Typename, "int")) throw new semanticError("Index is not int", arrayAccExprNode.pos);
        if (((ArrayTypeNode) arrayAccExprNode.array.exprType).size == 1)
            arrayAccExprNode.exprType = new ClassTypeNode(arrayAccExprNode.array.exprType.Typename, arrayAccExprNode.pos);
        else arrayAccExprNode.exprType = new ArrayTypeNode(arrayAccExprNode.array.exprType.Typename, ((ArrayTypeNode) arrayAccExprNode.array.exprType).size-1,arrayAccExprNode.pos);
        arrayAccExprNode.isAssignable=true;
    }

    @Override
    public void visit(SelfExprNode selfExprNode) {
        if(!selfExprNode.object.isAssignable) throw new semanticError("Right value can't operate",selfExprNode.pos);
        selfExprNode.object.accept(this);
        if (!Objects.equals(selfExprNode.object.exprType.Typename, "int"))
            throw new semanticError("Wrong type when operate1", selfExprNode.pos);
        selfExprNode.exprType = selfExprNode.object.exprType;
    }

    @Override
    public void visit(UnaryExprNode unaryExprNode) {
        unaryExprNode.object.accept(this);
        if((unaryExprNode.op.equals("--") || unaryExprNode.op.equals("++")) && !unaryExprNode.object.isAssignable)
            throw new semanticError("Right value can't operate",unaryExprNode.pos);
        switch (unaryExprNode.op) {
            case "++","--","~","+","-"->{
                if (!Objects.equals(unaryExprNode.object.exprType.Typename, "int"))
                    throw new semanticError("Wrong type when operate2", unaryExprNode.pos);
            }
            case "!"->{
                if (!Objects.equals(unaryExprNode.object.exprType.Typename, "bool"))
                    throw new semanticError("Wrong type when operate3", unaryExprNode.pos);
            }
        }
        if (unaryExprNode.op.equals("--") || unaryExprNode.op.equals("++")) unaryExprNode.isAssignable=true;
        unaryExprNode.exprType = unaryExprNode.object.exprType;
    }

    @Override
    public void visit(BinaryExprNode binaryExprNode) {
        String op=binaryExprNode.op;
        ExprNode exprL=binaryExprNode.exprL;
        ExprNode exprR=binaryExprNode.exprR;
        exprL.accept(this);
        exprR.accept(this);
        if (!exprL.exprType.equals(exprR.exprType) && !Objects.equals(op, "==") && !Objects.equals(op, "!="))
            throw new semanticError("Type does not matched in binary op", binaryExprNode.pos);
        TypeNode type=exprL.exprType;
        binaryExprNode.exprType=new ClassTypeNode(type.Typename, binaryExprNode.pos);
        switch (op) {
            case "+","<",">",">=","<=" -> {
                if (!Objects.equals(type.Typename, "int") && !Objects.equals(type.Typename, "string"))
                    throw new semanticError("Wrong type1", binaryExprNode.pos);
                if (!op.equals("+")) binaryExprNode.exprType.Typename="bool";
            }
            case "-","*","/","%",">>","<<","&","|","^" -> {
                if (!Objects.equals(type.Typename, "int"))
                    throw new semanticError("Wrong type2", binaryExprNode.pos);
            }
            case "&&","||" -> {
                if (!Objects.equals(type.Typename, "bool"))
                    throw new semanticError("Wrong type3", binaryExprNode.pos);
                binaryExprNode.exprType.Typename="bool";
            }
            case "==","!=" -> {
                if (!exprL.exprType.equals(exprR.exprType) && exprR.exprType.Typename!=null)
                    throw new semanticError("Type does not matched in == or !=", binaryExprNode.pos);
                binaryExprNode.exprType.Typename="bool";
            }
        }
    }

    @Override
    public void visit(AssignExprNode assignExprNode) {
        assignExprNode.exprL.accept(this);
        assignExprNode.exprR.accept(this);
        if (!assignExprNode.exprL.isAssignable) throw new semanticError("Left value is required", assignExprNode.pos);
        if (assignExprNode.exprR.exprType!=null && assignExprNode.exprR.exprType.Typename!=null && !assignExprNode.exprL.exprType.equals(assignExprNode.exprR.exprType))
            throw new semanticError("Type does not matched", assignExprNode.pos);
        if (assignExprNode.exprR.exprType!=null && assignExprNode.exprR.exprType.Typename==null && assignExprNode.exprL.exprType instanceof ClassTypeNode &&((Objects.equals(assignExprNode.exprL.exprType.Typename, "int") || Objects.equals(assignExprNode.exprL.exprType.Typename, "bool") || Objects.equals(assignExprNode.exprL.exprType.Typename, "String"))))
            throw new semanticError("Null can not assigned", assignExprNode.pos);
        assignExprNode.isAssignable=true;
    }

    @Override
    public void visit(LambdaExprNode lambdaExprNode) {
        curScp = new Scope(curScp);
        funcInDef.push(lambdaExprNode);
        if ((lambdaExprNode.parList == null) != (lambdaExprNode.aryList == null))
            throw new semanticError("Wrong parameter in lambda", lambdaExprNode.pos);
        if (lambdaExprNode.parList != null) {
            for (VarDefNode ele : lambdaExprNode.parList) ele.accept(this);
            for (ExprNode ele : lambdaExprNode.aryList) ele.accept(this);
            if (lambdaExprNode.parList.size() != lambdaExprNode.aryList.size())
                throw new semanticError("Wrong parameter in lambda", lambdaExprNode.pos);
            for (int i = 0; i < lambdaExprNode.parList.size(); i++) {
                if (!lambdaExprNode.parList.get(i).varType.equals(lambdaExprNode.aryList.get(i).exprType))
                    throw new semanticError("Wrong parameter in lambda", lambdaExprNode.pos);
            }
        }
        lambdaExprNode.body.accept(this);
        if (lambdaExprNode.returnType == null) throw new semanticError("No return statement in lambda", lambdaExprNode.pos);
        lambdaExprNode.exprType = lambdaExprNode.returnType;
        lambdaExprNode.isAssignable = true;
        curScp = curScp.parent;
        funcInDef.pop();
    }
}