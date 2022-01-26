package MIR.IRType;

import java.util.Objects;

public class PointerType extends BaseType {
    public int dim;
    public PointerType(BaseType type) {
        super(type.typeName);
        byteNum = 8;
        if (!(type instanceof PointerType)) dim = 1;
        else dim = ((PointerType) type).dim + 1;
    }

    @Override
    public String toString() {return typeName + "*".repeat(dim);}

    public BaseType getObjType() {
        if (dim == 1) return new BaseType(typeName);
        else {
            PointerType ret = new PointerType(new BaseType(typeName));
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
