package Util;

import AST.FuncDefNode;
import AST.TypeNode;

import java.util.HashMap;

public class globalScope extends Scope{
    public HashMap<String, FuncDefNode> funcTable;
    public HashMap<String, globalScope> classTable;
    public globalScope(Scope parent) {
        super(parent);
        funcTable=new HashMap<>();
        classTable=new HashMap<>();
    }

    public void defFunction(String id, FuncDefNode func) {
        funcTable.put(id,func);
    }

    public void defClass(String id, globalScope Class) {
        classTable.put(id,Class);
    }

    public boolean containClass(String id) {
        return classTable.containsKey(id);
    }

    public FuncDefNode fetchFunc(String id) {
        return funcTable.getOrDefault(id, null);
    }
}
