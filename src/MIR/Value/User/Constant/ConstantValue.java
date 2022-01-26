package MIR.Value.User.Constant;

import MIR.IRType.BaseType;

import java.util.Objects;

public class ConstantValue extends Constant{
    public boolean boolValue = false;
    public int intValue = 0;
    public String stringValue = "";
    public ConstantValue(BaseType type1,boolean value) {
        super(type1);
        boolValue = value;
    }
    public ConstantValue(BaseType type1,int value) {
        super(type1);
        intValue = value;
    }
    public ConstantValue(BaseType type1,String value) {
        super(type1);
        stringValue = value;
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
