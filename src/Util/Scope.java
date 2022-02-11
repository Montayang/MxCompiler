package Util;

import AST.TypeNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Scope {
    public LinkedHashMap<String, TypeNode> varTable;
    public Scope parent;

    public Scope(Scope parent) {
        varTable = new LinkedHashMap<>();
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
