package MIR.IRType;

import java.util.ArrayList;

public class StructType extends BaseType {
    public ArrayList<BaseType> varList;
    public ArrayList<Integer> byteList = new ArrayList<>();

    public StructType(String name, ArrayList<BaseType> list) {
        super(name);
        varList = list;
        byteNum = 0;
        for (BaseType type : varList) byteNum += type.byteNum;
    }

    @Override
    public String toString() {
        return "%" + typeName;
    }
}
