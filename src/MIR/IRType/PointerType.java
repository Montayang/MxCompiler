package MIR.IRType;

import java.util.Objects;

public class PointerType extends BaseType {
    public BaseType base;
    public int dim;
    public PointerType(BaseType type) {
        super(type.typeName);
        byteNum = 8;
        if (!(type instanceof PointerType)) {
            dim = 1;
            base = type;
        }
        else {
            dim = ((PointerType) type).dim + 1;
            base = ((PointerType) type).base;
        }
    }

    @Override
    public String toString() {return base + "*".repeat(dim);}

    public BaseType getObjType() {
        if (dim == 1) return base;
        else {
            PointerType ret = new PointerType(base);
            ret.dim = dim - 1;
            return ret;
        }
    }

    @Override
    public boolean equal(String obj) {
        if (Objects.equals(obj, "string")) return Objects.equals(typeName, "i8") && (dim == 1);
        else return false;
    }
}
