package Util;

import AST.TypeNode;

import java.util.HashMap;

public class Scope {
    public HashMap<String, TypeNode> varTable;
    public Scope parent;

    public Scope(Scope parent) {
        varTable = new HashMap<>();
        this.parent=parent;
    }

    public TypeNode fetchVarType(String id) {
        if (varTable.containsKey(id)) return varTable.get(id);
        else if (parent != null) return parent.fetchVarType(id);
        else return null;
    }

    public void defVar(String id, TypeNode type) {
        varTable.put(id,type);
    }

    public boolean containVar(String id) {
        return varTable.containsKey(id);
    }
}
