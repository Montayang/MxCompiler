package MIR.IRType;

import java.util.Objects;

public class BaseType {
    public String typeName;
    public int byteNum = 0;

    public BaseType(String name) {
        typeName = name;
        if (Objects.equals(typeName, "i1") || Objects.equals(typeName, "i8")) byteNum = 1;
        else if (Objects.equals(typeName, "i32")) byteNum = 4;
    }

    public int getByteNum() { return byteNum; }

    public String toString() {return typeName;}

    public boolean equal(String obj) {
        if (Objects.equals(obj, "bool")) return Objects.equals(typeName, "i1");
        else if (Objects.equals(obj, "int")) return Objects.equals(typeName, "i32");
        else if (Objects.equals(obj, "void")) return Objects.equals(typeName, "void");
        else return false;
    }
}
