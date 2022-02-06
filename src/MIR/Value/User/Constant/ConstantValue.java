package MIR.Value.User.Constant;

import MIR.IRType.BaseType;
import MIR.IRType.PointerType;

import java.util.Objects;

public class ConstantValue extends Constant{
    public boolean boolValue = false;
    public long intValue = 0;
    public String stringValue = "";
    public ConstantValue(boolean value) {
        super(new BaseType("i1"));
        boolValue = value;
    }
    public ConstantValue(long value) {
        super(new BaseType("i32"));
        intValue = value;
    }
    public ConstantValue(String value) {
        super(new PointerType(new BaseType("i8")));
        stringValue = value;
    }

    public ConstantValue(BaseType type) {
        super(type);
    }

    @Override
    public String toString() {
        if (type.equal("bool")) return String.valueOf(boolValue);
        else if (type.equal("int")) return String.valueOf(intValue);
        else if (type.equal("string")) return stringValue;
        else return "null";
    }

    @Override
    public String unitOut() {
        if (type.equal("bool")) return type + " " + boolValue;
        else if (type.equal("int")) return type + " " + intValue;
        else if (type.equal("string")) {
            return " = private unnamed_addr constant [" + (stringValue.length() + 1) + " x i8] c\"" + stringValue.replace("\\", "\\5C").
                    replace("\n", "\\0A").replace("\t", "\\09").replace("\"", "\\22").
                    replace("\0", "\\00") + "\", align 1";
        }
        else return type + " null";
    }
}
