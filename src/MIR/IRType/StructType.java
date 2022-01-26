package MIR.IRType;

import java.util.ArrayList;

public class StructType extends BaseType {
    public ArrayList<BaseType> parList;

    public StructType(String name, ArrayList<BaseType> list) {
        super(name);
        parList = list;
    }

    @Override
    public String toString() {
        return "%" + typeName;
    }
}
