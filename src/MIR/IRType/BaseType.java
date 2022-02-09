package MIR.IRType;

import java.util.Objects;

public class BaseType {
    public String typeName;
    public int byteNum = 0;

    public BaseType(String name) {
        typeName = name;
        byteNum = 4;
    }

    public int getByteNum() { return byteNum; }

    public String toString() {return typeName;}

    public boolean equal(String obj) {
        if (Objects.equals(obj, "bool")) return Objects.equals(typeName, "i1");
        else if (Objects.equals(obj, "int")) return Objects.equals(typeName, "i32");
        else if (Objects.equals(obj, "void")) return Objects.equals(typeName, "void") || typeName == null;
        else return false;
    }
}
