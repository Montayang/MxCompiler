package MIR.IRType;

abstract public class BaseType {
    public String typeName;
    public int byteNum = 0;

    public BaseType(String name) { typeName = name; }

    public int getByteNum() { return byteNum; }

    abstract public boolean equals(Object object);
}
