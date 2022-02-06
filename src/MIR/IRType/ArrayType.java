package MIR.IRType;

public class ArrayType extends BaseType {
    public BaseType base;
    public int len;

    public ArrayType(BaseType base_, int len_) {
        super(base_.typeName);
        base = base_;
        len = len_;
    }

    @Override
    public String toString() {
        return "[" + len + " x " + base.toString() + "]";
    }

    @Override
    public int getByteNum() {
        return base.getByteNum() * len;
    }
}
