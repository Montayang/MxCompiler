package MIR;

import MIR.Value.User.Constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class IRScope {
    public Map<String, Constant> idMap = new HashMap<>();
    public IRScope parScope;

    public IRScope(IRScope scope) {
        parScope = scope;
    }

    public Constant get(String id) {
        if (idMap.containsKey(id)) return idMap.get(id);
        else if (parScope == null) return null;
        else return parScope.get(id);
    }

    public void addID(String name, Constant obj) {
        idMap.put(name, obj);
    }
}
