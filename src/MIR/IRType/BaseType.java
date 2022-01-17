package MIR.IRType;

public class BaseType {
    public String typeName;
    public int byteNum = 0;

    public BaseType(String name) { typeName = name; }

    public int getByteNum() { return byteNum; }
}
